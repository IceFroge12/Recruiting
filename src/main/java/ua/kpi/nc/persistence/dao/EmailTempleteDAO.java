package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.EmailTemplate;

/**
 * @author Korzh
 */
public interface EmailTempleteDAO {

    EmailTemplate getById(Long id);

    EmailTemplate getByTitle(String title);

    int updateEmailTemplate(EmailTemplate emailTemplate);

    int deleteEmailTemplate(EmailTemplate emailTemplate);
}
