package ua.kpi.nc.controller.admin;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.nc.persistence.model.SchedulingSettings;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.enums.SchedulingStatusEnum;
import ua.kpi.nc.service.*;

import java.lang.reflect.WildcardType;
import java.sql.Timestamp;
import java.util.List;

import ua.kpi.nc.persistence.model.impl.real.ScheduleTimePointImpl;
import ua.kpi.nc.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dima on 23.04.16.
 */
@RestController
@RequestMapping("scheduling")
public class AdminSchedulingController {

    private final static long HOURS_FACTOR = 60 * 60 * 1000;

    private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
    private UserService userService = ServiceFactory.getUserService();
    private SchedulingSettingsService schedulingSettingsService = ServiceFactory.getSchedulingSettingsService();
    private ScheduleTimePointService timePointService = ServiceFactory.getScheduleTimePointService();

    @RequestMapping(value = "getStudentCount", method = RequestMethod.GET)
    public SchedulingSettingDto getCountStudents() {

        //TODO refactor
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

    @RequestMapping(value = "saveSelectedDays", method = RequestMethod.POST)
    public ResponseEntity<Long> saveSelectedDays(@RequestBody SchedulingDaysDto schedulingDaysDto) {
        long id = schedulingSettingsService.insertTimeRange(new SchedulingSettings(
                new Timestamp(schedulingDaysDto.getDay() + schedulingDaysDto.getHourStart() * HOURS_FACTOR),
                new Timestamp(schedulingDaysDto.getDay() + schedulingDaysDto.getHourEnd() * HOURS_FACTOR)
        ));
        return ResponseEntity.ok(id);
    }

    @RequestMapping(value = "getSelectedDays", method = RequestMethod.POST)
    public List<SchedulingSettings> getSelectedDays() {
        List<SchedulingSettings> list = schedulingSettingsService.getAll();
        return list;
    }

    @RequestMapping(value = "deleteSelectedDay", method = RequestMethod.GET)
    public ResponseEntity deleteSelectedDay(@RequestParam Long id) {
        int amount = schedulingSettingsService.deleteTimeRange(id);
        if (amount != 0) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @RequestMapping(value = "editSelectedDay", method = RequestMethod.POST)
    public ResponseEntity editSelectedDay(@RequestBody SchedulingDaysDto schedulingDaysDto) {
        int amount = schedulingSettingsService.updateTimeRange(new SchedulingSettings(
                schedulingDaysDto.getId(),
                new Timestamp(schedulingDaysDto.getDay() + schedulingDaysDto.getHourStart() * HOURS_FACTOR),
                new Timestamp(schedulingDaysDto.getDay() + schedulingDaysDto.getHourEnd() * HOURS_FACTOR)
        ));
        if (amount != 0) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @RequestMapping(value = "deleteUserTimeFinal", method = RequestMethod.POST)
    public ResponseEntity deleteUserTimeFinal(@RequestParam long id1, @RequestParam long id2) {
        System.out.println(id1 + " " + id2);
        User user = userService.getUserByID(id1);
        ScheduleTimePoint stp = timePointService.getScheduleTimePointById(id2);
        System.out.println(user + "\n" + stp);
        timePointService.deleteUserTimeFinal(user, stp);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "getCurrentSchedule", method = RequestMethod.GET)
    public List<ScheduleOverallDto> getCurrentSchedule() {
        List<ScheduleOverallDto> scheduleOverall = new ArrayList<>();
        List<ScheduleTimePoint> allTimePoints = timePointService.getAll();
        for (ScheduleTimePoint timePoint : allTimePoints) {
            ScheduleOverallDto oneTimePoint = new ScheduleOverallDto(timePoint.getTimePoint());
            oneTimePoint.setId(timePoint.getId());
            Map<Long, Long> numberForEachRole = timePointService.getUsersNumberInFinalTimePoint(timePoint.getTimePoint());
            setUsersNumberToEachRole(numberForEachRole, oneTimePoint);
            scheduleOverall.add(oneTimePoint);
        }
        return scheduleOverall;
    }

    @RequestMapping(value = "getUsersByTimePoint", method = RequestMethod.GET)
    public List<UserDto> getUsersByTimePoint(@RequestParam Long idRole, @RequestParam Long idTimePoint) {
        return userService.getUserByTimeAndRole(idRole, idTimePoint).stream().map(UserDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "changeSchedulingStatus", method = RequestMethod.GET)
    public ResponseEntity changeSchedulingStatus(@RequestParam Long id) {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        recruitment.setSchedulingStatus(SchedulingStatusEnum.getStatus(id));
        if (recruitmentService.updateRecruitment(recruitment) != 0) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "getInterviewParameters", method = RequestMethod.GET)
    public ResponseEntity getInterviewParameters(){
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        return ResponseEntity.ok(new RecruitmentSettingsDto(recruitment.getTimeInterviewSoft(), recruitment.getTimeInterviewTech()));
    }

    @RequestMapping(value = "saveInterviewParameters", method = RequestMethod.GET)
    public ResponseEntity saveInterviewParameters(@RequestParam int softDuration, @RequestParam int techDuration){
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        recruitment.setTimeInterviewSoft(softDuration);
        recruitment.setTimeInterviewTech(techDuration);
        if (recruitmentService.updateRecruitment(recruitment) != 0){
            return ResponseEntity.ok(null);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private void setUsersNumberToEachRole(Map<Long, Long> numberForEachRole, ScheduleOverallDto timePoint) {
        if (numberForEachRole == null) {
            timePoint.setAmountOfStudents(0);
            timePoint.setAmountOfSoft(0);
            timePoint.setAmountOfTech(0);
        } else {
            if (numberForEachRole.get((long) RoleEnum.ROLE_STUDENT.getId()) == null) {
                timePoint.setAmountOfStudents(0);
            } else {
                timePoint.setAmountOfStudents(numberForEachRole.get((long) RoleEnum.ROLE_STUDENT.getId()));
            }
            if (numberForEachRole.get((long) RoleEnum.ROLE_SOFT.getId()) == null) {
                timePoint.setAmountOfSoft(0);
            } else {
                timePoint.setAmountOfSoft(numberForEachRole.get((long) RoleEnum.ROLE_SOFT.getId()));
            }
            if (numberForEachRole.get((long) RoleEnum.ROLE_TECH.getId()) == null) {
                timePoint.setAmountOfTech(0);
            } else {
                timePoint.setAmountOfTech(numberForEachRole.get((long) RoleEnum.ROLE_TECH.getId()));
            }
        }
    }

    @RequestMapping(value = "getUsersWithoutInterview", method = RequestMethod.GET)
    public List<User> getUsersWithoutInterview(@RequestParam Long roleId) {
        List<User> usersWithoutInterview = userService.getUsersWithoutInterview(roleId);
        return usersWithoutInterview;
    }

    @RequestMapping(value = "addUserToTimepoint", method = RequestMethod.POST)
    public void addUserToTimepoint(@RequestParam Long userId,@RequestParam Long idTimePoint) {
        User user = userService.getUserByID(userId);
        ScheduleTimePoint timePoint = timePointService.getScheduleTimePointById(idTimePoint);
        Long res = timePointService.addUserToTimepoint(user, timePoint);
        System.out.println("ressssul: "+ res);
    }
}
