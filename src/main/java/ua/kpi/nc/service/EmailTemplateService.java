package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.NotificationType;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.EmailTemplateEnum;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface EmailTemplateService {

    EmailTemplate getById(Long id);

    EmailTemplate getByTitle(String title);

    EmailTemplate getByNotificationType(NotificationType notificationType);

    int updateEmailTemplate(EmailTemplate emailTemplate);

    int deleteEmailTemplate(EmailTemplate emailTemplate);

    String showTemplateParams(String inputText, User user);

}
