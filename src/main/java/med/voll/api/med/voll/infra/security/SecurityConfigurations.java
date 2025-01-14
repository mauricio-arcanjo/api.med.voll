package med.voll.api.med.voll.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) //This annotation is needed if you want to use @Secured in the rest controller classes
public class SecurityConfigurations {

    //This object is needed to set the order of filters used by spring
    private final SecurityFilter securityFilter;

    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    //AuthenticationManager is the spring object that controls authentication process automatically
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    //SecurityFilterChain is the object used to configure authorizations and authentications
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        /*
         CSRF - Cross site request forgery:  this property is being disabled since we are working with JWT (json web tokens) and it is already a protection against this
          kind of attack, so it's not needed to be handled again by spring security.
          Session Management = Stateless - is the standard for backend applications which don't save sessions as front end do. It uses JWT token for each request.
         */
        System.out.println("Filter antes do return");
        return httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Turns application into statelss and disables login screen
                .authorizeHttpRequests(auth -> {
                            auth.requestMatchers(HttpMethod.POST, "/login").permitAll(); // Permite POST em /login
                            auth.requestMatchers( "v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll(); //Documentation - access on http://localhost:8080/v3/api-docs and http://localhost:8080/swagger-ui/index.html
//                            auth.requestMatchers(HttpMethod.DELETE, "/doctors/**").hasRole("ADMIN"); // This config can be changed for @Secured in the methods or class or rest controller. See DoctorController
                            auth.requestMatchers(HttpMethod.DELETE, "/patients/**").hasRole("[ADMIN]"); //Only admin can delete doctors and patients
                            auth.anyRequest().authenticated(); // Exige autenticação para qualquer outra requisição
                        })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //IMPORTANT: This parameter needs to be added so spring will execute application filter before its own filter
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
