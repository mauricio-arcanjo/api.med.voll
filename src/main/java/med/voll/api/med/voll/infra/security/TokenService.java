package med.voll.api.med.voll.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.med.voll.model.entity.User;
import med.voll.api.med.voll.service.impl.TokenException;
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

    public static final String ISSUER = "API Voll.med";

    public String generateToken(User user) {
        try {
            //This is the secret key used to encrypt token
            var algorithm = Algorithm.HMAC256(secret);

            // this JWT static method needs that library Auth0 java-jwt is add into dependencies. https://jwt.io/ (Dependency is in github's documentation)
            return JWT.create()
                    .withIssuer(ISSUER) //Name of application that issued token
                    .withSubject(user.getLogin()) // User identification
//                    .withClaim("id", user.getId())  //It's possible to use as many withClaim as wanted and it's made of a key (id or other) / value relation. It's used to add more info from user
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new TokenException("Error to generate JWT token " + exception.getMessage());
        }
    }

    public String getSubject(String jwtToken) throws RuntimeException{

        try {
            var algorithm = Algorithm.HMAC256(secret);;
            return JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer(ISSUER)
                    // reusable verifier instance
                    .build()
                    .verify(jwtToken)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new TokenException("JWT token invalid or expired! " + jwtToken);
        }

    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00")); //Quebec zone offset
    }

}
