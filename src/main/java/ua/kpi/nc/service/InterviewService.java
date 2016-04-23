package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface InterviewService {

	Interview getInterviewById(Long id);

	List<Interview> getInterviewsByMark(int mark);

	List<Interview> getInterviewsByDate(Timestamp date);

	List<Interview> getInterviewsByInterviewer(User user);

	List<Interview> getInterviewsByInterviewerRole(Role role);

	List<Interview> getInterviewsByAdequateMark(boolean adequateMark);

	List<Interview> getInterviewsByApplicationForm(ApplicationForm applicationFormId);

	int insertInterview(Interview interview);

	int updateInterview(Interview interview);

	int deleteInterview(Interview interview);

	Set<Interview> getAllInterviews();
}
