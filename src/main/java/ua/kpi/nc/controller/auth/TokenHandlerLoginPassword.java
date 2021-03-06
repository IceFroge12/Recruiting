package ua.kpi.nc.controller.auth;

import com.google.common.base.Preconditions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.model.User;

import java.util.Date;


/**
 * Created by IO on 23.04.2016.
 */
public class TokenHandlerLoginPassword extends TokenHandler {

    private UserAuthServiceLoginPassword userService;
    private Logger log = LoggerFactory.getLogger(TokenHandlerLoginPassword.class);

    private static class TokenHandlerLoginPasswordHolder {
        private static final TokenHandlerLoginPassword HOLDER = new TokenHandlerLoginPassword();
    }

    private TokenHandlerLoginPassword() {
        super();
        userService = UserAuthServiceLoginPassword.getInstance();
        log = LoggerFactory.getLogger(TokenHandlerLoginPassword.class);
    }

    public static TokenHandlerLoginPassword getInstance() {
        return TokenHandlerLoginPasswordHolder.HOLDER;
    }

    public UserAuthentication parseUserFromToken(String token) {

        log.info("Start parsing tokne - {}", token);
        try {
            String subject = parse(token);
            User user = userService.loadUserByUsername(subject);
            if (null != user) {
                return new UserAuthentication(user);
            }
        } catch (ExpiredJwtException e) {
            log.error("Token expired", e);
        }
        return null;
    }

    public String createTokenForUser(UserAuthentication userAuthentication) {
        User user = userAuthentication.getDetails();
        log.info("Start create token for user - {}", user.getEmail());
        return createToken(user.getUsername());

    }

}
