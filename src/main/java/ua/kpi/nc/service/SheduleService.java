package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.util.scheduling.CreatingOfAllSchedules;
import ua.kpi.nc.util.scheduling.ScheduleCell;
import ua.kpi.nc.util.scheduling.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalya on 14.05.2016.
 */
public class SheduleService {
    private CreatingOfAllSchedules creatingOfAllSchedules;

    private static UserService userService = ServiceFactory.getUserService();
    private static RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
    private static ScheduleTimePointService scheduleTimePointService = ServiceFactory.getScheduleTimePointService();

    public SheduleService(int durationOfLongIntervInMinutes, int durationOfShortIntervInMinutes,
                          int totalNumbOfRegisteredTeachersWithLongerInterv,
                          int totalNumbOfRegisteredTeachersWithShorterInterv,
                          ArrayList<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                          ArrayList<Integer> numbOfBookedPositionByShortTeacherForEachDay,
                          ArrayList<Timestamp> datesAndTimesList, ArrayList<User> undistributedStudents,
                          ArrayList<User> allLongTeachers, ArrayList<User> allShortTeachers) {
    }

    public void startSchedule() {
        List<ScheduleCell> scheduleCellList = creatingOfAllSchedules.getStudentsSchedule();
        for (ScheduleCell scheduleCell : scheduleCellList) {
            for (User user : scheduleCell.getStudents()) {
                ScheduleTimePoint scheduleTimePoint = scheduleTimePointService
                        .getScheduleTimePointByTimepoint(scheduleCell.getDateAndHour());
               // userService.insertFinalTimePoint(user,scheduleTimePoint);

            }
        }
    }
}
