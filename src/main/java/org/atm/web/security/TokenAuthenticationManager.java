package org.atm.web.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.MacAlgorithm;
import java.util.Collection;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenAuthenticationManager implements AuthenticationManager {
    @Value("${jwt.token.secret}")
    private String secret;

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        if (authentication instanceof TokenAuthentication) {
            return processAuthentication((TokenAuthentication) authentication);
        } else {
            authentication.setAuthenticated(false);
            return authentication;
        }
    }

    private TokenAuthentication processAuthentication(TokenAuthentication authentication)
            throws AuthenticationException {
        String token = authentication.getToken();
        DefaultClaims claims;
        MacAlgorithm hs256 = Jwts.SIG.HS256;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), hs256.getId());
        try {
            claims =
                    (DefaultClaims)
                            Jwts.parser()
                                    .verifyWith(secretKeySpec)
                                    .build()
                                    .parse(token)
                                    .getPayload();
        } catch (Exception ex) {
            throw new AuthenticationServiceException("Token corrupted");
        }
        if (claims.getExpiration() == null)
            throw new AuthenticationServiceException("Invalid token");
        Date expiredDate = claims.getExpiration();
        if (expiredDate.after(new Date()))
            return buildFullTokenAuthentication(authentication, claims);
        else throw new AuthenticationServiceException("Token expired date error");
    }

    private TokenAuthentication buildFullTokenAuthentication(
            TokenAuthentication authentication, DefaultClaims claims) {
        User user =
                (User) userDetailsService.loadUserByUsername(claims.get("username", String.class));
        if (user.isEnabled()) {
            Collection<GrantedAuthority> authorities = user.getAuthorities();
            return new TokenAuthentication(authentication.getToken(), user);
        } else {
            throw new AuthenticationServiceException("User disabled");
        }
    }
}
