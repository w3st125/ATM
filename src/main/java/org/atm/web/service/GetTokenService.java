package org.atm.web.service;

public interface GetTokenService {
    String getToken(String username, String password) throws Exception;
}
