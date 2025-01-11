package med.voll.api.med.voll.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.med.voll.model.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component //Uses to spring load a generic class automatically

public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var jwtToken = getToken(request);
        if (jwtToken != null){
            var subject = tokenService.getSubject(jwtToken);
            var user = userRepository.findByLogin(subject);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); //Usually second attribute is always null

            //This method set user as authenticated to be able to interact with the application
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //This method allow the process to flow to the next filter/interceptor. Do not call this method if you want to stop the proccess
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        //Authorization header is a String
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "").trim();
        }
        return null;
    }
}
