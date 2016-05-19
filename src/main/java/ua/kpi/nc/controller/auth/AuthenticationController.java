package ua.kpi.nc.controller.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.kpi.nc.persistence.dto.CurrentAuthUser;
import ua.kpi.nc.persistence.model.User;

/**
 * Created by IO on 05.05.2016.
 */
@Controller
@RequestMapping(value = "/currentUser")
public class AuthenticationController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public CurrentAuthUser getCurrentUser() {
        User user = ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getDetails();
        if (null == user){
            return null;
        }else {
            return new CurrentAuthUser(user.getId(), user.getFirstName(), user.getRoles());
        }
    }
}
