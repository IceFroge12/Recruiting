package ua.kpi.nc.controller.auth;

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

    private final TokenHandler tokenHandler;

    public TokenAuthenticationService(String secret, UserAuthService userAuthService) {
        tokenHandler = new TokenHandler(secret, userAuthService);
    }

    public String addAuthentication(HttpServletResponse response, Authentication authentication) {
        User user = (User) authentication.getDetails();
        return addToken(user, response);
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final ua.kpi.nc.persistence.model.User user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                addToken(user, response);
                return new UserAuthentication(user);
            }
        }
        return null;
    }

    private String addToken(User user, HttpServletResponse response){
        String token = tokenHandler.createTokenForUser(user);
        response.addHeader(AUTH_HEADER_NAME, token);
        return token;
    }
}
