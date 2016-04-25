package ua.kpi.nc.config.filter;

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

/**
 * Created by IO on 23.04.2016.
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private final TokenAuthenticationService tokenAuthenticationService;

    public StatelessAuthenticationFilter(TokenAuthenticationService taService) {
        this.tokenAuthenticationService = taService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        SecurityContextHolder.getContext().setAuthentication(tokenAuthenticationService.getAuthentication((HttpServletRequest) req));
        chain.doFilter(req, res); // always continue
    }


}
