package ua.kpi.nc.service.impl;

import java.util.Set;

import ua.kpi.nc.persistence.dao.ApplicationFormDao;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ApplicationFormService;

public class ApplicationFormServiceImpl implements ApplicationFormService {

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
	public Long insertApplicationForm(ApplicationForm applicationForm, User user) {
		return applicationFormDao.insertApplicationForm(applicationForm, user);
	}

	@Override
	public int updateApplicationForm(ApplicationForm applicationForm) {
		return applicationFormDao.updateApplicationForm(applicationForm);
	}

}