package med.voll.api.med.voll.controller;

import jakarta.validation.Valid;
import med.voll.api.med.voll.infra.security.TokenService;
import med.voll.api.med.voll.model.dto.JwtTokenDto;
import med.voll.api.med.voll.model.dto.UserDto;
import med.voll.api.med.voll.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    //Requires a @Bean configuration in SecurityConfiguration to spring be able to insert dependencies.
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /*
        Classes used to implement spring security authentication:
            1. pom - security dependencies
            2. User - user entity
            3. UserRepository - interface to access DB
            4. UserDto - to transfer data between front and back end
            5. AuthenticationServiceImpl - @Service class tha implements UserDetailsService to allow sprint to do all authentication process
            6. TokenService - to generate and read JWT tokens
            7. SecurityConfigurations - @Configuration class to set all security configurations needed
            8. SecurityFilter - @Components class that spring uses run some methods that acts like a filter during each request to check if token is sent and is correct
            9. AuthenticationController - @RestController class for authentication process
            10. JwtTokenDto - to transfer token from back end to front end client
     */

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid UserDto userDto){
        try {
            //This method starts authentication generation an object UsernamePasswordAuthenticationToken that is like a spring's authentication dto.
            var authenticationToken = new UsernamePasswordAuthenticationToken(userDto.login(), userDto.password());

            /*
                Although there is a class Authentication service we don't call it using controller because spring does that automatically through AuthenticationManager class.
                This needs to have an instance in SecurityConfigurations class, receives as parameter authenticationToken, check if user existis in DB using AuthencitcationService
                 and returns an authentication object (which contains user login info) and that represents that user is successfully loged.
                 Note: if user does not exist method returns error 400 bad request
             */
            var authentication = authenticationManager.authenticate(authenticationToken);

            //If Authentication is successful, this method will generate a JWT token to return to client
            var jwtToken = tokenService.generateToken((User) authentication.getPrincipal());

            return ResponseEntity.ok(new JwtTokenDto(jwtToken));
            //Class SecurityFilter needs to be implemented to allow spring to filter requests with valid tokens
         } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
         }
    }


}
