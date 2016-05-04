package ua.kpi.nc.controller.student;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.dto.ApplicationFormDto;
import ua.kpi.nc.persistence.dto.StudentAppFormQuestionDto;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

import javax.mail.MessagingException;

/**
 * Created by dima on 14.04.16.
 */
@Controller
@RequestMapping("/student")
public class StudentApplicationFormController {

    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
    private UserService userService = ServiceFactory.getUserService();

    @RequestMapping(value ="appform", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("studentappform");
        return modelAndView;
    }

//    @RequestMapping(value = "applicationForm", method = RequestMethod.GET)
//    public ModelAndView studentpage() {
//        ModelAndView modelAndView = new ModelAndView("studentAppl");
//        return modelAndView;
//    }

    @RequestMapping(value = "appform", method = RequestMethod.POST)
    @ResponseBody
    public String getApplicationForm() {
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(1L);
        Gson applicationFormGson = GsonFactory.getApplicationFormGson();
        String jsonResult = applicationFormGson.toJson(applicationForm);
        return jsonResult;
    }
    // headers = {"Content-type=application/json"}
    @RequestMapping(value = "saveApplicationForm", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public void addUsername(@RequestBody ApplicationFormDto applicationFormDto) throws MessagingException {
        for (StudentAppFormQuestionDto q : applicationFormDto.getQuestions()){
            System.out.println(q.toString());
        }
        System.out.println(applicationFormDto.getUser().toString());
//        System.out.println(applicationFormDto.getStatus());
//        System.out.println(applicationFormDto.getQuestions());
        User user = userService.getUserByID(49L);
        user.setLastName(applicationFormDto.getUser().getLastName());
        user.setFirstName(applicationFormDto.getUser().getFirstName());
        user.setSecondName(applicationFormDto.getUser().getSecondName());
        userService.updateUser(user);
        //System.out.println(firstName);
        //user.setFirstName(firstName);

        //userService.insertUser(user,roles);
//        EmailTemplate emailTemplate = emailTemplateService.getById(1L);

//        senderService.send(user.getEmail(), emailTemplate.getTitle(), emailTemplate.getText() + " " + password);

    }
}