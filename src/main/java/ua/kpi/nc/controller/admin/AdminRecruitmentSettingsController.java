package ua.kpi.nc.controller.admin;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.nc.persistence.dto.RecruitmentSettingsDto;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.impl.real.RecruitmentImpl;
import ua.kpi.nc.service.RecruitmentService;
import ua.kpi.nc.service.ServiceFactory;

import java.sql.Timestamp;

/**
 * Created by dima on 30.04.16.
 */
@RestController
@RequestMapping("/admin")
public class AdminRecruitmentSettingsController {

    private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();

    @RequestMapping(value = "/addRecruitment", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    private void addRecruitmentSettings(@RequestBody RecruitmentSettingsDto recruitmentDto) {
        System.out.println(recruitmentDto.toString());

        Recruitment recruitment = new RecruitmentImpl(recruitmentDto.getName(), recruitmentDto.getMaxAdvancedGroup(), recruitmentDto.getMaxGeneralGroup(),
                Timestamp.valueOf(recruitmentDto.getRegistrationDeadline()), Timestamp.valueOf(recruitmentDto.getScheduleChoicesDeadline()));

        recruitmentService.addRecruitment(recruitment);
    }

}
