package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.EmailTemplate;

import java.util.Set;

/**
 * @author Korzh
 */
public interface EmailTemplateDAOU {

    EmailTemplate getById(Long id);

    EmailTemplate getByTitle(String title);

    int updateEmailTemplate(EmailTemplate emailTemplate);

    int deleteEmailTemplate(EmailTemplate emailTemplate);


}
