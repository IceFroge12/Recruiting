package ua.kpi.nc.service;

import org.springframework.scheduling.annotation.Async;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.UserTimePriority;
import ua.kpi.nc.persistence.model.impl.real.RoleImpl;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.util.scheduling.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalya on 14.05.2016.
 */
public class ScheduleService {
    private CreatingOfAllSchedules creatingOfAllSchedules;

    private static final String CAN_TIME_PRIORITY = "Can";
    private static UserService userService = ServiceFactory.getUserService();
    private static RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
    private static ScheduleTimePointService scheduleTimePointService = ServiceFactory.getScheduleTimePointService();
    private static UserTimePriorityService userTimePriorityService = ServiceFactory.getUserTimePriorityService();
    private static final Role TECH_ROLE = new RoleImpl(2L);
    private static final Role SOFT_ROLE = new RoleImpl(5L);


    private List<User> undistributedStudents = new ArrayList<>();
    private List<Timestamp> datesAndTimesList = new ArrayList<>();
    private int durationOfLongIntervInMinutes;
    private int durationOfShortIntervInMinutes;
    private int totalNumbOfRegisteredTeachersWithLongerInterv;
    private int totalNumbOfRegisteredTeachersWithShorterInterv;
    private List<Integer> numbOfBookedPositionByLongTeacherForEachDay = new ArrayList<>();
    private List<Integer> numbOfBookedPositionByShortTeacherForEachDay = new ArrayList<>();
    private List<User> allLongTeachers = new ArrayList<>();
    private List<User> allShortTeachers = new ArrayList<>();
    private Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
    private boolean techLonger;

    public ScheduleService() {
        initializeStartParametr();
        creatingOfAllSchedules = new CreatingOfAllSchedules(durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                totalNumbOfRegisteredTeachersWithLongerInterv, totalNumbOfRegisteredTeachersWithShorterInterv,
                numbOfBookedPositionByLongTeacherForEachDay,numbOfBookedPositionByShortTeacherForEachDay,
                datesAndTimesList,undistributedStudents,allLongTeachers,allShortTeachers);
    }

    private void initializeStartParametr() {
        initializeInterviewTimeParameter();
        initializeNumOfInterviewerParameter();
        initializeNumbOfBookedPositionByForEachDay();
        initializeDatesAndTimesList();
        initializeUndistributedStudents();
        initializeTeachersList();
    }

    private void initializeTeachersList() {
        System.out.println("initializeTeachersList");
        List<ua.kpi.nc.persistence.model.User> softUsers = userService.getActiveStaffByRole(SOFT_ROLE);
        List<ua.kpi.nc.persistence.model.User> techUsers = userService.getActiveStaffByRole(TECH_ROLE);
        if (techLonger) {
            for (ua.kpi.nc.persistence.model.User user : softUsers){
                allShortTeachers.add(adaptUser(user));
                System.out.println("softAdaptUser " + user.getEmail());
            }
            for (ua.kpi.nc.persistence.model.User user : techUsers){
                allLongTeachers.add(adaptUser(user));
                System.out.println("techAdaptUser " + user.getEmail());
            }
        }else {
            for (ua.kpi.nc.persistence.model.User user : softUsers){
                allLongTeachers.add(adaptUser(user));
                System.out.println("softAdaptUser " + user.getEmail());
            }
            for (ua.kpi.nc.persistence.model.User user : techUsers){
                allShortTeachers.add(adaptUser(user));
                System.out.println("techAdaptUser " + user.getEmail());
            }
        }
        System.out.println( "----------------------------------------------------");
    }

//    public int getNumberOfTeacherWithLongerInterview(){
//        return AllActivityAboutFindingTeachers.getTotalNumberOfNecessaryTeacherWithLongerInterview(
//        userService.getAllStudentCount(), durationOfLongIntervInMinutes, durationOfShortIntervInMinutes, , ,
//                totalNumbOfRegisteredTeachersWithLongerInterv, totalNumbOfRegisteredTeachersWithShorterInterv,
//                numbOfBookedPositionByLongTeacherForEachDay, numbOfBookedPositionByShortTeacherForEachDay);
//    }

    private void initializeUndistributedStudents(){
        System.out.println("initializeUndistributedStudents");
        List<ua.kpi.nc.persistence.model.User> users = userService.getAllNotScheduleStudents();
        for (ua.kpi.nc.persistence.model.User user : users){
            undistributedStudents.add(adaptUser(user));
            System.out.println("student " + user.getEmail());
        }
        System.out.println( "----------------------------------------------------");
    }

    private void initializeDatesAndTimesList(){
        System.out.println("initializeDatesAndTimesList");
        List<ScheduleTimePoint> scheduleTimePoints = scheduleTimePointService.getAll();
        for (ScheduleTimePoint scheduleTimePoint : scheduleTimePoints) {
            datesAndTimesList.add(scheduleTimePoint.getTimePoint());
            System.out.println("time poitns " + scheduleTimePoint.getTimePoint());
        }
        System.out.println( "----------------------------------------------------");
    }

