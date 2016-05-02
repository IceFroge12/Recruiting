package ua.kpi.nc.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.Message;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.*;
import ua.kpi.nc.service.util.PasswordEncoderGeneratorService;
import ua.kpi.nc.service.util.SenderService;
import ua.kpi.nc.service.util.SenderServiceImpl;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

    private SenderService senderService = SenderServiceImpl.getInstance();

    private RoleService roleService = ServiceFactory.getRoleService();

    private SendMessageService sendMessageService = ServiceFactory.getResendMessageService();

    @RequestMapping(method = RequestMethod.GET)
<<<<<<< HEAD
    public ModelAndView registrationModel() {
        User user = new UserImpl();
        ModelAndView modelAndView = new ModelAndView("registration");
=======
    public ModelAndView registration() {
        UserImpl user = new UserImpl();
        ModelAndView modelAndView = new ModelAndView("/WEB-INF/registration.jsp");
>>>>>>> origin/#Security
        modelAndView.addObject("user", user);
        List<Message> list =  sendMessageService.getAll();
        for (Message message1 : list){
            System.out.println(message1);
        }
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
        userService.insertUser(user, role);

        String url = "http://localhost:8084/registration/" + token;

        EmailTemplate emailTemplate = emailTemplateService.getById(2L);

        String text = emailTemplate.getTitle() +
                url + emailTemplate.getText();
        String subject = "Please confirm your account NC KPI";

        senderService.send(user.getEmail(), subject, text);

        return "redirect:/login";
    }

    @RequestMapping(value = "{token}", method = RequestMethod.GET)
    public String registrationConfirm(@PathVariable("token") String token) {
        User user = userService.getUserByToken(token);
        user.setActive(true);
        Date date = new Date();
        user.setRegistrationDate(new Timestamp(date.getTime()));
        userService.updateUser(user);
        return "login";
    }

}
