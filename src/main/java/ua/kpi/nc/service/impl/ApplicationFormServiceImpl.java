package ua.kpi.nc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.ApplicationFormDao;
import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.dao.DataSourceFactory;
import ua.kpi.nc.persistence.dao.FormAnswerDao;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ApplicationFormService;

public class ApplicationFormServiceImpl implements ApplicationFormService {

	private static Logger log = LoggerFactory.getLogger(ApplicationFormServiceImpl.class);

	private ApplicationFormDao applicationFormDao;

	public ApplicationFormServiceImpl(ApplicationFormDao applicationFormDao) {
		super();
		this.applicationFormDao = applicationFormDao;
	}

	@Override
	public ApplicationForm getApplicationFormById(Long id) {
		return applicationFormDao.getById(id);
	}

	@Override
	public Set<ApplicationForm> getByUserId(Long id) {
		return applicationFormDao.getByUserId(id);
	}

	@Override
	public Set<ApplicationForm> getByStatus(String status) {
		return applicationFormDao.getByStatus(status);
	}

	@Override
	public Set<ApplicationForm> getByState(boolean state) {
		return applicationFormDao.getByState(state);
	}

	@Override
	public int deleteApplicationForm(ApplicationForm applicationForm) {
		return applicationFormDao.deleteApplicationForm(applicationForm);
	}

	@Override
	public boolean insertApplicationForm(ApplicationForm applicationForm, User user, Set<FormAnswer> formAnswers) {
		try (Connection connection = DataSourceFactory.getInstance().getConnection()) {
			connection.setAutoCommit(false);
			Long generatedId = applicationFormDao.insertApplicationForm(applicationForm, user, connection);
			applicationForm.setId(generatedId);
			FormAnswerDao formAnswerDao = DaoFactory.getFormAnswerDao();
			for (FormAnswer formAnswer : formAnswers) {
				formAnswerDao.insertFormAnswerForApplicationForm(formAnswer, formAnswer.getFormQuestion(),
						formAnswer.getFormAnswerVariant(), applicationForm, connection);
			}
			connection.commit();
		} catch (SQLException e) {
			if (log.isWarnEnabled()) {
				log.warn("Cannot insert Application form");
			}
			return false;
		}
		return true;
	}

	@Override
	public Set<ApplicationForm> getAll() {
		return applicationFormDao.getAll();
	}

	@Override
	public int updateApplicationForm(ApplicationForm applicationForm) {
		return applicationFormDao.updateApplicationForm(applicationForm);
	}

}
