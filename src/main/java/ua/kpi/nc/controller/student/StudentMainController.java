package ua.kpi.nc.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IO on 27.04.2016.
 */
@Controller
@RequestMapping("/student")
public class StudentMainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView studentmain(){
        return new ModelAndView("studentmain");
    }
}
