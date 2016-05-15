package ua.kpi.nc.controller.admin;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.InterviewService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.util.export.ExportApplicationForm;
import ua.kpi.nc.util.export.ExportApplicationFormImp;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Korzh
 */

@RestController
@RequestMapping("/admin")
public class AdminAppFormInterviewController {

    private InterviewService interviewService = ServiceFactory.getInterviewService();
    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();

    @RequestMapping(value = "getApplicationForm/{studentId}", method = RequestMethod.POST)
    public String getApplicationForm(@PathVariable Long studentId) {
        ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(studentId);
        Gson applicationFormGson = GsonFactory.getApplicationFormGson();
        String jsonResult = applicationFormGson.toJson(applicationForm);
        return jsonResult;
    }

    @RequestMapping(value = "getRolesInterview/{applicationFormId}", method = RequestMethod.GET)
    public Set<Role> getInterviewRoles(@PathVariable Long applicationFormId) {
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
        List<Interview> interviews = interviewService.getByApplicationForm(applicationForm);
        Set<Role> interviewRoles = new HashSet<>();
        for( Interview interview : interviews){
            interviewRoles.add(interview.getRole());
        }
        return interviewRoles;
    }
    @RequestMapping(value = "getInterview/{applicationFormId}/{role}", method = RequestMethod.POST)
    public String getInterview(@PathVariable Long applicationFormId, @PathVariable Long role) {
        Interview interview = null;
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
        List<Interview> interviews = interviewService.getByApplicationForm(applicationForm);
        for (Interview i : interviews) {
            if (i.getRole().getId().equals(role)) {
                interview = i;
            }
        }
        Gson interviewGson = GsonFactory.getInterviewGson();
        String jsonResult = interviewGson.toJson(interview);
        return jsonResult;
    }
    @RequestMapping(value = "getAdequateMark/{applicationFormId}", method = RequestMethod.GET)
    public boolean getAdequateMark(@PathVariable Long applicationFormId) {
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
        return interviewService.haveNonAdequateMarkForAdmin(applicationFormId);
    }

    @RequestMapping(value = "appForm/{applicationFormId}", method = RequestMethod.GET)
    public void exportAppform(@PathVariable Long applicationFormId, HttpServletResponse response) throws Exception {
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
        ExportApplicationForm pdfAppForm = new ExportApplicationFormImp();
        response.setHeader("Content-Disposition", String.format("inline; filename=ApplicationForm.pdf"));
        response.setContentType("application/pdf");
        pdfAppForm.export(applicationForm, response);
    }



}
