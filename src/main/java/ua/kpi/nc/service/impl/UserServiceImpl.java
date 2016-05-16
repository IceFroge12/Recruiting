package ua.kpi.nc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ua.kpi.nc.persistence.dao.DataSourceSingleton;
import ua.kpi.nc.persistence.dao.UserDao;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */

public class UserServiceImpl implements UserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public User getUserByID(Long id) {
        return userDao.getByID(id);
    }

    @Override
    public boolean isExist(String username) {
        return userDao.isExist(username);
    }

    @Override
    public boolean insertUser(User user, List<Role> roles) {
        try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            Long generatedUserId = userDao.insertUser(user, connection);
            user.setId(generatedUserId);
            for (Role role : roles) {
                userDao.addRole(user, role, connection);
            }
            connection.commit();
        } catch (SQLException e) {
            if (log.isWarnEnabled()) {
                log.warn("Cannot insert user", e);
            }
            return false;
        }
        return true;
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public boolean updateUserWithRole(User user) {
        try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            userDao.updateUser(user);
            userDao.deleteAllRoles(user);
            for (Role role : user.getRoles())
                userDao.addRole(user, role, connection);
            connection.commit();
        } catch (SQLException e) {
            if (log.isWarnEnabled()) {
                log.warn("Cannot update user", e);
            }
            return false;
        }
        return true;
    }


    @Override
    public boolean addRole(User user, Role role) {
        return userDao.addRole(user, role);
    }

    @Override
    public int deleteRole(User user, Role role) {
        return userDao.deleteRole(user, role);
    }

    @Override
    public List<User> getAllNotScheduleStudents() {
        return userDao.getAllNotScheduleStudents();
    }

    public List<User> getActiveStaffByRole(Role role) {
        return userDao.getActiveStaffByRole(role);
    }

    @Override
    public int deleteUser(User user) {
        return userDao.deleteUser(user);
    }

    @Override
    public Long insertFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint) {
        return userDao.insertFinalTimePoint(user, scheduleTimePoint);
    }

    @Override
    public int deleteFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint) {
        return userDao.deleteFinalTimePoint(user, scheduleTimePoint);
    }

    @Override
    public User getUserByToken(String token) {
        return userDao.getUserByToken(token);
    }

    @Override
    public Set<User> getAssignedStudents(Long id) {
        return userDao.getAssignedStudents(id);
    }

    @Override
    public Set<User> getAllStudents() {
        return userDao.getAllStudents();
    }

    @Override
    public List<User> getStudentsFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase) {
        return userDao.getStudentsFromToRows(fromRows, rowsNum, sortingCol, increase);
    }

    @Override
    public List<User> getFilteredEmployees(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart, Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated) {
        return userDao.getFilteredEmployees(fromRows, rowsNum, sortingCol, increase, idStart, idFinish, roles, interviewer, notIntrviewer, notEvaluated);
    }

    @Override
    public List<User> getEmployeesFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase) {
        return userDao.getEmployeesFromToRows(fromRows, rowsNum, sortingCol, increase);
    }

    @Override
    public Set<User> getAllEmploees() {
        return userDao.getAllEmploees();
    }

    @Override
    public Set<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getAuthorizedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return getUserByUsername(name);
    }

    @Override
    public List<Integer> getCountUsersOnInterviewDaysForRole(Role role) {
        return userDao.getCountUsersOnInterviewDaysForRole(role);
    }

    @Override
    public Long getAllStudentCount() {
        return userDao.getStudentCount();
    }

    @Override
    public Long getAllEmployeeCount() {
        return userDao.getEmployeeCount();
    }

    @Override
    public Long getAllEmployeeCountFiltered(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart, Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated) {
        return userDao.getEmployeeCountFiltered(fromRows, rowsNum, sortingCol, increase, idStart, idFinish, roles, interviewer, notIntrviewer, notEvaluated);
    }

    @Override
    public int deleteToken(Long id) {
        return userDao.deleteToken(id);
    }

    @Override
    public List<User> getEmployeesByNameFromToRows(String name) {
        return userDao.getEmployeesByNameFromToRows(name);
    }

    @Override
    public List<User> getStudentsByNameFromToRows(String lastName, Long fromRows, Long rowsNum, Long sortingCol) {
        return userDao.getStudentsByNameFromToRows(lastName, fromRows, rowsNum, sortingCol);
    }

    @Override
    public Long getUserCount() {
        return userDao.getUserCount();
    }

    @Override
    public Long getActiveEmployees(Long idParam0, Long idParam1) {
        return userDao.getActiveEmployees(idParam0, idParam1);
    }

    @Override
    public int disableAllStaff() {
        return userDao.disableAllStaff();
    }

	@Override
	public List<User> getStudentsWithNotconnectedForms() {
		return userDao.getStudentsWithNotconnectedForms();
	}
}

