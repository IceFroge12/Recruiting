package ua.kpi.nc.controller.admin;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.UserDto;
import ua.kpi.nc.persistence.dto.UserRateDto;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
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
import java.util.stream.Collectors;

import static ua.kpi.nc.persistence.model.enums.EmailTemplateEnum.STUDENT_REGISTRATION;

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

    private PasswordEncoderGeneratorService passwordEncoderGeneratorService = PasswordEncoderGeneratorService.getInstance();


    @RequestMapping(value = "showAllEmployees", method = RequestMethod.GET)
    public List<User> showEmployees(@RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol,
                                    @RequestParam boolean increase) {
        return userService.getEmployeesFromToRows(calculateStartRow(pageNum, rowsNum), rowsNum, sortingCol, increase);
    }

    @RequestMapping(value = "showFilteredEmployees", method = RequestMethod.GET)
    public List<User> showFilteredEmployees(@RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol,
                                            @RequestParam boolean increase, @RequestParam Long idStart, @RequestParam Long idFinish,
                                            @RequestParam("rolesId") List<Long> rolesId, @RequestParam boolean interviewer, @RequestParam boolean notInterviewer, @RequestParam boolean notEvaluated) {
        List<Role> neededRoles = rolesId.stream().map(roleService::getRoleById).collect(Collectors.toList());
        System.out.println(increase + " " + pageNum + " " + interviewer + " " + rolesId);
        List<User> res = userService.getFilteredEmployees(calculateStartRow(pageNum, rowsNum), rowsNum, sortingCol, increase, idStart, idFinish, neededRoles, interviewer, notInterviewer, notEvaluated);
        for (User us : res) System.out.println(us);
        return res;
    }

    private Long calculateStartRow(int pageNum, Long rowsNum) {
        return (pageNum - 1) * rowsNum;
    }

    @RequestMapping(value = "getCountOfEmployee", method = RequestMethod.GET)
    public Long getCountOfEmployee() {
        return userService.getAllEmployeeCount();
    }

    @RequestMapping(value = "getCountOfEmployeeFiltered", method = RequestMethod.GET)
    public Long getCountOfEmployeeFiltered(@RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol,
                                           @RequestParam boolean increase, @RequestParam Long idStart, @RequestParam Long idFinish,
                                           @RequestParam("rolesId") List<Long> rolesId, @RequestParam boolean interviewer, @RequestParam boolean notInterviewer, @RequestParam boolean notEvaluated) {
        List<Role> neededRoles = rolesId.stream().map(roleService::getRoleById).collect(Collectors.toList());
        System.out.println(increase + " " + pageNum + " " + interviewer + " " + rolesId);
        return userService.getAllEmployeeCountFiltered(calculateStartRow(pageNum, rowsNum), rowsNum, sortingCol, increase, idStart, idFinish, neededRoles, interviewer, notInterviewer, notEvaluated);
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public List<User> searchEmployee(@RequestParam String lastName) {
        return userService.getEmployeesByNameFromToRows(lastName);
    }


    @RequestMapping(value = "addEmployee", method = RequestMethod.POST)
    public void addEmployee(@RequestBody UserDto userDto) throws MessagingException {
        System.out.println(userDto.toString());
        List<RoleImpl> roles = userDto.getRoleList();
        List<Role> userRoles = new ArrayList<>();
        for (Role role : roles) {
            userRoles.add(roleService.getRoleByTitle(role.getRoleName()));
        }
        Date date = new Date();
        String password = RandomStringUtils.randomAlphabetic(10);
        User user = new UserImpl();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoderGeneratorService.encode(password));
        user.setActive(true);
        user.setRegistrationDate(new Timestamp(date.getTime()));

        userService.insertUser(user, userRoles);
        user.setPassword(password);
        EmailTemplate emailTemplate = emailTemplateService.getById(STUDENT_REGISTRATION.getId());
        String template = emailTemplateService.showTemplateParams(emailTemplate.getText(), user);
        senderService.send(user.getEmail(), emailTemplate.getTitle(), template);
    }


    @RequestMapping(value = "editEmployee", method = RequestMethod.POST)
    public void editEmployeeParams(@RequestBody UserDto userDto) {
//        System.out.println("Зашли в контроллер");
//        System.out.println(userDto);

        User user = userService.getUserByID(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        Set<Role> userRoles = new HashSet<>();
        for (Role role : userDto.getRoleList()) {
            userRoles.add(roleService.getRoleByTitle("ROLE_" + role.getRoleName()));
        }
        user.setRoles(userRoles);
        //System.out.println(user);
        //userService.updateUser(user);
        userService.updateUserWithRole(user);
//        User userl = userService.getUserByID(2536L);
//        for(Role role:userl.getRoles())System.out.println(role.getRoleName());
    }

    @RequestMapping(value = "changeEmployeeStatus", method = RequestMethod.GET)
    public Boolean changeEmployeeStatus(@RequestParam String email) {
        User user = userService.getUserByUsername(email);
        user.setActive(!user.isActive());
        userService.updateUser(user);
        return user.isActive();
    }

    @RequestMapping(value = "showAssignedStudent", method = RequestMethod.POST)
    public List<UserRateDto> showAssignedStudent(@RequestParam String email) {
        User user = userService.getUserByUsername(email);
        List<UserRateDto> userRateDtos = new ArrayList<>();
        for (Interview interview : interviewService.getByInterviewer(user)) {
            UserRateDto userRateDto = new UserRateDto(interview.getApplicationForm().getUser(),
                    interview.getMark(),
                    interview.getRole(),
                    interview.getId());
            userRateDtos.add(userRateDto);
        }
        return userRateDtos;
    }

    @RequestMapping(value = "deleteEmployee", method = RequestMethod.GET)
    public void deleteEmployee(@RequestParam String email) {
        System.out.println(email);
        User user = userService.getUserByUsername(email);
        System.out.println(user);
        userService.deleteUser(user);
    }

    @RequestMapping(value = "deleteAssignedStudent", method = RequestMethod.POST)
    public void deleteAssignedStudent(@RequestParam String idInterview) {
        Interview interview = interviewService.getById(Long.parseLong(idInterview));
        if (null == interview) {
            //// TODO: 12.05.2016 someaction;
        } else {
            interviewService.deleteInterview(interview);
        }
    }

    @RequestMapping(value = "getActiveEmployee", method = RequestMethod.GET)
    public List<String> getActiveEmployee() {

        Long activeTech = userService.getActiveEmployees(RoleEnum.ROLE_TECH.getId(),
                RoleEnum.ROLE_SOFT.getId());

        Long activeSoft = userService.getActiveEmployees(RoleEnum.ROLE_SOFT.getId(),
                RoleEnum.ROLE_TECH.getId());

        List<String> activeEmployees = new ArrayList<>();
        activeEmployees.add(String.valueOf(activeTech));
        activeEmployees.add(String.valueOf(activeSoft));
        return activeEmployees;
    }


    @RequestMapping(value = "getMaxId", method = RequestMethod.GET)
    public Long getMaxId() {
        return userService.getUserCount();
    }

    @RequestMapping(value = "hasNotMarked", method = RequestMethod.GET)
    public List<String> hasNotMarked(@RequestParam List<String> emails) {
        List<String> notMarkedAll = new ArrayList<>();
        for (String email : emails) {
            User user = userService.getUserByUsername(email);
            List<Interview> interviews = interviewService.getByInterviewer(user);
            boolean foundNotMarked = false;
            for (Interview interview : interviews) {
                if (null == interview.getMark())
                    foundNotMarked = true;
            }
            if (foundNotMarked)
                notMarkedAll.add(email);
        }

        return notMarkedAll;
    }

}