    private void initializeNumbOfBookedPositionByForEachDay(){
        System.out.println("initializeNumbOfBookedPositionByForEachDay");
        if (techLonger) {
            numbOfBookedPositionByLongTeacherForEachDay = userService.getCountUsersOnInterviewDaysForRole(TECH_ROLE);
            numbOfBookedPositionByShortTeacherForEachDay = userService.getCountUsersOnInterviewDaysForRole(SOFT_ROLE);

        }else {
            numbOfBookedPositionByLongTeacherForEachDay = userService.getCountUsersOnInterviewDaysForRole(SOFT_ROLE);
            numbOfBookedPositionByShortTeacherForEachDay = userService.getCountUsersOnInterviewDaysForRole(TECH_ROLE);
        }
        System.out.println("numbOfBookedPositionByLongTeacherForEachDay  " + numbOfBookedPositionByLongTeacherForEachDay );
        System.out.println("numbOfBookedPositionByShortTeacherForEachDay  " + numbOfBookedPositionByShortTeacherForEachDay );
        System.out.println( "----------------------------------------------------");
    }

    private void initializeNumOfInterviewerParameter() {
        System.out.println("initializeNumOfInterviewerParameter");
        if (techLonger) {
            totalNumbOfRegisteredTeachersWithLongerInterv = recruitment.getNumberTechInterviewers();
            totalNumbOfRegisteredTeachersWithShorterInterv = recruitment.getNumberSoftInterviewers();
        } else {
            totalNumbOfRegisteredTeachersWithLongerInterv = recruitment.getNumberSoftInterviewers();
            totalNumbOfRegisteredTeachersWithShorterInterv = recruitment.getNumberTechInterviewers();
        }
        System.out.println("totalNumbOfRegisteredTeachersWithLongerInterv   " + totalNumbOfRegisteredTeachersWithLongerInterv);
        System.out.println(" totalNumbOfRegisteredTeachersWithShorterInterv   "  +  totalNumbOfRegisteredTeachersWithShorterInterv );
        System.out.println( "----------------------------------------------------");
    }

    private void initializeInterviewTimeParameter() {
        System.out.println("initializeInterviewTimeParameter");
        int softInterviewTime = recruitment.getTimeInterviewSoft();
        int techInterviewTime = recruitment.getTimeInterviewTech();
        if (softInterviewTime > techInterviewTime) {
            durationOfLongIntervInMinutes = softInterviewTime;
            durationOfShortIntervInMinutes = techInterviewTime;
            techLonger = false;
        } else {
            durationOfLongIntervInMinutes = techInterviewTime;
            durationOfShortIntervInMinutes = softInterviewTime;
            techLonger = true;
        }
        System.out.println("durationOfLongIntervInMinutes    " + durationOfLongIntervInMinutes);
        System.out.println("durationOfShortIntervInMinutes   "  + durationOfShortIntervInMinutes);
        System.out.println( "----------------------------------------------------");
    }

    public void startScheduleForStudents() {
        List<StudentsScheduleCell> scheduleCellList = creatingOfAllSchedules.getStudentsSchedule();
        for (StudentsScheduleCell scheduleCell : scheduleCellList) {
            for (User user : scheduleCell.getStudents()) {
                ScheduleTimePoint scheduleTimePoint = scheduleTimePointService
                        .getScheduleTimePointByTimepoint(scheduleCell.getDateAndHour());
                userService.insertFinalTimePoint(reverseAdaptUser(user), scheduleTimePoint);
            }
        }
    }

    public boolean startScheduleForStaff() {
        List<TeachersScheduleCell> scheduleCellList = creatingOfAllSchedules.getTeachersSchedule();
        for (TeachersScheduleCell scheduleCell : scheduleCellList) {
            System.out.println("sche" + scheduleCell.getTeachers().size());
            for (User user : scheduleCell.getTeachers()) {
                ScheduleTimePoint scheduleTimePoint = scheduleTimePointService
                        .getScheduleTimePointByTimepoint(scheduleCell.getDate());
                userService.insertFinalTimePoint(reverseAdaptUser(user), scheduleTimePoint);
                System.out.println(scheduleTimePoint + "   " + user.getId());
            }
        }

        return (scheduleCellList==null)?false:true;
    }

    private ua.kpi.nc.persistence.model.User reverseAdaptUser(User user) {
        return new UserImpl(user.getId());
    }

    private User adaptUser(ua.kpi.nc.persistence.model.User user) {
        List<Timestamp> timesAndDates = new ArrayList<>();
        List<UserTimePriority> userTimePriorities = userTimePriorityService.getAllUserTimePriorities(user.getId());
        for (UserTimePriority userTimePriority : userTimePriorities) {
            if (userTimePriority.getTimePriorityType().getPriority().equals(CAN_TIME_PRIORITY)) {
                timesAndDates.add(userTimePriority.getScheduleTimePoint().getTimePoint());
            }
        }
        return new User(user.getId(), timesAndDates);
    }
}
