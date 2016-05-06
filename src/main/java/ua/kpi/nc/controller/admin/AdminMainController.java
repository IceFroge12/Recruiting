package ua.kpi.nc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.service.RecruitmentService;
import ua.kpi.nc.service.ServiceFactory;

/**
 * Created by dima on 23.04.16.
 */
@Controller
@RequestMapping("/admin")
public class AdminMainController {

    private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("adminmain");
        return modelAndView;
    }


    @RequestMapping(value = "recruitment", method = RequestMethod.POST)
    @ResponseBody
    public Recruitment getRecruitmentData() {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();//TODO change method
        System.out.println(recruitment.toString());
        return recruitment;
    }
}
