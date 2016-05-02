package ua.kpi.nc.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua.kpi.nc.controller.auth.TokenAuthenticationService;
import ua.kpi.nc.controller.auth.UserAuthentication;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.persistence.model.adapter.UserAdapter;
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
import java.util.Set;

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
        JsonParser jsonParser = new JsonParser();
        JsonObject obj = (JsonObject) jsonParser.parse(request.getReader());
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                obj.get("email").getAsString(), obj.get("password").getAsString());
        return getAuthenticationManager().authenticate(loginToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        final User authenticatedUser = userAuthService.loadUserByUsername(authentication.getName());

        final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);
        tokenAuthenticationService.addAuthentication(response, request, userAuthentication);
        //response.addHeader("redirectURL", determineTargetUrl(userAuthentication));
//        super.successfulAuthentication(request, response, chain, authentication);
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        AuthenticationSuccessHandlerService.getInstance().onAuthenticationSuccess(request,response,authentication);

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        super.doFilter(req, res, chain);
    }
//
//    private String determineTargetUrl(Authentication authentication) {
//        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//        if (authorities.contains("ADMIN")) {
//            return "/admin";
//        } else if (authorities.contains("STUDENT")) {
//            return "/student";
//        } else {
//            throw new IllegalStateException();
//        }
//    }
}
