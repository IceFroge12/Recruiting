package ua.kpi.nc.controller.registration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.UserDto;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.impl.real.RoleImpl;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

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


    public RegistrationController() {
        userService = ServiceFactory.getUserService();
        roleService = ServiceFactory.getRoleService();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> registerNewStudent(@RequestBody UserDto user) {
        if (userService.isExist(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Role role = roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.STUDENT));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            userService.insertUser(
                    new UserImpl(
                            user.getEmail(),
                            user.getFirstName(),
                            user.getSecondName(),
                            user.getLastName(),
                            user.getPassword(),
                            false,
                            new Timestamp(System.currentTimeMillis())
                    ),
                    new ArrayList<>(roles));
            return ResponseEntity.ok(null);
        }
    }

}
