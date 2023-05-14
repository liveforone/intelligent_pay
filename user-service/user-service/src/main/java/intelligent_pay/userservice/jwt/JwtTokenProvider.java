package intelligent_pay.userservice.jwt;

import intelligent_pay.userservice.jwt.constant.JwtConstant;
import intelligent_pay.userservice.utility.CommonUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenInfo generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + JwtConstant.TWO_HOUR_MS);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(JwtConstant.CLAIM_NAME, authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + JwtConstant.TWO_HOUR_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType(JwtConstant.BEARER_TOKEN)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (CommonUtils.isNull(claims.get(JwtConstant.CLAIM_NAME))) {
            throw new RuntimeException(JwtConstant.NO_AUTH_INFO_TOKEN_MESSAGE);
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(JwtConstant.CLAIM_NAME).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(
                claims.getSubject(),
                "",
                authorities
        );
        return new UsernamePasswordAuthenticationToken(
                principal,
                "",
                authorities
        );
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info(JwtConstant.INVALID_MESSAGE, e);
        } catch (ExpiredJwtException e) {
            log.info(JwtConstant.EXPIRED_MESSAGE, e);
        } catch (UnsupportedJwtException e) {
            log.info(JwtConstant.UNSUPPORTED_MESSAGE, e);
        } catch (IllegalArgumentException e) {
            log.info(JwtConstant.EMPTY_MESSAGE, e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
