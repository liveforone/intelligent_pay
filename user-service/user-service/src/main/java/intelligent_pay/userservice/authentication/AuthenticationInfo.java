package intelligent_pay.userservice.authentication;

import intelligent_pay.userservice.jwt.constant.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;

@Component
public class AuthenticationInfo {

    private final Key key;

    public AuthenticationInfo(@Value(JwtConstant.SECRET_KEY_PATH) String secretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsername(HttpServletRequest request) {
        String token = resolveToken(request);
        Claims claims = getAuthentication(token);
        return claims.getSubject();
    }

    public String getAuth(HttpServletRequest request) {
        String token = resolveToken(request);
        Claims claims = getAuthentication(token);
        return claims.get(JwtConstant.CLAIM_NAME).toString();
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtConstant.HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtConstant.BEARER_TOKEN)) {
            return bearerToken.substring(JwtConstant.TOKEN_SUB_INDEX);
        }
        return null;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private Claims getAuthentication(String accessToken) {
        return parseClaims(accessToken);
    }
}
