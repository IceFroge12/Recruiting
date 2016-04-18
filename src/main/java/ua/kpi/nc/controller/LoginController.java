package ua.kpi.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dima on 12.04.16.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
            return model;
        }
        return model;
    }
}
