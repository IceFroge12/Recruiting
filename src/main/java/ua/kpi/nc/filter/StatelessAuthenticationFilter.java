package ua.kpi.nc.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import ua.kpi.nc.controller.auth.TokenAuthenticationService;
import ua.kpi.nc.service.util.AuthenticationSuccessHandlerService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by IO on 23.04.2016.
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private final TokenAuthenticationService tokenAuthenticationService;

    public StatelessAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        Authentication authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) req, (HttpServletResponse) res);
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        if (null != authentication){
//            redirect(req, res, authentication);
//        }
        chain.doFilter(req, res);

    }

//    public void redirect(ServletRequest request, ServletResponse response, Authentication authentication) throws IOException, ServletException {
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        AuthenticationSuccessHandlerService.getInstance().onAuthenticationSuccess((HttpServletRequest) request, (HttpServletResponse) response, authentication);
//    }


}
