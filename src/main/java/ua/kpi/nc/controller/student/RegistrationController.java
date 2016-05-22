package ua.kpi.nc.controller.student;

import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.controller.auth.ForgotPassword;
import ua.kpi.nc.controller.auth.TokenAuthenticationService;
import ua.kpi.nc.persistence.dto.MessageDto;
import ua.kpi.nc.persistence.dto.UserDto;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.EmailTemplateEnum;
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
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping(value = "/registrationStudent")
public class RegistrationController {

    private static Logger log = LoggerFactory.getLogger(RegistrationController.class.getName());
    private static final String USER_EXIST = "User with this email already exist";
    private static final String TOKEN_EXPIRED = "User token expired";

    private UserService userService = ServiceFactory.getUserService();
    private RoleService roleService = ServiceFactory.getRoleService();
    private EmailTemplateService emailTemplateService = ServiceFactory.getEmailTemplateService();
    private SenderService senderService = SenderServiceImpl.getInstance();
    private PasswordEncoderGeneratorService passwordEncoderGeneratorService = PasswordEncoderGeneratorService.getInstance();


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity registerNewStudent(@RequestBody UserDto userDto, HttpServletRequest request) throws MessagingException {
        log.info("Looking user with email - {}", userDto.getEmail());
        if (userService.isExist(userDto.getEmail())) {
            log.info("User with email - {} already exist", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(USER_EXIST));
        } else {
            Role role = roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            String token = RandomStringUtils.randomAlphabetic(50);
            User user = new UserImpl(userDto.getEmail(),
                    userDto.getFirstName(),
                    userDto.getSecondName(),
                    userDto.getLastName(),
                    passwordEncoderGeneratorService.encode(userDto.getPassword()),
                    false,
                    new Timestamp(System.currentTimeMillis()),
                    token);
            log.trace("Inserting user with email - {} in data base", userDto.getEmail());
            userService.insertUser(user, new ArrayList<>(roles));

            String url = String.format("%s://%s:%d/frontend/index.html#/registrationStudent/%s",request.getScheme(),  request.getServerName(), request.getServerPort(), token);

            EmailTemplate emailTemplate = emailTemplateService.getById(EmailTemplateEnum.STUDENT_REGISTRATION.getId());

            String template = emailTemplateService.showTemplateParams(emailTemplate.getText(), user);

            String text = template + "\n" + url;

            String subject = emailTemplate.getTitle();

            log.info("Lending email");
            senderService.send(user.getEmail(), subject, text);

            return ResponseEntity.ok(new UserDto(user.getEmail(), user.getFirstName()));
        }
    }


    @RequestMapping(value = "/{token}", method = RequestMethod.GET)
    public String registrationConfirm(@PathVariable("token") String token) {
        User user = userService.getUserByToken(token);
        Gson gson = new Gson();
        if (null == user) {
            String json = "error";
            return gson.toJson(json);
        }
        user.setActive(true);
        userService.updateUser(user);
        userService.deleteToken(user.getId());

        String json = "ok";
        return gson.toJson(json);
    }
}
