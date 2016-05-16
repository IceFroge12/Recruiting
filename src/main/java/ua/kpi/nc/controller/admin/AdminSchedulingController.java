package ua.kpi.nc.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.nc.persistence.dto.SchedulingSettingDto;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.RecruitmentService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

/**
 * Created by dima on 23.04.16.
 */
@RestController
@RequestMapping("scheduling")
public class AdminSchedulingController {

    RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
    ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
    UserService userService = ServiceFactory.getUserService();

    @RequestMapping(value = "getStudentCount", method = RequestMethod.GET)
    public SchedulingSettingDto getCountStudents() {

        Long activeTech = userService.getActiveEmployees((long) RoleEnum.ROLE_TECH.getId(),
                (long) RoleEnum.ROLE_SOFT.getId());

        Long activeSoft = userService.getActiveEmployees((long) RoleEnum.ROLE_SOFT.getId(),
                (long) RoleEnum.ROLE_TECH.getId());

        return new SchedulingSettingDto(
                applicationFormService.getCountRecruitmentStudents(recruitmentService.getCurrentRecruitmnet().getId()),
                activeSoft,
                activeTech
        );
    }




}
