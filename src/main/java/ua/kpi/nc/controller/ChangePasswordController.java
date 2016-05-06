package ua.kpi.nc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;
import ua.kpi.nc.service.util.PasswordEncoderGeneratorService;

/**
 * Created by dima on 06.05.16.
 */
@RestController
public class ChangePasswordController {

    private UserService userService = ServiceFactory.getUserService();

    private PasswordEncoderGeneratorService passwordEncoderGeneratorService = PasswordEncoderGeneratorService.getInstance();


    @RequestMapping(value = "changepassword", method = RequestMethod.POST)
    public ResponseEntity<String> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = auth.getName();

        User user = userService.getUserByUsername(name);

        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(passwordEncoderGeneratorService.encode(newPassword));
            userService.updateUser(user);
        }
        //TODO
        return ResponseEntity.ok("ok");
    }
}
