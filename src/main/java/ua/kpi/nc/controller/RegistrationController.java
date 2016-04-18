package ua.kpi.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.domain.model.Role;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.domain.model.impl.real.RoleImpl;
import ua.kpi.nc.domain.model.impl.real.UserImpl;
import ua.kpi.nc.service.PasswordEncoderGeneratorService;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.UserService;
import ua.kpi.nc.service.mail.SenderService;

import java.util.Random;

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

    @Autowired
    private RoleService roleService;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView registration() {
        UserImpl user = new UserImpl();
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("user", user);
        return modelAndView;
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addUser(UserImpl user) {

        Random rand = new Random();

        int randomNum = rand.nextInt((1000000 - 7) + 7);

        String token = "http://localhost:8084/registration/token=" +
                passwordEncoderGeneratorService.encode(user.getFirstName()) + randomNum;


        String text = "<html><body><h4>Chiki piki</h4><br><img src=\"cid:\"/><br><a href=" +
                token + ">Confirm your account</a><br></body></html>";

        String password = user.getPassword();
        String hashedPassword = passwordEncoderGeneratorService.encode(password);

        user.setPassword(hashedPassword);
        if (userService.isExist(user.getEmail())) {
            return "redirect:registration";//TODO
        }

        Role role = roleService.getRoleById(2l);
        userService.insertUser(user, role);

        senderService.send(user.getEmail(), "Please confirm your account NC KPI", text);

        return "redirect:/login";
    }


    @RequestMapping(value = "/{token}", method = RequestMethod.GET)
    public String registrationConfirm(@PathVariable String token) {

        if (userService.confirmUser(token)) {

        } else {
            userService.deleteUserByToken(token);
        }

        return "login";
    }

}
