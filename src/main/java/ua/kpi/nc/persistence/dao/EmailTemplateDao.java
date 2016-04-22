package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.NotificationType;

import java.util.Set;

/**
 * @author Korzh
 */
public interface EmailTemplateDao {

    EmailTemplate getById(Long id);

    EmailTemplate getByTitle(String title);

    EmailTemplate getByNotificationType(NotificationType notificationType);

    int updateEmailTemplate(EmailTemplate emailTemplate);

    int deleteEmailTemplate(EmailTemplate emailTemplate);


}
