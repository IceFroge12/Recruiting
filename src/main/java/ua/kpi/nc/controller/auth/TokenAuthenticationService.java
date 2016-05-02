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

//    private static TokenAuthenticationService tokenAuthenticationService;

    public TokenAuthenticationService(String secret, UserAuthService userAuthService) {
        tokenHandler = new TokenHandler(secret, userAuthService);
    }

    public String addAuthentication(HttpServletResponse response, HttpServletRequest request, UserAuthentication authentication) {
        User user = authentication.getDetails();
        String token = tokenHandler.createTokenForUser(user);
        request.getSession().setAttribute(AUTH_HEADER_NAME, token);
        return token;
    }


    public Authentication getAuthentication(HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(AUTH_HEADER_NAME);
        String token1 = request.getHeader(AUTH_HEADER_NAME);
        System.out.println(token1);
        if (token != null) {
            final ua.kpi.nc.persistence.model.User user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                user.getAuthorities().forEach(GrantedAuthority::getAuthority);
                return new UserAuthentication(user);
            }
        }
        return null;
    }
}
