package ua.kpi.nc.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.RecruitmentService;
import ua.kpi.nc.service.ServiceFactory;

/**
 * Created by dima on 23.04.16.
 */
@RestController
@RequestMapping("/admin")
public class AdminMainController {

    private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();

    @RequestMapping(value = "recruitment", method = RequestMethod.POST)
    public Recruitment getRecruitmentData() {
        if(null == recruitmentService.getCurrentRecruitmnet()){
           return recruitmentService.getLastRecruitment();
        }
        return recruitmentService.getCurrentRecruitmnet();
    }

    @RequestMapping(value = "getCurrentRecruitmentStudents", method = RequestMethod.GET)
    public Long getCurrentRecruitmentStudents() {
        return applicationFormService.getCountRecruitmentStudents(recruitmentService.getCurrentRecruitmnet().getId());
    }

}
