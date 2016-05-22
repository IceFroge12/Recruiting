package ua.kpi.nc.controller.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.nc.persistence.dao.impl.DecisionDaoImpl;
import ua.kpi.nc.persistence.dto.CurrentAuthUser;
import ua.kpi.nc.persistence.model.User;

/**
 * Created by IO on 05.05.2016.
 */
@RestController
@RequestMapping(value = "/currentUser")
public class AuthenticationController {


    private static Logger log = LoggerFactory.getLogger(AuthenticationController.class.getName());

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public CurrentAuthUser getCurrentUser() {
        log.info("Looking authorized user");
        if (SecurityContextHolder.getContext().getAuthentication() instanceof UserAuthentication){
            User user = ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getDetails();
            log.info("Authorized user found - {}", user.getEmail());
            return new  CurrentAuthUser(user.getId(), user.getFirstName(), user.getRoles());
        }else {
            log.info("Authorized user not found");
            return null;
        }
    }
}
