package ua.kpi.nc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.dao.RecruitmentDAO;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.impl.real.RecruitmentImpl;
import ua.kpi.nc.service.RecruitmentService;
import ua.kpi.nc.service.ServiceFactory;

import java.util.List;
import java.util.Set;

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
        Recruitment recruitment1 = recruitmentService.getRecruitmentById(1L);//TODO change method
        return recruitment1;
    }
}
