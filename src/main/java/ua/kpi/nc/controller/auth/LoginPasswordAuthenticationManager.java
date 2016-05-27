package ua.kpi.nc.controller.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import ua.kpi.nc.persistence.model.User;

/**
 * Created by IO on 24.04.2016.
 */

public class LoginPasswordAuthenticationManager implements AuthenticationManager {

    private static Logger log = LoggerFactory.getLogger(LoginPasswordAuthenticationManager.class.getName());

    private UserAuthService userAuthService;
    private PasswordEncoderGeneratorService passwordEncoderGeneratorService;

    private LoginPasswordAuthenticationManager() {
        this.userAuthService = UserAuthService.getInstance();
        this.passwordEncoderGeneratorService = PasswordEncoderGeneratorService.getInstance();
    }

    private static class LoginPasswordAuthenticationProviderHolder{
        private static final LoginPasswordAuthenticationManager HOLDER = new LoginPasswordAuthenticationManager();
    }

    public static LoginPasswordAuthenticationManager getInstance(){
        return LoginPasswordAuthenticationProviderHolder.HOLDER;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        log.info("Looking user - {} in data base", username);
        User user = userAuthService.loadUserByUsername(username);

        if (!user.isActive()){
            log.info("User - {} is not active", username);
            throw new BadCredentialsException("User is not active");
        }

        if (!user.getUsername().equalsIgnoreCase(username)){
            log.info("User - {} not found", username);
            throw new BadCredentialsException("Username not found");
        }

        if (!passwordEncoderGeneratorService.matches(user.getPassword(), password )){
            log.info("User - {} have wrong password", username);
            throw new BadCredentialsException("Password wrong");
        }
        log.info("User - {} has been auth", username);
        return new UserAuthentication(user);
    }

}
