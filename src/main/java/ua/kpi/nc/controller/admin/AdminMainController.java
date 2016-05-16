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
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        System.out.println(recruitment.toString());
        return recruitment;
    }

    @RequestMapping(value = "getCurrentRecruitmentStudents", method = RequestMethod.GET)
    public Long getCurrentRecruitmentStudents() {
        Long studentCount = applicationFormService.getCountRecruitmentStudents(recruitmentService.getCurrentRecruitmnet().getId());
        return studentCount;
    }

}
