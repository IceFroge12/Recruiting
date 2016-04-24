package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.InterviewDao;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.InterviewService;

import java.util.Set;

/**
 * @author Desparete Housewives
 */
public class InterviewServiceImpl implements InterviewService {

    InterviewDao interviewDao;

    public InterviewServiceImpl(InterviewDao interviewDao) {
        this.interviewDao = interviewDao;
    }

    @Override
    public Set<Interview> getAll() {
        return interviewDao.getAll();
    }

    @Override
    public Interview getById(Long id) {
        return interviewDao.getById(id);
    }

    @Override
    public Set<Interview> getByInterviewer(User user) {
        return interviewDao.getByInterviewer(user);
    }

    @Override
    public Set<Interview> getByApplicationForm(ApplicationForm applicationForm) {
        return interviewDao.getByApplicationForm(applicationForm);
    }

    @Override
    public Long insertInterview(Interview interview, ApplicationForm applicationForm, User interviewer, Role role) {
        return interviewDao.insertInterview(interview, applicationForm, interviewer, role);
    }

    @Override
    public int updateInterview(Interview interview) {
        return interviewDao.updateInterview(interview);
    }

    @Override
    public int deleteInterview(Interview interview) {
        return interviewDao.deleteInterview(interview);
    }
}
