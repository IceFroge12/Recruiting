package ua.kpi.nc.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dima on 14.04.16.
 */
@Controller
@RequestMapping("/student")
public class StudentMainController {

    @RequestMapping("/student")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("student");
        return modelAndView;
    }

}
