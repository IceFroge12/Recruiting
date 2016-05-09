package ua.kpi.nc.service.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.poi.ss.formula.ptg.MemAreaPtg;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import ua.kpi.nc.controller.auth.UserAuthentication;
import ua.kpi.nc.controller.auth.UserAuthority;
import ua.kpi.nc.persistence.model.User;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
        HashMap<String, String> map = new HashMap<>();
        map.put("redirectURL", determineTargetUrl(authentication));
        map.put("username", ((User) authentication.getDetails()).getFirstName());
        map.put("id", ((User) authentication.getDetails()).getId().toString());
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(map));
    }


    private String determineTargetUrl(Authentication authentication) {
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (authorities.contains("ROLE_ADMIN")) {
            return "admin/main";
        } else if (authorities.contains("ROLE_STUDENT")) {
            return "student/appform";
        }else if (authorities.contains("ROLE_SOFT")){
            return "staff/main";
        }else if (authorities.contains("ROLE_TECH")) {
            return "staff/main";
        } else {
            throw new IllegalStateException();
        }
    }


}

