package ua.kpi.nc.service;

import java.util.List;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface InterviewService {

	Interview getById(Long id);

	List<Interview> getByInterviewer(User user);

	List<Interview> getByApplicationForm(ApplicationForm applicationForm);

	Interview getByApplicationFormAndInterviewerRoleId(ApplicationForm applicationForm, Long interviewerRoleId );

	Long insertInterview(Interview interview, ApplicationForm applicationForm, User interviewer, Role role);

	boolean insertInterviewWithAnswers(Interview interview, List<FormAnswer> formAnswers);

	boolean updateInterview(Interview interview);

	int deleteInterview(Interview interview);

	List<Interview> getAll();

	boolean haveNonAdequateMark(Long applicationFormID, Long interviewerId);

	boolean haveNonAdequateMarkForAdmin (Long applicationFormID);
	
	boolean isFormAssigned(ApplicationForm applicationForm, User interviewer);
	
	public void assignStudent(ApplicationForm applicationForm, User interviewer, Role role);
}
