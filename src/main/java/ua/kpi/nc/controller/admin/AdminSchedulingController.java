package ua.kpi.nc.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dao.DaoUtil;
import ua.kpi.nc.persistence.dto.*;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.enums.SchedulingStatusEnum;
import ua.kpi.nc.service.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static ua.kpi.nc.persistence.model.enums.RoleEnum.*;

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
    private DaoUtilService daoUtilService = ServiceFactory.getDaoUtilService();
    private UserTimePriorityService userTimePriorityService = ServiceFactory.getUserTimePriorityService();


    private final static String SAVE_SELECTED_DAYS_ERROR = "Choiced day has been early select, please refresh page";
    private final static String GET_SELECTED_DAYS_ERROR = "Error during get selected days, refresh page or try again later";
    private final static String DELETE_SELECTED_DAY_ERROR = "Error during delete selected day, refresh page or try again later";
    private final static String EDIT_SELECTED_DAY_ERROR = "Error during edit selected day, refresh page or try again later";
    private final static String GET_USERS_BY_ROLE_TIME_PRIORITY_ERROR = "Error during get users, refresh page or try again later";
    private final static String CHANGE_STATUS_ERROR = "Action has been not complete";
    private final static String RECRUITMENT_NOT_STARTED = "Any recruitment not started";
    private final static String STAFF_STUDENT_NOT_CONFIRM = "Student and staff hasn't been confirm";
    private final static String STAFF_NOT_CONFIRM = "Staff hasn't been confirm";
    private final static String STUDENT_NOT_CONFIRM = "Student hasn't been confirm";


    @RequestMapping(value = "getCurrentStatus", method = RequestMethod.GET)
    public ResponseEntity getCurrentStatus() {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        if (null != recruitment) {
            return ResponseEntity.ok(recruitment.getSchedulingStatus());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(RECRUITMENT_NOT_STARTED));
        }
    }

    @RequestMapping(value = "getStaffCount", method = RequestMethod.GET)
    public SchedulingSettingDto getStaffCount() {

        Long activeTech = userService.getCountActiveEmployees(ROLE_TECH.getId(),
                ROLE_SOFT.getId());

        Long activeSoft = userService.getCountActiveEmployees(ROLE_SOFT.getId(),
                ROLE_TECH.getId());

        return new SchedulingSettingDto(
                applicationFormService.getCountRecruitmentStudents(recruitmentService.getCurrentRecruitmnet().getId()),
                activeSoft,
                activeTech
        );
    }

    @RequestMapping(value = "saveSelectedDays", method = RequestMethod.POST)
    public ResponseEntity saveSelectedDays(@RequestBody SchedulingDaysDto schedulingDaysDto) {
        long id = schedulingSettingsService.insertTimeRange(new SchedulingSettings(
                new Timestamp(schedulingDaysDto.getDay() + schedulingDaysDto.getHourStart() * HOURS_FACTOR),
                new Timestamp(schedulingDaysDto.getDay() + schedulingDaysDto.getHourEnd() * HOURS_FACTOR)
        ));
        if (id == 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(SAVE_SELECTED_DAYS_ERROR));
        }
        return ResponseEntity.ok(id);
    }

    @RequestMapping(value = "getSelectedDays", method = RequestMethod.POST)
    public ResponseEntity getSelectedDays() {
        List<SchedulingSettings> schedulingSettingsList = schedulingSettingsService.getAll();
        if (null != schedulingSettingsList) {
            return ResponseEntity.ok(schedulingSettingsList);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(GET_SELECTED_DAYS_ERROR));
    }

    @RequestMapping(value = "deleteSelectedDay", method = RequestMethod.GET)
    public ResponseEntity deleteSelectedDay(@RequestParam Long id) {
        int amount = schedulingSettingsService.deleteTimeRange(id);
        if (amount != 0) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDto(DELETE_SELECTED_DAY_ERROR));
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDto(EDIT_SELECTED_DAY_ERROR));
        }
    }

    @RequestMapping(value = "deleteUserTimeFinal", method = RequestMethod.POST)
    public ResponseEntity deleteUserTimeFinal(@RequestParam long userId, @RequestParam long timePointId) {
        User user = userService.getUserByID(userId);
        ScheduleTimePoint stp = timePointService.getScheduleTimePointById(timePointId);
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
    public ResponseEntity getUsersByTimePoint(@RequestParam Long idRole, @RequestParam Long idTimePoint) {
        List<UserDto> userDtoList = userService.getUserByTimeAndRole(idRole, idTimePoint).stream().map(UserDto::new).collect(Collectors.toList());
        if (null != userDtoList) {
            return ResponseEntity.ok(userDtoList);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDto(GET_USERS_BY_ROLE_TIME_PRIORITY_ERROR));
        }
    }

    @RequestMapping(value = "changeSchedulingStatus", method = RequestMethod.GET)
    public ResponseEntity changeSchedulingStatus(@RequestParam Long idStatus) {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        SchedulingStatus schedulingStatus = SchedulingStatusEnum.getStatus(idStatus);
        if (Objects.equals(schedulingStatus.getId(), SchedulingStatusEnum.DATES.getId())) {
            saveTimePoint();
        }
        recruitment.setSchedulingStatus(schedulingStatus);
        if (recruitmentService.updateRecruitment(recruitment) != 0) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDto(CHANGE_STATUS_ERROR));
        }
    }

    @RequestMapping(value = "cancelSchedulingStatus", method = RequestMethod.GET)
    public void cancelDaySelect() {
        schedulingSettingsService.deleteAll();
        timePointService.deleteAll();
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        recruitment.setSchedulingStatus(SchedulingStatusEnum.getStatus(SchedulingStatusEnum.NOT_STARTED.getId()));
        recruitmentService.updateRecruitment(recruitment);
    }

    @RequestMapping(value = "getInterviewParameters", method = RequestMethod.GET)
    public ResponseEntity getInterviewParameters() {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        if (null != recruitment) {
            return ResponseEntity.ok(new RecruitmentSettingsDto(recruitment.getTimeInterviewSoft(), recruitment.getTimeInterviewTech()));

        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @RequestMapping(value = "saveInterviewParameters", method = RequestMethod.GET)
    public ResponseEntity saveInterviewParameters(@RequestParam int softDuration, @RequestParam int techDuration) {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        recruitment.setTimeInterviewSoft(softDuration);
        recruitment.setTimeInterviewTech(techDuration);
        if (recruitmentService.updateRecruitment(recruitment) != 0) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "startScheduling", method = RequestMethod.GET)
    public ResponseEntity startScheduling() {
        if (!(userTimePriorityService.isSchedulePrioritiesExistStudent() & userTimePriorityService.isSchedulePrioritiesExistStaff())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(STAFF_STUDENT_NOT_CONFIRM));
        } else if (!userTimePriorityService.isSchedulePrioritiesExistStaff()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(STAFF_NOT_CONFIRM));
        } else if (!userTimePriorityService.isSchedulePrioritiesExistStudent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(STUDENT_NOT_CONFIRM));
        } else {
            ScheduleService scheduleService = new ScheduleService();
            scheduleService.startScheduleForStudents();
            scheduleService.startScheduleForStaff();
            return ResponseEntity.ok(null);
        }
    }

    @RequestMapping(value = "getUsersWithoutInterview", method = RequestMethod.GET)
    public List<User> getUsersWithoutInterview(@RequestParam Long roleId) {
        return userService.getUsersWithoutInterview(roleId);
    }

    @RequestMapping(value = "addUserToTimepoint", method = RequestMethod.POST)
    public void addUserToTimePoint(@RequestParam Long userId, @RequestParam Long idTimePoint) {
        User user = userService.getUserByID(userId);
        ScheduleTimePoint timePoint = timePointService.getScheduleTimePointById(idTimePoint);
        timePointService.addUserToTimepoint(user, timePoint);
    }

    private void saveTimePoint() {
        List<SchedulingSettings> list = schedulingSettingsService.getAll();
        List<Timestamp> listForInsert = new LinkedList<>();
        for (SchedulingSettings schedulingSettings : list) {
            do {
                listForInsert.add(new Timestamp(schedulingSettings.getStartDate().getTime()));
                schedulingSettings.getStartDate().setTime(schedulingSettings.getStartDate().getTime() + HOURS_FACTOR);
            } while (!schedulingSettings.getStartDate().equals(schedulingSettings.getEndDate()));
        }
        timePointService.batchInsert(listForInsert);
    }

    private void setUsersNumberToEachRole(Map<Long, Long> numberForEachRole, ScheduleOverallDto timePoint) {
        if (numberForEachRole == null) {
            timePoint.setAmountOfStudents(0);
            timePoint.setAmountOfSoft(0);
            timePoint.setAmountOfTech(0);
        } else {
            if (numberForEachRole.get(ROLE_STUDENT.getId()) == null) {
                timePoint.setAmountOfStudents(0);
            } else {
                timePoint.setAmountOfStudents(numberForEachRole.get(ROLE_STUDENT.getId()));
            }
            if (numberForEachRole.get(ROLE_SOFT.getId()) == null) {
                timePoint.setAmountOfSoft(0);
            } else {
                timePoint.setAmountOfSoft(numberForEachRole.get(ROLE_SOFT.getId()));
            }
            if (numberForEachRole.get(ROLE_TECH.getId()) == null) {
                timePoint.setAmountOfTech(0);
            } else {
                timePoint.setAmountOfTech(numberForEachRole.get((ROLE_TECH.getId())));
            }
        }
    }
}
