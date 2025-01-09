package med.voll.api.med.voll.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.med.voll.model.entity.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public String generateToken(User user) {
        try {
            //This is the secret key used to encrypt token
            var algorithm = Algorithm.HMAC256("12345678");

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
