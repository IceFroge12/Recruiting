package ua.kpi.nc.controller.student;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.controller.auth.UserAuthentication;
import ua.kpi.nc.persistence.dto.ApplicationFormDto;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;


@RestController
@RequestMapping("/student")
public class StudentFeedbackController {

    private ApplicationFormService applicationFormService;
    private UserService userService;

    public StudentFeedbackController() {
        applicationFormService = ServiceFactory.getApplicationFormService();
        userService = ServiceFactory.getUserService();
        applicationFormService = ServiceFactory.getApplicationFormService();
    }

    @RequestMapping(value = "saveFeedBack", method = RequestMethod.POST)
    public void getFeedback(@RequestParam String feedBack){
        ApplicationForm applicationForm = applicationFormService.getLastApplicationFormByUserId(((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getDetails().getId());
        applicationForm.setFeedback(feedBack);
        applicationFormService.updateApplicationForm(applicationForm);
    }

    @RequestMapping(value = "getFeedBack", method = RequestMethod.GET)
    public ApplicationFormDto getFeedBack(@RequestParam String id){
        User user = userService.getUserByID(Long.parseLong(id));
        if (null != user){
            return new ApplicationFormDto(applicationFormService.getLastApplicationFormByUserId(user.getId()).getFeedback());
        }else {
            return null;
        }
    }
}
