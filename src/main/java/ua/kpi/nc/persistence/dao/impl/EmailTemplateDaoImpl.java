package ua.kpi.nc.persistence.dao.impl;

import org.apache.log4j.Logger;
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
    private static Logger log = Logger.getLogger(EmailTemplateDaoImpl.class.getName());
    public EmailTemplateDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public EmailTemplate getById(Long id) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for form email template with id  = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters("SELECT email_template.id, email_template.title, email_template.text, notification_type.title " +
                        ",notification_type.id n_id FROM public.email_template , public.notification_type   " +
                        "WHERE notification_type.id = email_template.id and email_template.id=?;",
                new EmailTemplateExtractor(), id);
    }

    @Override
    public EmailTemplate getByTitle(String title) {
        return null;
    }

    @Override
    public int updateEmailTemplate(EmailTemplate emailTemplate) {
        return 0;
    }

    @Override
    public int deleteEmailTemplate(EmailTemplate emailTemplate) {
        return 0;
    }

    private static final class EmailTemplateExtractor implements ResultSetExtractor<EmailTemplate> {
        @Override
        public EmailTemplate extractData(ResultSet resultSet) throws SQLException {
            EmailTemplate emailTemplate = new EmailTemplate();
            emailTemplate.setId(resultSet.getLong("id"));
            emailTemplate.setTitle(resultSet.getString("title"));
            emailTemplate.setText(resultSet.getString("text"));
            emailTemplate.setNotificationType(new NotificationType(resultSet.getLong("n_id"),resultSet.getString("n_title")) );

            return emailTemplate;
        }
    }
}
