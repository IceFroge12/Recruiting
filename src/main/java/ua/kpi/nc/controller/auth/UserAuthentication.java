package ua.kpi.nc.controller.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ua.kpi.nc.persistence.model.User;


import java.util.Collection;

/**
 * Created by IO on 23.04.2016.
 */
public class UserAuthentication implements Authentication {


    private final User user;
    private boolean authenticated = true;

    public UserAuthentication(User user) {
        this.user = user;
        user.getRoles();
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public User getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
