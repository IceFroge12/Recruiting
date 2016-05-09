package ua.kpi.nc.controller.staff;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.controller.auth.UserAuthentication;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.RecruitmentService;
import ua.kpi.nc.service.ServiceFactory;

/**
 * Created by Chalienko on 04.05.2016.
 */
@Controller
@RequestMapping("/staff")
public class StaffMainController {

    private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();

    @RequestMapping(value = "recruitment", method = RequestMethod.POST)
    @ResponseBody
    public Recruitment getRecruitmentData() {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        System.out.println(recruitment);
        return recruitment;
    }
}
