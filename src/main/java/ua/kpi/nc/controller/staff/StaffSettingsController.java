package ua.kpi.nc.controller.staff;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

/**
 * @author Yaroslav Kruk on 5/5/16.
 *         e-mail: yakruck@gmail.com
 *         GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.7
 */
@Controller
@RequestMapping("/staff/settings")
public class StaffSettingsController {

    private UserService userService = ServiceFactory.getUserService();
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView settings() {
        return new ModelAndView("staff_settinngs");
    }

    @RequestMapping(value = "changepass", method = RequestMethod.POST)
    @ResponseBody
    public Boolean changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        Boolean response = Boolean.FALSE;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();

        User user = userService.getUserByUsername(name);

        if (user.getPassword().equals(encoder.encode(oldPassword))) {
            user.setPassword(newPassword);
            userService.updateUser(user);
            response = Boolean.TRUE;
        } else {
            // TODO check
        }
        return response;
    }
}
