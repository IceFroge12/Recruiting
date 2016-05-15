package ua.kpi.nc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.dao.DataSourceSingleton;
import ua.kpi.nc.persistence.dao.FormAnswerDao;
import ua.kpi.nc.persistence.dao.InterviewDao;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.service.InterviewService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Desparete Housewives
 */
public class InterviewServiceImpl implements InterviewService {

    private static Logger log = LoggerFactory.getLogger(InterviewServiceImpl.class);

    InterviewDao interviewDao;

    public InterviewServiceImpl(InterviewDao interviewDao) {
        this.interviewDao = interviewDao;
    }

    @Override
    public List<Interview> getAll() {
        return interviewDao.getAll();
    }

    @Override
    public Interview getById(Long id) {
        return interviewDao.getById(id);
    }

    @Override
    public List<Interview> getByInterviewer(User user) {
        return interviewDao.getByInterviewer(user);
    }

    @Override
    public List<Interview> getByApplicationForm(ApplicationForm applicationForm) {
        return interviewDao.getByApplicationForm(applicationForm);
    }

    @Override
    public Long insertInterview(Interview interview, ApplicationForm applicationForm, User interviewer, Role role) {
        return interviewDao.insertInterview(interview, applicationForm, interviewer, role);
    }

    @Override
    public boolean insertInterviewWithAnswers(Interview interview, List<FormAnswer> formAnswers) {
        try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            Long generatedId = interviewDao.insertInterview(interview, connection);
            interview.setId(generatedId);
            FormAnswerDao formAnswerDao = DaoFactory.getFormAnswerDao();
            for (FormAnswer formAnswer : formAnswers) {
                formAnswerDao.insertFormAnswerForInterview(formAnswer, formAnswer.getFormQuestion(),
                        formAnswer.getFormAnswerVariant(), interview, connection);
            }
            connection.commit();
        } catch (SQLException e) {
            if (log.isWarnEnabled()) {
                log.warn("Cannot insert Interview with answers");
            }
            return false;
        }
        return true;
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
    public boolean haveNonAdequateMark(Long applicationFormID, Long interviewerId) {
        return interviewDao.haveNonAdequateMark(applicationFormID,interviewerId);
    }

    @Override
    public boolean haveNonAdequateMarkForAdmin(Long applicationFormID) {
        return interviewDao.haveNonAdequateMarkForAdmin(applicationFormID);
    }
}
