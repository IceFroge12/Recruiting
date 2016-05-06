package ua.kpi.nc.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.nc.service.RecruitmentService;
import ua.kpi.nc.service.ServiceFactory;

/**
 * Created by dima on 30.04.16.
 */
@RestController
public class AdminRecruitmentSettingsController {

    private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();

    @RequestMapping(value = "/addRecruitment", method = RequestMethod.POST)
    private void addRecruitmentSettings(){

//        recruitmentService.addRecruitment();

    }

}
