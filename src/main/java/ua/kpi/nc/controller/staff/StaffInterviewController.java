package ua.kpi.nc.controller.staff;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.service.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @RequestMapping(value = "getInterview/{applicationFormId}/{role}", method = RequestMethod.POST)
    public String getInterview(@PathVariable Long applicationFormId, @PathVariable Long role) {
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
        List<Interview> interviews = interviewService.getByApplicationForm(applicationForm);
        Interview interview = null;
        for (Interview i : interviews){
            if(i.getRole().getId().equals(role)){
                interview = i;
            }
        }
        Gson interviewGson = GsonFactory.getInterviewGson();
        String jsonResult = interviewGson.toJson(interview);
        return jsonResult;
    }

    @RequestMapping(value = "submitInterview", method = RequestMethod.POST)

    public void saveApplicationForm(@RequestBody String jsonInterviewDto) {

        System.out.println(jsonInterviewDto);
    }

    @RequestMapping(value = "getRoles/{applicationFormId}", method = RequestMethod.GET)
    public Set<Role> getRoles(@PathVariable Long applicationFormId) {
        User interviwer = userService.getAuthorizedUser();
        List<Interview> interviews = new ArrayList<>();
        for(Interview interview : interviewService.getByInterviewer(interviwer)){
            if(interview.getApplicationForm().getId().equals(applicationFormId)){
                interviews.add(interview);
            }
        }
        Set<Role> interviwerRoles = new HashSet<>();
        for(Role role : interviwer.getRoles()) {
            for (Interview i : interviews){
                if(i.getRole().getId().equals(role.getId())){
                    interviwerRoles.add(role);
                }
            }
        }
        return interviwerRoles;
    }







}
