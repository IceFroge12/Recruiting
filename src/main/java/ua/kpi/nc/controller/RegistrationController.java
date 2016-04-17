package ua.kpi.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.domain.model.impl.real.RoleImpl;
import ua.kpi.nc.domain.model.impl.real.UserImpl;
import ua.kpi.nc.service.PasswordEncoderGeneratorService;
import ua.kpi.nc.service.SenderService;
import ua.kpi.nc.service.UserService;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dima on 12.04.16.
 */
@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoderGeneratorService passwordEncoderGeneratorService;

    @Autowired
    private SenderService senderService;



    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView registration() {
        UserImpl user = new UserImpl();
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("user", user);
        return modelAndView;
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addUser(UserImpl user, BindingResult result) {

        Set<User> users = new HashSet<>();
        Role role = new RoleImpl(25l, "ROLE_STUDENT", users);
        String password = user.getPassword();
        String hashedPassword = passwordEncoderGeneratorService.encode(password);
//        user.setPassword(hashedPassword);
        System.out.println("HASH" + hashedPassword);
        if (userService.isExist(user.getEmail())) {
            System.out.println("exist");
            return "redirect:registration";
        }
        String token = "http://localhost:8080/" + hashedPassword + user.getFirstName();

        String text = "<html><body><h4>Chiki piki</h4>br" + token + "br" +
                "</body></html>";

        userService.insertUser(user, role);
        System.out.println("INSERT");
//        senderService.send(user, "Please Confirm your account","dmytromyna@gmail.com", "text");
        senderService.send(user);

        return "redirect:/student";
    }

}
