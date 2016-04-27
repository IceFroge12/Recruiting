package ua.kpi.nc.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by dima on 14.04.16.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService = ServiceFactory.getUserService();

    private RoleService roleService = ServiceFactory.getRoleService();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("/WEB-INF/admin.jsp");
        return modelAndView;
    }


//    @RequestMapping(value="/getRecruitmentData", method = RequestMethod.POST)
//    @ResponseBody
//    public void setRecruitmentData(@RequestBody Recruitment recruitment){
//
//    }

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

        //TODO Send message with password
    }


    public void changeEmployeeStatus(String email) {

    }

    @RequestMapping(value = "searchEmployee", method = RequestMethod.POST)
    @ResponseBody
    public void searchEmployee() {
//        userService.getAllEmployee();

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
    public void editEmployeeParams(HttpServletRequest req) throws IOException {

        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String lastName = req.getParameter("secondName");
        String email = req.getParameter("secondName");

    }


    public void showEmployees(){

    }


}
