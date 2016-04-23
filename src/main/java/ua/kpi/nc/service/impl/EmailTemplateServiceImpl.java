package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.EmailTemplateDao;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.NotificationType;
import ua.kpi.nc.service.EmailTemplateService;

public class EmailTemplateServiceImpl implements EmailTemplateService {

	private EmailTemplateDao emailTemplateDao;

	public EmailTemplateServiceImpl(EmailTemplateDao emailTemplateDao) {
		this.emailTemplateDao = emailTemplateDao;
	}

	@Override
	public EmailTemplate getById(Long id) {
		return emailTemplateDao.getById(id);
	}

	@Override
	public EmailTemplate getByTitle(String title) {
		return emailTemplateDao.getByTitle(title);
	}

	@Override
	public EmailTemplate getByNotificationType(NotificationType notificationType) {
		return emailTemplateDao.getByNotificationType(notificationType);
	}

	@Override
	public int updateEmailTemplate(EmailTemplate emailTemplate) {
		return emailTemplateDao.updateEmailTemplate(emailTemplate);
	}

	@Override
	public int deleteEmailTemplate(EmailTemplate emailTemplate) {
		return emailTemplateDao.deleteEmailTemplate(emailTemplate);
	}

}
