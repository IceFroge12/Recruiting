package ua.kpi.nc.controller.admin;

import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.service.*;

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


    @RequestMapping(value = "getAllStudents", method = RequestMethod.GET)
    public List<User> getAllStudents(@RequestParam int pageNum) {
        Long itemsByPage = 9L;
        Long fromRow = (pageNum - 1) * itemsByPage;
        List<User> students = userService.getStudentsFromToRows(fromRow);
        return students;
    }

    @RequestMapping(value = "getStatus", method = RequestMethod.GET)
    public Status getStatusById(@RequestParam Long id) {
        System.out.println("Request id =" + id);
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        return af.getStatus();
    }

    @RequestMapping(value = "getUniverse", method = RequestMethod.GET)
    public FormAnswer getUniverseById(@RequestParam Long id) {
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        List<FormAnswer> formAnswer = formAnswerService.getByApplicationFormAndQuestion(af, formQuestionService.getById(3L));
        return formAnswer.get(0);
    }


    @RequestMapping(value = "getCourse", method = RequestMethod.GET)
    public FormAnswer getCourseById(@RequestParam Long id) {
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        List<FormAnswer> formAnswer = formAnswerService.getByApplicationFormAndQuestion(af, formQuestionService.getById(1L));
        return formAnswer.get(0);
    }

    @RequestMapping(value = "changeStudentsStatus", method = RequestMethod.POST)
    public boolean changeStatus(@RequestParam Long id, @RequestBody Status status) {
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        af.setStatus(status);
        return true;
    }

    ;


    @RequestMapping(value = "confirmSelection", method = RequestMethod.POST)
    @ResponseBody
    public void confirmSelection() {
        //TODO
    }


}
