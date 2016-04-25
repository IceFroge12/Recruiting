package ua.kpi.nc.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua.kpi.nc.controller.auth.NoOpAuthenticationManager;
import ua.kpi.nc.controller.auth.TokenAuthenticationService;
import ua.kpi.nc.controller.auth.UserAuthentication;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.util.AuthenticationSuccessHandlerService;
import ua.kpi.nc.service.util.UserAuthService;

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
public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenAuthenticationService tokenAuthenticationService;
    private final UserAuthService userAuthService;

    public StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService,
                                   UserAuthService userAuthService, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(urlMapping));
        this.userAuthService = userAuthService;
        this.tokenAuthenticationService = tokenAuthenticationService;
        setAuthenticationManager(authManager);
        setAuthenticationSuccessHandler(AuthenticationSuccessHandlerService.getInstance());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        final User user = new ObjectMapper().readValue(request.getInputStream(), UserImpl.class);
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());
        return getAuthenticationManager().authenticate(loginToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {

        // Lookup the complete User object from the database and create an Authentication for it
        final User authenticatedUser = userAuthService.loadUserByUsername(authentication.getName());
        final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

        // Add the custom token as HTTP header to the response
        tokenAuthenticationService.addAuthentication(response, request, userAuthentication);
        String token = response.getHeader("X-AUTH-TOKEN");

        // Add the authentication to the Security context
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        super.doFilter(req, res, chain);
    }
}
