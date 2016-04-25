package ua.kpi.nc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.impl.proxy.RecruitmentProxy;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;
import ua.kpi.nc.persistence.model.impl.real.RecruitmentImpl;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.FormAnswerService;
import ua.kpi.nc.service.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 23.04.16.
 */
@Controller
@RequestMapping("/admin")
public class AdminStudentManagementController {

    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();


    @RequestMapping(value = "studentmanagement", method = RequestMethod.GET)
    public ModelAndView studentManagement() {
        ModelAndView modelAndView = new ModelAndView("adminsudentmanagement");
        return modelAndView;
    }

    @RequestMapping(value = "getallstudent", method = RequestMethod.POST)
    @ResponseBody
    public ApplicationForm getAllStudents() {

        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(1L);
//        System.out.println("APPFORM"+applicationForm.toString());
        Recruitment recruitment =  applicationForm.getRecruitment();


        applicationForm.setRecruitment(recruitment);
//        Recruitment recruitmentMediate = ;

        System.out.println(recruitment.getId());
        System.out.println("REC"+recruitment.toString());

        ApplicationForm applicationFormMediate = new ApplicationFormImpl();
//        List<ApplicationForm> applicationForms = new ArrayList<>();
//        applicationForms.add(applicationForm);
//        applicationForms.add(applicationForm);
//        applicationForms.add(applicationForm);
        //TODO change method types and select method  not work dao
        return applicationForm;
    }

    @RequestMapping(value = "confirmSelection", method = RequestMethod.POST)
    @ResponseBody
    public void confirmSelection(){
        //TODO
    }






}
