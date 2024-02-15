package org.atm.web.security;

import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenAuthentication implements Authentication {
    @Getter private String token;
    private boolean isAuthenticated;
    private UserDetails principal;

    public TokenAuthentication(String token) {
        this.token = token;
    }

    public TokenAuthentication(String token, UserDetails principal) {
        this.token = token;
        this.principal = principal;
        this.isAuthenticated = true;
    }

    public TokenAuthentication() {
        this.isAuthenticated = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public String getName() {
        if (principal != null) return principal.getUsername();
        else return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.isAuthenticated = b;
    }
}
