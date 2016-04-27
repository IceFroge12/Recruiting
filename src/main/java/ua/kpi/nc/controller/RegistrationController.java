package ua.kpi.nc.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.EmailTemplateService;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;
import ua.kpi.nc.service.util.PasswordEncoderGeneratorService;
import ua.kpi.nc.service.util.SenderService;
import ua.kpi.nc.service.util.SenderServiceImpl;

import javax.mail.MessagingException;
import java.io.IOException;
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

    private EmailTemplateService emailTemplateService = ServiceFactory.getEmailTemplateService();

    private SenderService senderService = new SenderServiceImpl();

    private RoleService roleService = ServiceFactory.getRoleService();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView registrationModel() {
        User user = new UserImpl();
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("user", user);
        return modelAndView;
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addUser(UserImpl user) throws MessagingException {

        if (userService.isExist(user.getEmail())) {
            return "redirect:registration";
        }

        String hashedPassword = passwordEncoderGeneratorService.encode(user.getPassword());

        String token = RandomStringUtils.randomAlphabetic(50);
        user.setConfirmToken(token);
        user.setPassword(hashedPassword);

        Role role = roleService.getRoleByTitle(String.valueOf(RoleEnum.STUDENT));
        System.out.println(role);
        userService.insertUser(user, role);

        String url = "http://localhost:8084/registration/token="+token;

        System.out.println("TOKEN"+url);

        EmailTemplate emailTemplate = emailTemplateService.getById(2L);

        String text =  emailTemplate.getTitle()+
                token  + emailTemplate.getText();

        System.out.println(user.getEmail().toString());

        senderService.send(user.getEmail(), "Please confirm your account NC KPI", text);


        return "redirect:/login";
    }


    @RequestMapping(value = "/{token}", method = RequestMethod.GET)
    public String registrationConfirm(@PathVariable String token) {

//        if (userService.confirmUser(token)) {
//
//        }
        //todo

        return "login";
    }

}
