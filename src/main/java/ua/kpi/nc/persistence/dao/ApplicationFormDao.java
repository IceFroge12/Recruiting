package ua.kpi.nc.persistence.dao;

import java.sql.Connection;
import java.util.Set;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.User;

public interface ApplicationFormDao {

	ApplicationForm getById(Long id);

	Set<ApplicationForm> getByUserId(Long id);

	Set<ApplicationForm> getByStatus(String status);

	Set<ApplicationForm> getByState(boolean state);

	int deleteApplicationForm(ApplicationForm applicationForm);

	Long insertApplicationForm(ApplicationForm applicationForm, User user, Connection connection);

	int updateApplicationForm(ApplicationForm applicationForm);
}
