package ua.kpi.nc.controller.admin;

import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.impl.real.RoleImpl;
import ua.kpi.nc.service.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dima on 23.04.16.
 */
@RestController
@RequestMapping("/admin")
public class AdminManagementStudentController {

    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
    private RoleService roleService = ServiceFactory.getRoleService();
    private UserService userService = ServiceFactory.getUserService();
    private FormAnswerService formAnswerService = ServiceFactory.getFormAnswerService();
    private FormQuestionService formQuestionService = ServiceFactory.getFormQuestionService();
    private StatusService statusService=ServiceFactory.getStatusService();


    @RequestMapping(value = "showAllStudents", method = RequestMethod.GET)
    public List<User> getAllStudents(@RequestParam int pageNum) {
        Long itemsByPage = 9L;
        Long fromRow = (pageNum - 1) * itemsByPage;
        List<User> students = userService.getStudentsFromToRows(fromRow);
        return students;
    }

    @RequestMapping(value = "getAllStatuses", method = RequestMethod.GET)
    public List<Status> getAllStatuses(){
        return statusService.getAllStatuses();
    }

    @RequestMapping(value = "getStatus", method = RequestMethod.GET)
    public Status getStatusById(@RequestParam Long id) {
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        return af.getStatus();
    }

    @RequestMapping(value = "getUniverse", method = RequestMethod.GET)
    public FormAnswer getUniverseById(@RequestParam Long id) {
        System.out.println("Зашли 8");
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        System.out.println("get app form 8");
        List<FormAnswer> formAnswer = formAnswerService.getByApplicationFormAndQuestion(af, formQuestionService.getById(8L));
        if(formAnswer==null)System.out.println("form answer = NULL");
        System.out.println("get form answer 8");
        System.out.println("Перед отправкой 8"+formAnswer.get(0));
        return formAnswer.get(0);
    }


    @RequestMapping(value = "getCourse", method = RequestMethod.GET)
    public FormAnswer getCourseById(@RequestParam Long id) {
        System.out.println("Зашли 9");
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        System.out.println("get app form 9");
        List<FormAnswer> formAnswer = formAnswerService.getByApplicationFormAndQuestion(af, formQuestionService.getById(9L));
        if(formAnswer==null)System.out.println("form answer = NULL");
        System.out.println("get form answer 9");
        System.out.println("Перед отправкой 9"+formAnswer.get(0));
        return formAnswer.get(0);
    }

    @RequestMapping(value = "confirmSelection", method = RequestMethod.POST)
    public boolean changeStatus(@RequestParam Long id, @RequestBody Status status) {
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        af.setStatus(status);
        return true;
    }

}
