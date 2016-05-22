package ua.kpi.nc.controller.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger log = LoggerFactory.getLogger(RegistrationController.class.getName());

    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
    private UserService userService = ServiceFactory.getUserService();


    @RequestMapping(value = "saveFeedBack", method = RequestMethod.POST)
    public void getFeedback(@RequestParam String feedBack){
        log.info("Getting last application form");
        ApplicationForm applicationForm = applicationFormService.getLastApplicationFormByUserId(
                ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getDetails().getId());
        applicationForm.setFeedback(feedBack);
        log.info("Save feedback in application form id - {}", applicationForm.getId());
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
