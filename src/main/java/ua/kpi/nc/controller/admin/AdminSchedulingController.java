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
@RequestMapping("scheduling")
public class AdminSchedulingController {

    RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
    ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();

    @RequestMapping(value = "getStudentCount", method = RequestMethod.POST)
    public Long getCountStudents(){
       Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        System.out.println(recruitment.getId());
        return applicationFormService.getCountRecruitmentStudents(recruitment.getId());
    }

}
