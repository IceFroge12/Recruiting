package ua.kpi.nc.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.controller.auth.UserAuthentication;

/**
 * Created by dima on 14.04.16.
 */
@Controller
public class StudentController {

    @RequestMapping("/student")
    public ModelAndView student() {
        ModelAndView modelAndView = new ModelAndView("student");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthentication) {
            modelAndView.addObject("name", ((UserAuthentication) authentication).getName());
        }
        return modelAndView;
    }

}
