package ua.kpi.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dima on 23.04.16.
 */
@Controller
@RequestMapping("/admin")
public class AdminDataController {

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("admindata");
        return modelAndView;
    }

}
