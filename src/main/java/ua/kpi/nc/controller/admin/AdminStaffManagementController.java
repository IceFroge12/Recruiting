package ua.kpi.nc.controller.admin;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.NotificationType;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.*;
import ua.kpi.nc.service.util.SenderService;
import ua.kpi.nc.service.util.SenderServiceImpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by dima on 23.04.16.
 */
@Controller
@RequestMapping("/admin")
public class AdminStaffManagementController {

    private UserService userService = ServiceFactory.getUserService();

    private RoleService roleService = ServiceFactory.getRoleService();

    private EmailTemplateService emailTemplateService = ServiceFactory.getEmailTemplateService();


    @RequestMapping(value = "staffmanagement", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("adminstaffmanagement");
        return modelAndView;
    }


    @RequestMapping(value = "showAllEmployee", method = RequestMethod.POST)
    @ResponseBody
    public Set<User> showAllEmployees() {
        Set<User> users = userService.getAll(); //TODO Change method
        for (User user : users) {
            System.out.println(user);
        }
        return users;
    }

    @RequestMapping(value = "addEmployee", method = RequestMethod.POST)
    @ResponseBody
    public void addEmployee(@RequestParam String firstName, @RequestParam String secondName,
                            @RequestParam String lastName, @RequestParam String email,
                            @RequestParam Boolean admin, @RequestParam Boolean soft,
                            @RequestParam Boolean tech) {

        List<String> roles = new ArrayList<>();
        Date date = new Date();
        String password = RandomStringUtils.randomAlphabetic(10);
        User user = new UserImpl(email, firstName, secondName, lastName, password, true, new Timestamp(date.getTime()));
        if (admin.equals(true)) {
            String role = "ROLE_ADMIN";
            roles.add(role);
        }
        if (soft.equals(true)) {
            String role = "ROLE_SOFT";
            roles.add(role);
        }
        if (tech.equals(true)) {
            String role = "ROLE_TECH";
            roles.add(role);
        }

        if (roles.size() == 1) {
            String role = roles.get(0);
            Role userRole = roleService.getRoleByTitle(role);
            System.out.println("ROLE" + userRole);
            userService.insertUser(user, userRole);
        } else {
            String role = roles.get(0);
            Role userRole = roleService.getRoleByTitle(role);
            userService.insertUser(user, userRole);
            for (int i = 1; i < roles.size(); i++) {
                String nextRole = roles.get(i);
                Role nextUserRole = roleService.getRoleByTitle(nextRole);
                userService.addRole(user, nextUserRole);
            }
        }

        EmailTemplate emailTemplate = emailTemplateService.getById(1L);//TODO change method
        System.out.println(emailTemplate.toString());
        SenderServiceImpl senderService = new SenderServiceImpl();
        senderService.send(email, emailTemplate.getTitle(),emailTemplate.getText()+" "+password);
        //TODO Send message with password
    }


    @RequestMapping(value = "getEmployeeParams", method = RequestMethod.POST)
    @ResponseBody
    public User getEmployeeParams(@RequestParam String email) {
        User user = userService.getUserByUsername(email);

        User employee = new UserImpl(user.getEmail(), user.getFirstName(),
                user.getSecondName(), user.getLastName(), user.getRoles());

        return employee;
    }

    @RequestMapping(value = "editEmployee", method = RequestMethod.POST)
    @ResponseBody
    public void editEmployeeParams(@RequestParam String firstName, @RequestParam String secondName,
                                   @RequestParam String lastName, @RequestParam String email,
                                   @RequestParam Boolean admin, @RequestParam Boolean soft,
                                   @RequestParam Boolean tech) throws IOException {

//        Set<Role> roles = new HashSet<>();
        User user = new UserImpl(email, firstName, secondName, lastName);
        userService.updateUser(user);

        //TODO add roles !!!

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
