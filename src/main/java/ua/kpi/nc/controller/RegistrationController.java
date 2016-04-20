package ua.kpi.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dima on 12.04.16.
 */
@Controller
public class RegistrationController
{
    @RequestMapping(value = "/registration")
    public ModelAndView studentRegistration(){
        ModelAndView model = new ModelAndView("registration");
        return model;
    }
}
