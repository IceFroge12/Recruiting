package ua.kpi.nc.controller.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.SocialNetwork;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.SocialNetworkEnum;
import ua.kpi.nc.persistence.model.impl.real.SocialInformationImpl;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by IO on 23.04.2016.
 */
public class UserAuthentication implements Authentication {


    private User user;
    private boolean authenticated = true;

    public UserAuthentication(String email, SocialNetwork socialNetwork, String accessToken) {
        user = new UserImpl();
        SocialInformation socialInformation = new SocialInformationImpl
                (
                        socialNetwork, accessToken, new Timestamp(System.currentTimeMillis())
                );
        user.setSocialInformations(new LinkedHashSet<>());
        user.getSocialInformations().add(socialInformation);
        user.setEmail(email);
    }

    public UserAuthentication(User user) {
        this.user = user;
//        user.getRoles();
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
