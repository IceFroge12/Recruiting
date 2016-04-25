package ua.kpi.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dima on 12.04.16.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String loginIn(HttpServletRequest request) {
        request.getHeader("X-AUTH-TOKEN");
        System.out.println(request.getHeader("X-AUTH-TOKEN"));
        return "student";
    }
}
