package com.RegistratieSysteem.LoginSec;

import com.RegistratieSysteem.model.User;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class SecContext
        implements SecurityContext {
    private User user;
    private String scheme;

    public SecContext(User user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.user;
    }

    @Override
    public boolean isUserInRole(String s) {
        if (user.getName() != null) {
            return s.equals(user.getName());
        }
        return false;
    }

    @Override
    public boolean isSecure() {
        return "https".equals(this.scheme);
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}