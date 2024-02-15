package org.atm.web.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.atm.db.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class GetTokenServiceImpl implements GetTokenService {
    @Value("${jwt.token.secret}")
    private String secret;

    private final UserDetailsService userDetailsService;

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
           return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String getToken(String username, String password) throws Exception {
        if (username == null || password == null) return null;
        User user = (User) userDetailsService.loadUserByUsername(username);
        if (password.equals(user.getPassword())) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 1);
            JwtBuilder jwtBuilder = Jwts.builder();
            jwtBuilder.expiration(calendar.getTime());
            jwtBuilder.subject(user.getUsername());
            jwtBuilder.issuedAt(new Date());
            return jwtBuilder.signWith(getSignKey()).compact();
        } else {
            throw new Exception("Authentication error");
        }
    }
}
