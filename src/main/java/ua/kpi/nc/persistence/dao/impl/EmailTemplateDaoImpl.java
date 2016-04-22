package ua.kpi.nc.persistence.dao.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.EmailTemplateDao;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.NotificationType;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Korzh
 */
public class EmailTemplateDaoImpl extends JdbcDaoSupport implements EmailTemplateDao {
    private static Logger log = LoggerFactory.getLogger(EmailTemplateDaoImpl.class.getName());
    private static final String GET_BY_ID = "SELECT e.id, e.title, e.text, n.n_title " +
            ", n.id n_id FROM public.email_template e, public.notification_type  n " +
            "WHERE n.id = e.id and e.id=?;";
    private static final String GET_BY_TITLE = "SELECT e.id, e.title, e.text, n.n_title " +
            ", n.id n_id FROM public.email_template e, public.notification_type n  " +
            "WHERE n.id = e.id and e.title=?;";
    private static final String GET_BY_NOTIFICATION_TYPE = "SELECT e.id, e.title, e.text, n.n_title " +
            ", n.id n_id FROM public.email_template e, public.notification_type  n " +
            "WHERE n.id = e.id and n.id=?;";
    private static final String UPDATE_EMAIL_TEMPLATE = "UPDATE public.email_template SET title = ? , text = ?" +
            "WHERE email_template.id = ?;";
    private static final String DELETE_EMAIL_TEMPLATE = "DELETE FROM public.email_template WHERE id = ?;";

    public EmailTemplateDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public EmailTemplate getById(Long id) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for form email template with id  = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters(GET_BY_ID, new EmailTemplateExtractor(), id);
    }

    @Override
    public EmailTemplate getByTitle(String title) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for form email template with title  = " + title);
        }
        return this.getJdbcTemplate().queryWithParameters(GET_BY_TITLE, new EmailTemplateExtractor(), title);
    }

    @Override
    public EmailTemplate getByNotificationType(NotificationType notificationType) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for form email template with notificationType  = " + notificationType.getTitle());
        }
        return this.getJdbcTemplate().queryWithParameters(GET_BY_NOTIFICATION_TYPE,
                new EmailTemplateExtractor(), notificationType.getId());
    }

    @Override
    public int updateEmailTemplate(EmailTemplate emailTemplate) {
        if (log.isTraceEnabled()) {
            log.trace("Updating email template with id  = " + emailTemplate.getId());
        }
        return this.getJdbcTemplate().update(UPDATE_EMAIL_TEMPLATE, emailTemplate.getTitle(),
                emailTemplate.getText(), emailTemplate.getId());
    }

    @Override
    public int deleteEmailTemplate(EmailTemplate emailTemplate) {
        if (log.isTraceEnabled()) {
            log.trace("Deleting email template with id  = " + emailTemplate.getId());
        }
        return this.getJdbcTemplate().update(DELETE_EMAIL_TEMPLATE, emailTemplate.getId());
    }

    private static final class EmailTemplateExtractor implements ResultSetExtractor<EmailTemplate> {
        @Override
        public EmailTemplate extractData(ResultSet resultSet) throws SQLException {
            EmailTemplate emailTemplate = new EmailTemplate();
            emailTemplate.setId(resultSet.getLong("id"));
            emailTemplate.setTitle(resultSet.getString("title"));
            emailTemplate.setText(resultSet.getString("text"));
            emailTemplate.setNotificationType(new NotificationType(resultSet.getLong("n_id"), resultSet.getString("n_title")));

            return emailTemplate;
        }
    }
}
