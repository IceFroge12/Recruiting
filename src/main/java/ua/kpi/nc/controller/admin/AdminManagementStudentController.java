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
    public List<User> showStudents(@RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol,
                                    @RequestParam boolean increase) {
        Long fromRow = (pageNum - 1) * rowsNum;
        return userService.getStudentsFromToRows(fromRow,rowsNum, sortingCol, increase);
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
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        List<FormAnswer> formAnswer = formAnswerService.getByApplicationFormAndQuestion(af, formQuestionService.getById(8L));
        return formAnswer.get(0);
    }


    @RequestMapping(value = "getCourse", method = RequestMethod.GET)
    public FormAnswer getCourseById(@RequestParam Long id) {
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        List<FormAnswer> formAnswer = formAnswerService.getByApplicationFormAndQuestion(af, formQuestionService.getById(9L));
        return formAnswer.get(0);
    }

    @RequestMapping(value = "getRejectCount", method = RequestMethod.GET)
    public Long getRejectCount(){
        return ServiceFactory.getApplicationFormService().getCountRejectedAppForm();
    }

    @RequestMapping(value = "getJobCount", method = RequestMethod.GET)
    public Long getJobCount(){
        return ServiceFactory.getApplicationFormService().getCountToWorkAppForm();
    }

    @RequestMapping(value = "getAdvancedCount", method = RequestMethod.GET)
    public Long getAdvancedCount(){
        return ServiceFactory.getApplicationFormService().getCountAdvancedAppForm();
    }

    @RequestMapping(value = "getGeneralCount", method = RequestMethod.GET)
    public Long getGeneralCount(){
        return ServiceFactory.getApplicationFormService().getCountGeneralAppForm();
    }

    @RequestMapping(value = "confirmSelection", method = RequestMethod.POST)
    public boolean changeStatus(@RequestParam Long id, @RequestBody Status status) {
        System.out.println(id+"\n"+status);
//        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
//        af.setStatus(status);
        return true;
    }

}
