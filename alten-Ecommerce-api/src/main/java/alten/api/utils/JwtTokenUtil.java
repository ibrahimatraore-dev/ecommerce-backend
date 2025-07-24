package alten.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import alten.core.ApplicationProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtTokenUtil {

    private final ApplicationProperties applicationProperties;

    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withClaim("role", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)  // Convert to List<String>
                        .collect(Collectors.toList()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .sign(getAlgorithm());
    }

    public String extractUsername(String token) {
        return extractClaim(token, "sub").asString();
    }

    private Claim extractClaim(String token, String claim) {
        DecodedJWT decoded = JWT.decode(token);
        return decoded.getClaim(claim);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, "exp").asDate();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        JWTVerifier verifier = JWT.require(getAlgorithm())
                .build();
        try{
            verifier.verify(token);
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }catch(Exception e){
            return false;
        }
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(applicationProperties.getJwtSecret());
    }
}