package ua.kpi.nc.controller.student;

import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.UserDto;
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
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping(value = "/registrationStudent")
public class RegistrationController {

    private UserService userService;
    private RoleService roleService;
    private EmailTemplateService emailTemplateService;
    private SenderService senderService;
    private PasswordEncoderGeneratorService passwordEncoderGeneratorService;


    public RegistrationController() {
        userService = ServiceFactory.getUserService();
        roleService = ServiceFactory.getRoleService();
        emailTemplateService = ServiceFactory.getEmailTemplateService();
        senderService = SenderServiceImpl.getInstance();
        passwordEncoderGeneratorService = PasswordEncoderGeneratorService.getInstance();

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> registerNewStudent(@RequestBody UserDto userDto) throws MessagingException {
        if (userService.isExist(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
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
            userService.insertUser(user,new ArrayList<>(roles));

            String url = "http://localhost:8085/frontend/index.html#/registrationStudent/" + token;

            EmailTemplate emailTemplate = emailTemplateService.getById(2L);

            String template = emailTemplateService.showTemplateParams(emailTemplate.getText(), user);

            String text = template +"\n"+ url;

            String subject = emailTemplate.getTitle();

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
//        userService.deleteToken(user.getId());

        String json = "ok";
        return gson.toJson(json);
    }
}
