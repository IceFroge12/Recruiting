package ua.kpi.nc.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.kpi.nc.persistence.model.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by dima on 12.04.16.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        return "login";
    }


    @RequestMapping(value = "/logined", method = RequestMethod.GET)
    public void logined(HttpServletRequest request){
        String token = request.getHeader("X-AUTH-TOKEN");

    }
}
