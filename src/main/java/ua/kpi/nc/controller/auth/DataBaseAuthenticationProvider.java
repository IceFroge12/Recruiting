package ua.kpi.nc.controller.auth;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.util.UserAuthService;

import java.util.Collection;

/**
 * Created by IO on 24.04.2016.
 */
@Component
public class DataBaseAuthenticationProvider implements AuthenticationProvider {

    private UserAuthService userAuthService;

    public DataBaseAuthenticationProvider() {
        this.userAuthService = UserAuthService.getInstance();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user = userAuthService.loadUserByUsername(username);

        if (null == user || !user.getUsername().equalsIgnoreCase(username)){
            throw new BadCredentialsException("Username not found");
        }

        if (!password.equals(user.getPassword())){
            throw new BadCredentialsException("Password wrong");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user, password, authorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
