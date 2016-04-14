package ua.kpi.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dima on 14.04.16.
 */
@Controller
public class HrController {

    @RequestMapping("/hr")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("hr");
        return modelAndView;
    }
}
