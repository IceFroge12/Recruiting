package ua.kpi.nc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.ApplicationFormDao;
import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.dao.DataSourceSingleton;
import ua.kpi.nc.persistence.dao.FormAnswerDao;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

public class ApplicationFormServiceImpl implements ApplicationFormService {

	private static Logger log = LoggerFactory.getLogger(ApplicationFormServiceImpl.class);

	private ApplicationFormDao applicationFormDao;

	public ApplicationFormServiceImpl(ApplicationFormDao applicationFormDao) {
		this.applicationFormDao = applicationFormDao;
	}

	@Override
	public ApplicationForm getApplicationFormById(Long id) {
		return applicationFormDao.getById(id);
	}

	@Override
	public List<ApplicationForm> getByUserId(Long id) {
		return applicationFormDao.getByUserId(id);
	}

	@Override
	public List<ApplicationForm> getByStatus(String status) {
		return applicationFormDao.getByStatus(status);
	}

	@Override
	public List<ApplicationForm> getByState(boolean state) {
		return applicationFormDao.getByState(state);
	}

	@Override
	public int deleteApplicationForm(ApplicationForm applicationForm) {
		return applicationFormDao.deleteApplicationForm(applicationForm);
	}

	@Override
	public boolean insertApplicationForm(ApplicationForm applicationForm) {
		try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
			connection.setAutoCommit(false);
			Long generatedId = applicationFormDao.insertApplicationForm(applicationForm, connection);
			applicationForm.setId(generatedId);
			FormAnswerDao formAnswerDao = DaoFactory.getFormAnswerDao();
			for (FormAnswer formAnswer : applicationForm.getAnswers()) {
				formAnswerDao.insertFormAnswerForApplicationForm(formAnswer, formAnswer.getFormQuestion(),
						 applicationForm, connection);
			}
			connection.commit();
		} catch (SQLException e) {
			if (log.isWarnEnabled()) {
				log.error("Cannot insert Application form",e);
			}
			return false;
		}
		return true;
	}

	@Override
	public List<ApplicationForm> getAll() {
		return applicationFormDao.getAll();
	}

	@Override
	public int updateApplicationForm(ApplicationForm applicationForm) {
		return applicationFormDao.updateApplicationForm(applicationForm);
	}

	@Override
	public ApplicationForm getCurrentApplicationFormByUserId(Long id) {
		return applicationFormDao.getCurrentApplicationFormByUserId(id);
	}

	@Override
	public ApplicationForm getLastApplicationFormByUserId(Long id) {
		return applicationFormDao.getLastApplicationFormByUserId(id);
	}

}
