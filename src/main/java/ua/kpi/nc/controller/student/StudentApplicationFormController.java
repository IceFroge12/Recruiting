package ua.kpi.nc.controller.student;

import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.dto.UserDto;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
    @RequestMapping(value = "changeUsername", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public void addUsername(@RequestBody String firstName) throws MessagingException {
        System.out.println(firstName);
        User user = userService.getUserByID(49L);
        System.out.println(firstName);
        //user.setFirstName(firstName);

        //userService.insertUser(user,roles);
//        EmailTemplate emailTemplate = emailTemplateService.getById(1L);

//        senderService.send(user.getEmail(), emailTemplate.getTitle(), emailTemplate.getText() + " " + password);

    }
}