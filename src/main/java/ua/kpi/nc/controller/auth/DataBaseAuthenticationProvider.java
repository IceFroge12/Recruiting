package ua.kpi.nc.controller.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.util.PasswordEncoderGeneratorService;
import ua.kpi.nc.service.util.UserAuthService;

import java.util.Collection;
import java.util.List;

/**
 * Created by IO on 24.04.2016.
 */
@Component
public class DataBaseAuthenticationProvider implements AuthenticationProvider {

    private UserAuthService userAuthService;
    private PasswordEncoderGeneratorService passwordEncoderGeneratorService;

    public DataBaseAuthenticationProvider() {
        this.userAuthService = UserAuthService.getInstance();
        this.passwordEncoderGeneratorService = PasswordEncoderGeneratorService.getInstance();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user = userAuthService.loadUserByUsername(username);

        if (null == user || !user.getUsername().equalsIgnoreCase(username)){
            throw new BadCredentialsException("Username not found");
        }

        if (passwordEncoderGeneratorService.matches(user.getPassword(), password )){
            throw new BadCredentialsException("Password wrong");
        }

        return new UserAuthentication(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
