package ua.kpi.nc.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.ServiceFactory;

/**
 * Created by Алексей.
 */
@Controller
@RequestMapping("/student")
public class StudentFeedbackController {
    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();

    @RequestMapping(value = "feedback/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void getFeedback(@PathVariable("id") long id, @PathVariable("feedback") String feedback){
    ApplicationForm ap=applicationFormService.getApplicationFormById(id);
        ap.setFeedback(feedback);
    applicationFormService.updateApplicationForm(ap);
        System.out.println("Feedback was update");
    }

}
