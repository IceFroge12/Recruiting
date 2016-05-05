package ua.kpi.nc.controller.registration;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.UserDto;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.impl.real.RoleImpl;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.*;
import ua.kpi.nc.service.util.PasswordEncoderGeneratorService;
import ua.kpi.nc.service.util.SenderService;
import ua.kpi.nc.service.util.SenderServiceImpl;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by IO on 04.05.2016.
 */
@Controller
@RequestMapping(value = "/registrationStudent")
public class RegistrationController {

    private UserService userService;
    private RoleService roleService;
    private EmailTemplateService emailTemplateService;
    private SenderService senderService;
    private SendMessageService sendMessageService;
    private PasswordEncoderGeneratorService passwordEncoderGeneratorService;


    public RegistrationController() {
        userService = ServiceFactory.getUserService();
        roleService = ServiceFactory.getRoleService();
        emailTemplateService = ServiceFactory.getEmailTemplateService();
        senderService = SenderServiceImpl.getInstance();
        sendMessageService = ServiceFactory.getResendMessageService();
        passwordEncoderGeneratorService = PasswordEncoderGeneratorService.getInstance();

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> registerNewStudent(@RequestBody UserDto user) throws MessagingException {
        if (userService.isExist(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Role role = roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.STUDENT));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            String token = RandomStringUtils.randomAlphabetic(50);
            userService.insertUser(
                    new UserImpl(
                            user.getEmail(),
                            user.getFirstName(),
                            user.getSecondName(),
                            user.getLastName(),
                            passwordEncoderGeneratorService.encode(user.getPassword()),
                            false,
                            new Timestamp(System.currentTimeMillis())
                    ),
                    new ArrayList<>(roles));
            String url = "http://localhost:8084/registration/" + token;

            EmailTemplate emailTemplate = emailTemplateService.getById(2L);

            String text = emailTemplate.getTitle() +
                    url + emailTemplate.getText();
            String subject = "Please confirm your account NC KPI";

            senderService.send(user.getEmail(), subject, text);
            return ResponseEntity.ok(null);
        }
    }

}
