package ua.kpi.nc.controller.student;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

/**
 * Created by dima on 14.04.16.
 */
@Controller
@RequestMapping("/student")
public class StudentApplicationFormController {

    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
    private UserService userService = ServiceFactory.getUserService();

    @RequestMapping("/student")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("student");
        return modelAndView;
    }

    @RequestMapping(value = "applicationForm", method = RequestMethod.GET)
    public ModelAndView studentpage() {
        ModelAndView modelAndView = new ModelAndView("studentAppl");
        return modelAndView;
    }

    @RequestMapping(value = "applicationForm", method = RequestMethod.POST)
    @ResponseBody
    public String getApplicationForm() {
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(1L);
        Gson applicationFormGson = GsonFactory.getApplicationFormGson();
        String jsonResult = applicationFormGson.toJson(applicationForm);
        return jsonResult;
    }
}