package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface InterviewDao {
    Interview getById(Long id);

    List<Interview> getByMark(int mark);

    List<Interview> getByDate(Timestamp date);

    List<Interview> getByInterviewer(User user);

    List<Interview> getByInterviewerRole(Role role);

    List<Interview> getByAdequateMark(boolean adequateMark);

    List<Interview> getByApplicationFormId(ApplicationForm applicationFormId);

    void insertInterview(Interview interview);

    void updateInterview(Long id, Interview interview);

    void deliteInterview(Long id);

    void deliteInterview(Interview interview);
}
