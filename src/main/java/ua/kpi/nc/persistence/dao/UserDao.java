package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface UserDao {

    User getByUsername(String username);

    User getByID(Long id);

    boolean isExist(String username);

    int deleteUser(User user);

    Long insertUser(User user, Connection connection);

    int updateUser(User user);

    boolean addRole(User user, Role role);

    boolean addRole(User user, Role role, Connection connection);

    int deleteRole(User user, Role role);

    Long insertFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint);

    int deleteFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint);

    User getUserByToken(String token);

    Set<User> getAssignedStudents(Long id);

    Set<User> getAllStudents();

    List<User> getStudentsFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase);

    List<User> getEmployeesFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase);

    Set<User> getAllEmploees();

    Set<User> getAll();

    Long getEmployeeCount();

    Long getStudentCount();
}
