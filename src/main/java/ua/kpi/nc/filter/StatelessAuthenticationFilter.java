package ua.kpi.nc.filter;

import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import ua.kpi.nc.controller.auth.TokenAuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by IO on 23.04.2016.
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private final TokenAuthenticationService tokenAuthenticationServiceLoginPassword;
    private final TokenAuthenticationService tokenAuthenticationServiceSocial;
    private static final String AUTH_HEADER_NAME_LOGIN_PASSWORD = "X-AUTH-TOKEN_LOGIN_PASSWORD";
    private static final String AUTH_HEADER_NAME_SOCIAL = "X-AUTH-TOKEN_SOCIAL";

    public StatelessAuthenticationFilter(TokenAuthenticationService tokenAuthenticationServiceLoginPassword,
                                         TokenAuthenticationService tokenAuthenticationServiceSocial) {
        this.tokenAuthenticationServiceLoginPassword = tokenAuthenticationServiceLoginPassword;
        this.tokenAuthenticationServiceSocial = tokenAuthenticationServiceSocial;

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = ((HttpServletRequest) req);
        Authentication authentication = null;
        if (request.getHeader(AUTH_HEADER_NAME_LOGIN_PASSWORD) != null){
            authentication = tokenAuthenticationServiceLoginPassword.getAuthentication((HttpServletRequest) req,
                    (HttpServletResponse) res);
        }else if (request.getHeader(AUTH_HEADER_NAME_SOCIAL) != null){
            authentication = tokenAuthenticationServiceSocial.getAuthentication((HttpServletRequest) req,
                    (HttpServletResponse) res);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);

    }

}
