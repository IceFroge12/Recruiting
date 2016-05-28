package ua.kpi.nc.filter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.security.SocialAuthenticationFilter;
import ua.kpi.nc.controller.auth.TokenAuthenticationService;
import ua.kpi.nc.controller.auth.AuthenticationSuccessHandlerService;

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
    private static final String LOGIN_TITLE = "email";
    private static final String PASSWORD_TITLE = "password";

    public StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService,
                                AuthenticationManager authenticationManager, AuthenticationSuccessHandler authenticationSuccessHandler) {
        super(new AntPathRequestMatcher(urlMapping));
        this.tokenAuthenticationService = tokenAuthenticationService;
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
//        setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler());
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        JsonObject obj = (JsonObject) new JsonParser().parse(request.getReader());
        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                getLogin(obj), getPassword(obj));
        return getAuthenticationManager().authenticate(loginToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        tokenAuthenticationService.addAuthentication(response, authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthenticationSuccessHandlerService.getInstance().onAuthenticationSuccess(request,response,authentication);


    }

    private static String getLogin(JsonObject jsonObject){
        return jsonObject.get(LOGIN_TITLE).getAsString();
    }

    private static String getPassword(JsonObject jsonObject){
        return jsonObject.get(PASSWORD_TITLE).getAsString();
    }
}
