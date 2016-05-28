package ua.kpi.nc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.dao.DataSourceSingleton;
import ua.kpi.nc.persistence.dao.FormAnswerDao;
import ua.kpi.nc.persistence.dao.InterviewDao;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerImpl;
import ua.kpi.nc.persistence.model.impl.real.InterviewImpl;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.FormQuestionService;
import ua.kpi.nc.service.InterviewService;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.ServiceFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    public Interview getByApplicationFormAndInterviewerRoleId(ApplicationForm applicationForm, Long interviewerRoleId) {
        return interviewDao.getByApplicationFormAndInterviewerRoleId(applicationForm,interviewerRoleId);
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
            log.error("Cannot insert Interview with answers {}",e);
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

	@Override
	public boolean isFormAssigned(ApplicationForm applicationForm, User interviewer) {
		return interviewDao.isFormAssigned(applicationForm, interviewer);
	}

	public void assignStudent(ApplicationForm applicationForm, User interviewer, Role role) {
		RoleService roleService = ServiceFactory.getRoleService();
		ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
		if (roleService.isInterviewerRole(role)
				&& !applicationFormService.isAssignedForThisRole(applicationForm, role)) {
			Interview interview = new InterviewImpl();
			interview.setInterviewer(interviewer);
			interview.setApplicationForm(applicationForm);
			interview.setDate(new Timestamp(System.currentTimeMillis()));
			interview.setRole(role);
			FormQuestionService questionService = ServiceFactory.getFormQuestionService();
			List<FormQuestion> questions = questionService.getEnableByRole(role);
			List<FormAnswer> answers = new ArrayList<>();
			for (FormQuestion formQuestion : questions) {
				FormAnswer formAnswer = new FormAnswerImpl();
				formAnswer.setFormQuestion(formQuestion);
				formAnswer.setInterview(interview);
				answers.add(formAnswer);
			}
			insertInterviewWithAnswers(interview, answers);
		}
	}
	
}
