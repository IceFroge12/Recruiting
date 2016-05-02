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
            ServletException, IOException {
//        HttpSession session = request.getSession();
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        session.setAttribute("uname", user.getUsername());
//        session.setAttribute("authorities", user.getAuthorities());

        String redirectURL = determineTargetUrl(authentication);
        //response.addHeader("redirectURL", redirectURL);
        redirectStrategy.sendRedirect(request,response, redirectURL);
    }


    private String determineTargetUrl(Authentication authentication) {
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (authorities.contains("ROLE_ADMIN")) {
            return "/admin/";
        } else if (authorities.contains("ROLE_STUDENT")) {
            return "/student/";
        } else {
            throw new IllegalStateException();
        }
    }


}

