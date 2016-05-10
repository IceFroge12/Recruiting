package ua.kpi.nc.controller.admin;

import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.dto.UserDto;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.Interview;
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
import java.util.*;

/**
 * Created by dima on 23.04.16.
 */
@RestController
@RequestMapping("/admin")
public class AdminManagementStaffController {

    private UserService userService = ServiceFactory.getUserService();

    private RoleService roleService = ServiceFactory.getRoleService();

    private InterviewService interviewService = ServiceFactory.getInterviewService();

    private EmailTemplateService emailTemplateService = ServiceFactory.getEmailTemplateService();

    private SenderService senderService = SenderServiceImpl.getInstance();

    private SendMessageService sendMessageService = ServiceFactory.getResendMessageService();


    @RequestMapping(value = "showAllEmployees", method = RequestMethod.GET)
    public List<User> showEmployees(@RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol,
                                    @RequestParam boolean increase) {
        Long fromRow = (pageNum - 1) * rowsNum;
        return userService.getEmployeesFromToRows(fromRow,rowsNum, sortingCol, increase);
    }

    @RequestMapping(value = "getCountOfEmployee", method = RequestMethod.GET)
    public Long getCountOfEmployee() {
        return userService.getAllEmployeeCount();
    }


    @RequestMapping(value = "addEmployee", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public void addEmployee(@RequestBody UserDto userDto){

        System.out.println(userDto.toString());
        List<RoleImpl> roles = userDto.getRoleList();
        List<Role> userRoles = new ArrayList<>();
        for (Role role : roles) {
            userRoles.add(roleService.getRoleByTitle(role.getRoleName()));
        }
        Date date = new Date();
        String password = RandomStringUtils.randomAlphabetic(10);
        User user = new UserImpl(userDto.getEmail(), userDto.getFirstName(), userDto.getSecondName(),
                userDto.getLastName(), password, true, new Timestamp(date.getTime()),null);
        userService.insertUser(user, userRoles);
//        EmailTemplate emailTemplate = emailTemplateService.getById(1L);

//        senderService.send(user.getEmail(), emailTemplate.getTitle(), emailTemplate.getText() + " " + password);
    }


    @RequestMapping(value = "editEmployee", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public void editEmployeeParams(@RequestBody UserDto userDto) {

        User user = userService.getUserByID(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        user.setEmail(userDto.getEmail());
        Set<Role> userRoles = new HashSet<>();
        for (Role role : userDto.getRoleList()) {
            userRoles.add(roleService.getRoleByTitle(role.getRoleName()));
        }
        user.setRoles(userRoles);

        userService.updateUser(user);
    }

    @RequestMapping(value = "changeEmployeeStatus", method = RequestMethod.GET)
    public Boolean changeEmployeeStatus(@RequestParam String email) {
        User user = userService.getUserByUsername(email);
        user.setActive(!user.isActive());
        userService.updateUser(user);
        return user.isActive();
    }

    @RequestMapping(value = "showAssignedStudent", method = RequestMethod.POST)
    public List<Interview> showAssignedStudent(@RequestParam String email) {

        User user = userService.getUserByUsername(email);

        List<Interview> interviewList = interviewService.getByInterviewer(user);

        interviewList.get(0).getApplicationForm().getUser().getFirstName();
        for (Interview interview: interviewList){
            System.out.println(interview.toString());
        }

        Set<User> assignedStudent = userService.getAssignedStudents(user.getId());

        return interviewList;
    }

    @RequestMapping(value = "deleteEmployee", method = RequestMethod.GET)
    public void deleteEmployee(@RequestParam String email){
        System.out.println(email);
        User user = userService.getUserByUsername(email);
        System.out.println(user);
        userService.deleteUser(user);
    }

}
