package org.atm.web.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface GetTokenService {
    boolean isTokenValid(String token, UserDetails userDetails);

    String extractUserName(String token);

    String getToken(String username, String password) throws Exception;
}
