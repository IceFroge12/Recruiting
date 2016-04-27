package ua.kpi.nc.controller;

/**
 * Created by dima on 14.04.16.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaController {

    @RequestMapping("/ba")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("/WEB-INF/ba.jsp");
        return modelAndView;
    }

}
