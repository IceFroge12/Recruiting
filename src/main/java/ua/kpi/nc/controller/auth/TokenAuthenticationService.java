package ua.kpi.nc.controller.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.util.UserAuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IO on 23.04.2016.
 */
public class TokenAuthenticationService {


    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    private static Logger log = LoggerFactory.getLogger(TokenAuthenticationService.class.getName());
    private final TokenHandler tokenHandler;


    public TokenAuthenticationService(String secret, UserAuthService userAuthService) {
        tokenHandler = new TokenHandler(secret, userAuthService);
    }

    public String addAuthentication(HttpServletResponse response, Authentication authentication) {

        User user = (User) authentication.getDetails();
        return addToken(user, response);
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("Get token from query");
        String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            log.info("Token found");
            final ua.kpi.nc.persistence.model.User user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                log.info("User found");
                addToken(user, response);
                return new UserAuthentication(user);
            }
        }
        log.info("User with token - {} not exist", token);
        return null;
    }

    private String addToken(User user, HttpServletResponse response){
        String token = tokenHandler.createTokenForUser(user);
        log.info("Add token to header");
        response.addHeader(AUTH_HEADER_NAME, token);
        return token;
    }
}
