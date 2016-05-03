package ua.kpi.nc.controller.admin;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.dto.UserDto;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.RoleImpl;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.*;
import ua.kpi.nc.service.util.SenderService;
import ua.kpi.nc.service.util.SenderServiceImpl;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by dima on 23.04.16.
 */
@Controller
@RequestMapping("/admin")
public class AdminManagementStaffController {

    private UserService userService = ServiceFactory.getUserService();

    private RoleService roleService = ServiceFactory.getRoleService();

    private EmailTemplateService emailTemplateService = ServiceFactory.getEmailTemplateService();

    private SenderService senderService = SenderServiceImpl.getInstance();

    private SendMessageService sendMessageService = ServiceFactory.getResendMessageService();


    @RequestMapping(value = "staffmanagement", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("adminstaffmanagement");
        return modelAndView;
    }

    @RequestMapping(value = "showAllEmployee", method = RequestMethod.POST)
    @ResponseBody
    public Set<User> showAllEmployees() {
        Set<User> users = userService.getAllEmploees();
        for (User user :users){
            System.out.println(user);
        }
        return users;
    }

    @RequestMapping(value = "addEmployee", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public void addEmployee(@RequestBody UserDto userDto) throws MessagingException {
        System.out.println(userDto.toString());
        List<Role> roles = userDto.getRoleList();
        Date date = new Date();
        String password = RandomStringUtils.randomAlphabetic(10);
        User user = new UserImpl(userDto.getEmail(), userDto.getFirstName(), userDto.getSecondName(),
                userDto.getLastName(), password, true, new Timestamp(date.getTime()));
        userService.insertUser(user,roles);
//        EmailTemplate emailTemplate = emailTemplateService.getById(1L);

//        senderService.send(user.getEmail(), emailTemplate.getTitle(), emailTemplate.getText() + " " + password);

    }


    @RequestMapping(value = "getEmployeeParams", method = RequestMethod.POST)
    @ResponseBody
    public User getEmployeeParams(@RequestParam String email) {
        User user = userService.getUserByUsername(email);

        User employee = new UserImpl(user.getEmail(), user.getFirstName(),
                user.getSecondName(), user.getLastName(), user.getRoles());

        return employee;
    }

    @RequestMapping(value = "editEmployee", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public void editEmployeeParams(@RequestBody UserDto userDto) throws IOException {

        User user = new UserImpl(userDto.getEmail(), userDto.getFirstName(),
                userDto.getSecondName(), userDto.getLastName());        //TODO add roles !!!

        userService.updateUser(user);

    }

    @RequestMapping(value = "changeEmployeeStatus", method = RequestMethod.GET)
    @ResponseBody
    public Boolean changeEmployeeStatus(@RequestParam String email) {
        User user = userService.getUserByUsername(email);
        Boolean status;
        if (user.isActive()) {
            user.setActive(false);
            status = false;
            userService.updateUser(user);
        } else {
            user.setActive(true);
            status = true;
            userService.updateUser(user);
        }
        return status;
    }

    @RequestMapping(value = "showAssignedStudent", method = RequestMethod.GET)
    @ResponseBody
    public Set<User> showAssignedStudent(String email) {
        //TODO change from getAll to getAssignedStudents
        Set<User> assignedStudentList = userService.getAll();

        return assignedStudentList;
    }


}
