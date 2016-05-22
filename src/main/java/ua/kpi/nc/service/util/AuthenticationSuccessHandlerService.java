package ua.kpi.nc.service.util;

import com.google.gson.Gson;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import ua.kpi.nc.persistence.dto.AuthUserDto;
import ua.kpi.nc.persistence.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws
            ServletException, IOException {
        response.getWriter().write(new Gson().toJson(
                new AuthUserDto(
                        ((User) authentication.getDetails()).getId(),
                        ((User) authentication.getDetails()).getUsername(),
                        determineTargetUrl(authentication),
                        new HashSet(authentication.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.toSet())).toString()
                )
        ));
    }

    private String determineTargetUrl(Authentication authentication) {
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (authorities.contains("ROLE_ADMIN")) {
            return "admin/main";
        } else if (authorities.contains("ROLE_STUDENT")) {
            return "student/appform";
        } else if (authorities.contains("ROLE_SOFT")) {
            return "staff/main";
        } else if (authorities.contains("ROLE_TECH")) {
            return "staff/main";
        } else {
            throw new IllegalStateException();
        }
    }


}

