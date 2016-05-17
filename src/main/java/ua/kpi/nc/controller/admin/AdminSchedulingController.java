package ua.kpi.nc.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.nc.persistence.dto.ScheduleOverallDto;
import ua.kpi.nc.persistence.dto.SchedulingSettingDto;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.impl.real.ScheduleTimePointImpl;
import ua.kpi.nc.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 23.04.16.
 */
@RestController
@RequestMapping("scheduling")
public class AdminSchedulingController {

    RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
    ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
    UserService userService = ServiceFactory.getUserService();
    ScheduleTimePointService timePointService = ServiceFactory.getScheduleTimePointService();

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

    @RequestMapping(value = "getCurrentSchedule", method = RequestMethod.GET)
    public List<ScheduleOverallDto> getCurrentSchedule() {
        List<ScheduleOverallDto> scheduleOverall = new ArrayList<>();
        List<ScheduleTimePoint> allTimePoints = timePointService.getAll();
        for (ScheduleTimePoint timePoint : allTimePoints) {
            ScheduleOverallDto oneTimePoint = new ScheduleOverallDto(timePoint.getTimePoint().toString());
            Map<Long,Long> numberForEachRole = timePointService.getUsersNumberInFinalTimePoint(timePoint.getTimePoint());
            setUsersNumberToEachRole(numberForEachRole, oneTimePoint);
            scheduleOverall.add(oneTimePoint);
        }
        return scheduleOverall;
    }

    private void setUsersNumberToEachRole(Map<Long,Long> numberForEachRole, ScheduleOverallDto timePoint) {
        if (numberForEachRole == null) {
            timePoint.setAmountOfStudents(0);
            timePoint.setAmountOfSoft(0);
            timePoint.setAmountOfTech(0);
        } else {
            if (numberForEachRole.get((long)RoleEnum.ROLE_STUDENT.getId()) == null) {
                timePoint.setAmountOfStudents(0);
            } else {
                timePoint.setAmountOfStudents(numberForEachRole.get((long)RoleEnum.ROLE_STUDENT.getId()));
            }
            if (numberForEachRole.get((long)RoleEnum.ROLE_SOFT.getId()) == null) {
                timePoint.setAmountOfSoft(0);
            } else {
                timePoint.setAmountOfSoft(numberForEachRole.get((long)RoleEnum.ROLE_SOFT.getId()));
            }
            if (numberForEachRole.get((long)RoleEnum.ROLE_TECH.getId()) == null) {
                timePoint.setAmountOfTech(0);
            } else {
                timePoint.setAmountOfTech(numberForEachRole.get((long)RoleEnum.ROLE_TECH.getId()));
            }
        }
    }

}
