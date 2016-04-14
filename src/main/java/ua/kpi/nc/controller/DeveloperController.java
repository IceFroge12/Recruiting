package ua.kpi.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dima on 14.04.16.
 */
@Controller
public class DeveloperController {

    @RequestMapping("/dev")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("dev");
        return modelAndView;
    }

}
