package ua.kpi.nc.controller.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

/**
 * Created by dima on 24.04.16.
 */
@Controller
@RequestMapping("/admin")
public class AdminChangePasswordController {

    private UserService userService = ServiceFactory.getUserService();

    @RequestMapping(value = "changepassword", method = RequestMethod.GET)
    public ModelAndView studentManagement() {
        ModelAndView modelAndView = new ModelAndView("adminchangepassword");
        return modelAndView;
    }


    @RequestMapping(value = "changepass", method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(@RequestParam String oldPasssword, @RequestParam String newPassword) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        String name = auth.getName();
        System.out.println(name);
        System.out.println(oldPasssword);
        System.out.println(newPassword);

        User user = userService.getUserByUsername(name);

        System.out.println(user);

        if (user.getPassword().equals(oldPasssword)){
            user.setPassword(newPassword);
            userService.updateUser(user);
        }
        return "updated User";
    }

}
