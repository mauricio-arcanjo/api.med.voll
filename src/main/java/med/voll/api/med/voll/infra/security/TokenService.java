package med.voll.api.med.voll.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.med.voll.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    //Instructions to read the correct location of variable secret in application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            //This is the secret key used to encrypt token
            var algorithm = Algorithm.HMAC256(secret);

            // this JWT static method needs that library Auth0 java-jwt is add into dependencies. https://jwt.io/ (Dependency is in github's documentation)
            return JWT.create()
                    .withIssuer("API Voll.med") //Name of application that issued token
                    .withSubject(user.getLogin()) // User identification
//                    .withClaim("id", user.getId())  //It's possible to use as many withClaim as wanted and it's made of a key (id or other) / value relation. It's used to add more info from user
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error to generate JWT", exception);
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00")); //Quebec zone offset
    }

}
