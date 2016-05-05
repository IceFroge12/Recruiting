package ua.kpi.nc.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.ServiceFactory;


@Controller
@RequestMapping("/student")
public class StudentFeedbackController {
    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();

    @RequestMapping(value = "/feedback/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void getFeedback(@PathVariable("id") long id, @RequestBody String feedback){
    ApplicationForm ap=applicationFormService.getApplicationFormById(id);
        ap.setFeedback(feedback);
    applicationFormService.updateApplicationForm(ap);
        System.out.println("Feedback was update");
    }

}
