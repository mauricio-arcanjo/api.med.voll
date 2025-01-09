package med.voll.api.med.voll.controller;

import jakarta.validation.Valid;
import med.voll.api.med.voll.infra.security.TokenService;
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
//    @Autowired
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid UserDto userDto){

        var token = new UsernamePasswordAuthenticationToken(userDto.login(), userDto.password());
        var authentication = authenticationManager.authenticate(token);

        return ResponseEntity
                .ok(tokenService
                        .generateToken((User) authentication.getPrincipal()));
    }

}
