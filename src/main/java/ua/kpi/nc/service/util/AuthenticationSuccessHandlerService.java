package ua.kpi.nc.service.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import ua.kpi.nc.persistence.model.User;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * Created by dima on 20.04.16.
 */
public class AuthenticationSuccessHandlerService implements AuthenticationSuccessHandler {

    private static AuthenticationSuccessHandlerService customAuthenticationSuccessHandler;

    private AuthenticationSuccessHandlerService() {

    }

    public static AuthenticationSuccessHandlerService getInstance() {
        if (customAuthenticationSuccessHandler == null) {
            customAuthenticationSuccessHandler = new AuthenticationSuccessHandlerService();
        }
        return customAuthenticationSuccessHandler;
    }


    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException{
        HttpSession session = request.getSession();

        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("uname", authUser.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());

        String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }


    private String determineTargetUrl(Authentication authentication) {
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (authorities.contains("ADMIN")) {
            return "/admin";
        } else if (authorities.contains("STUDENT")) {
            return "/student";
        } else {
            throw new IllegalStateException();
        }
    }


}

