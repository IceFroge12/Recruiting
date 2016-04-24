package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;

import java.util.Set;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface InterviewDao {
    Interview getById(Long id);

    Set<Interview> getByInterviewer(User user);

    Set<Interview> getByApplicationForm(ApplicationForm applicationForm);

    Long insertInterview(Interview interview, ApplicationForm applicationForm, User interviewer, Role role);

    int updateInterview(Interview interview);

    int deleteInterview(Interview interview);

    Set<Interview> getAll();
}
