package ua.kpi.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.UserService;

/**
 * Created by dima on 12.04.16.
 */
@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(String.valueOf(GoogleAuthController.class));

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "logout", required = false) String logout) {

        User user = userService.getUserByUsername("dmytromyna@gmail.com");
        for(Role role: user.getRoles()){
            logger.info( role.getRoleName());
        }

        ModelAndView model = new ModelAndView();

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
            return model;
        }

        return model;
    }
}
