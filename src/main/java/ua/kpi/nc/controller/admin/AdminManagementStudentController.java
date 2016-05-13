package ua.kpi.nc.controller.admin;

import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.StudentAppFormDto;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.Status;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.service.*;

import java.util.ArrayList;
import java.util.List;

import static ua.kpi.nc.persistence.model.enums.StatusEnum.*;

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
    private StatusService statusService = ServiceFactory.getStatusService();

    @RequestMapping(value = "showAllStudents", method = RequestMethod.GET)
    public List<StudentAppFormDto> showStudents(@RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol,
                                                @RequestParam boolean increase) {
        Long fromRow = (pageNum - 1) * rowsNum;
        List<StudentAppFormDto> studentAppFormDtoList = new ArrayList<>();
        List<ApplicationForm> applicationForms = applicationFormService.getCurrentsApplicationForms(fromRow, rowsNum, sortingCol, increase);
        for (ApplicationForm applicationForm : applicationForms) {
            studentAppFormDtoList.add(new StudentAppFormDto(applicationForm.getUser().getId(),
                    applicationForm.getId(), applicationForm.getUser().getFirstName(),
                    applicationForm.getUser().getLastName(), applicationForm.getStatus().getTitle(),
                    getPossibleStatus(applicationForm.getStatus())));
            System.out.println(studentAppFormDtoList.get(studentAppFormDtoList.size()-1));
        }

        return studentAppFormDtoList;
    }

    private List<Status> getPossibleStatus(Status status) {
        List<Status> statusList = new ArrayList<>();

        if (status.getTitle().equals(valueOf(IN_REVIEW))) {
            statusList.add(new Status(valueOf(APPROVED)));
            statusList.add(new Status(valueOf(REJECTED)));
        }else {
            statusList.add(new Status(valueOf(APPROVED)));
            statusList.add(new Status(valueOf(REJECTED)));
            statusList.add(new Status(valueOf(APPROVED_TO_ADVANCED_COURSES)));
            statusList.add(new Status(valueOf(APPROVED_TO_GENERAL_COURSES)));
            statusList.add(new Status(valueOf(APPROVED_TO_JOB)));
        }
        return statusList;
    }

    @RequestMapping(value = "getCountOfStudents", method = RequestMethod.GET)
    public Long getCountOfStudents() {

        return userService.getAllStudentCount();
    }

    @RequestMapping(value = "getAllStatuses", method = RequestMethod.GET)
    public List<Status> getAllStatuses() {
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
    public Long getRejectCount() {
        return applicationFormService.getCountRejectedAppForm();
    }

    @RequestMapping(value = "getJobCount", method = RequestMethod.GET)
    public Long getJobCount() {
        return applicationFormService.getCountToWorkAppForm();
    }

    @RequestMapping(value = "getAdvancedCount", method = RequestMethod.GET)
    public Long getAdvancedCount() {
        return applicationFormService.getCountAdvancedAppForm();
    }

    @RequestMapping(value = "getGeneralCount", method = RequestMethod.GET)
    public Long getGeneralCount() {
        return applicationFormService.getCountGeneralAppForm();
    }

    @RequestMapping(value = "confirmSelection", method = RequestMethod.POST)
    public boolean changeStatus(@RequestParam Long id, @RequestBody Status status) {
        System.out.println(id + "\n" + status);
//        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
//        af.setStatus(status);
        return true;
    }

    @RequestMapping(value = "searchStudent", method = RequestMethod.POST)
    public List<User> searchStudentById(@RequestParam String lastName,
                                        @RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol) {
        Long fromRow = (pageNum - 1) * rowsNum;
        return userService.getStudentsByNameFromToRows(lastName, fromRow, rowsNum, sortingCol);
    }

}
