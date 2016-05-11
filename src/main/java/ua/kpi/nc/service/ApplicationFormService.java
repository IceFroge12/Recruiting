package ua.kpi.nc.service;

import java.util.List;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface ApplicationFormService {

	ApplicationForm getApplicationFormById(Long id);

	ApplicationForm getCurrentApplicationFormByUserId(Long id);

	ApplicationForm getLastApplicationFormByUserId(Long id);

	List<ApplicationForm> getByUserId(Long id);

	List<ApplicationForm> getByStatus(String status);

	List<ApplicationForm> getByState(boolean state);

	Long getCountRejectedAppForm();

	Long getCountToWorkAppForm();

	Long getCountGeneralAppForm();

	Long getCountAdvancedAppForm();

	int deleteApplicationForm(ApplicationForm applicationForm);

	boolean insertApplicationForm(ApplicationForm applicationForm);

	int updateApplicationForm(ApplicationForm applicationForm);

	List<ApplicationForm> getAll();
	
	List<ApplicationForm> getByInterviewer(User interviewer);

	boolean isAssignedForThisRole(ApplicationForm applicationForm, Role role);

	int changeCurrentsAppFormStatus(Long fromIdStatus, Long toIdStatus);
}
