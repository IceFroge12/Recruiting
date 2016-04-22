package ua.kpi.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;
import ua.kpi.nc.service.util.PasswordEncoderGeneratorService;

import java.util.Random;

/**
 * Created by dima on 12.04.16.
 */
@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    private UserService userService = ServiceFactory.getUserService();

    private PasswordEncoderGeneratorService passwordEncoderGeneratorService
            = PasswordEncoderGeneratorService.getInstance();

//    private SenderService senderService;

    private RoleService roleService = ServiceFactory.getRoleService();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView registration() {
        UserImpl user = new UserImpl();
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("user", user);
        return modelAndView;
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addUser(UserImpl user) {
        System.out.println("LOL"+user.toString());

        Random rand = new Random();

        int randomNum = rand.nextInt((1000000 - 7) + 7);

        String token = "http://localhost:8084/registration/token=" +
                passwordEncoderGeneratorService.encode(user.getFirstName()) + randomNum;


        System.out.println("TOKEN"+token);

        String text = "<html><body><h4>Chiki piki</h4><br><img src=\"cid:\"/><br><a href=" +
                token + ">Confirm your account</a><br></body></html>";

        String password = user.getPassword();


//        String hashedPassword = passwordEncoderGeneratorService.encode(password);

//        user.setPassword(hashedPassword);

//        if (userService.isExist(user.getEmail())) {
//            return "redirect:registration";
//        }

        Role role = roleService.getRoleById(2L);
        System.out.println(role);
        userService.insertUser(user, role);

//        senderService.send(user.getEmail(), "Please confirm your account NC KPI", text);

        return "redirect:/login";
    }


    @RequestMapping(value = "/{token}", method = RequestMethod.GET)
    public String registrationConfirm(@PathVariable String token) {

//        if (userService.confirmUser(token)) {
//
//        } else {
//            userService.deleteUserByToken(token);
//        }

        return "login";
    }

}
