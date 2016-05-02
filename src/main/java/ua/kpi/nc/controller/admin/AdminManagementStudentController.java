package ua.kpi.nc.controller.admin;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 23.04.16.
 */
@Controller
@RequestMapping("/admin")
public class AdminManagementStudentController {

    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();

//    @RequestMapping(value = "studentmanagement", method = RequestMethod.GET)
//    public ModelAndView studentManagement() {
//        ModelAndView modelAndView = new ModelAndView("adminsudentmanagement");
//        return modelAndView;
//    }

    @RequestMapping(value = "getallstudent", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getAllStudents() {

        List<String> allAppFormList = new ArrayList<>();
        System.out.println("lol");
        List<ApplicationForm> applicationForms = applicationFormService.getAll();

        for (ApplicationForm applicationForm : applicationForms) {
            Gson applicationFormGson = GsonFactory.getApplicationFormGson();

            String jsonResult = applicationFormGson.toJson(applicationForm);
            allAppFormList.add(jsonResult);
            System.out.println(jsonResult);
        }
        return allAppFormList;
    }

    @RequestMapping(value = "confirmSelection", method = RequestMethod.POST)
    @ResponseBody
    public void confirmSelection() {
        //TODO
    }


}
