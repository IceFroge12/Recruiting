package ua.kpi.nc.controller.staff;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.service.*;

import java.util.List;

/**
 * @author Korzh
 */
@RestController
@RequestMapping("/staff")
public class StaffInterviewController {

    private FormAnswerService formAnswerService;
    private ApplicationFormService applicationFormService;
    private UserService userService;
    private FormQuestionService formQuestionService;
    private FormAnswerVariantService formAnswerVariantService;
    private RoleService roleService;
    private InterviewService interviewService;

    public StaffInterviewController() {
        formAnswerService = ServiceFactory.getFormAnswerService();
        applicationFormService = ServiceFactory.getApplicationFormService();
        userService = ServiceFactory.getUserService();
        formQuestionService = ServiceFactory.getFormQuestionService();
        formAnswerVariantService = ServiceFactory.getFormAnswerVariantService();
        roleService = ServiceFactory.getRoleService();
        interviewService = ServiceFactory.getInterviewService();
    }

    @RequestMapping(value = "getApplicationForm/{applicationFormId}", method = RequestMethod.POST)
    public String getApplicationForm(@PathVariable Long applicationFormId) {
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
        Gson applicationFormGson = GsonFactory.getApplicationFormGson();
        String jsonResult = applicationFormGson.toJson(applicationForm);
        return jsonResult;
    }

    @RequestMapping(value = "getInterview/{applicationFormId}", method = RequestMethod.POST)
    public String getInterview(@PathVariable Long applicationFormId, @RequestParam String role) {
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
        List<Interview> interviews = interviewService.getByApplicationForm(applicationForm);
        Interview interview = interviews.get(0);
        System.out.println(interview.toString());
        Gson interviewGson = GsonFactory.getInterviewGson();
        String jsonResult = interviewGson.toJson(interview);
        return jsonResult;
    }




}
