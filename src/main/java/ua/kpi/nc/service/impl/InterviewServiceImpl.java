package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.InterviewDao;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.InterviewService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Created by Алексей on 23.04.2016.
 */
public class InterviewServiceImpl implements InterviewService{

    InterviewDao interviewDao;

    public InterviewServiceImpl(InterviewDao interviewDao) {
        this.interviewDao = interviewDao;
    }

    @Override
    public Interview getInterviewById(Long id) {
        return interviewDao.getById(id);
    }

    @Override
    public List<Interview> getInterviewsByMark(int mark) {
        return interviewDao.getByMark(mark);
    }

    @Override
    public List<Interview> getInterviewsByDate(Timestamp date) {
        return interviewDao.getByDate(date);
    }

    @Override
    public List<Interview> getInterviewsByInterviewer(User user) {
        return interviewDao.getByInterviewer(user);
    }

    @Override
    public List<Interview> getInterviewsByInterviewerRole(Role role) {
        return interviewDao.getByInterviewerRole(role);
    }

    @Override
    public List<Interview> getInterviewsByAdequateMark(boolean adequateMark) {
        return interviewDao.getByAdequateMark(adequateMark);
    }

    @Override
    public List<Interview> getInterviewsByApplicationForm(ApplicationForm applicationForm) {
        return interviewDao.getByApplicationForm(applicationForm);
    }

    @Override
    public int insertInterview(Interview interview) {
        return interviewDao.insertInterview(interview);
    }

    @Override
    public int updateInterview(Interview interview) {
        return interviewDao.updateInterview(interview);
    }

    @Override
    public int deleteInterview(Interview interview) {
        return interviewDao.deleteInterview(interview);
    }

    @Override
    public Set<Interview> getAllInterviews() {
        return interviewDao.getAll();
    }
}
