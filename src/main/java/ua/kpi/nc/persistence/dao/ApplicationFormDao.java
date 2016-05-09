package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;

import java.sql.Connection;
import java.util.List;

public interface ApplicationFormDao {

    ApplicationForm getById(Long id);

    List<ApplicationForm> getByUserId(Long id);

    List<ApplicationForm> getByStatus(String status);

    List<ApplicationForm> getByState(boolean state);

    int deleteApplicationForm(ApplicationForm applicationForm);

    Long insertApplicationForm(ApplicationForm applicationForm, Connection connection);

    int updateApplicationForm(ApplicationForm applicationForm);

    List<ApplicationForm> getAll();

	ApplicationForm getCurrentApplicationFormByUserId(Long id);

	ApplicationForm getLastApplicationFormByUserId(Long id);

	List<ApplicationForm> getByInterviewer(User interviewer);

	boolean isAssignedForThisRole(ApplicationForm applicationForm, Role role);
}
