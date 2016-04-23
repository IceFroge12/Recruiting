package ua.kpi.nc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.DataSourceFactory;
import ua.kpi.nc.persistence.dao.FormAnswerVariantDao;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.service.FormAnswerVariantService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * @author Yaroslav Kruk on 4/23/16.
 *         e-mail: yakruck@gmail.com
 *         GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.7
 */
public class FormAnswerVariantServiceImpl implements FormAnswerVariantService {

    private static Logger log = LoggerFactory.getLogger(FormAnswerVariantServiceImpl.class.getName());

    private FormAnswerVariantDao formAnswerVariantDao;


    public FormAnswerVariantServiceImpl(FormAnswerVariantDao formAnswerVariantDao) {
        this.formAnswerVariantDao = formAnswerVariantDao;
    }

    @Override
    public Set<FormAnswerVariant> getAnswerVariantsByQuestion(FormQuestion question) {
        return formAnswerVariantDao.getByQuestionId(question.getId());
    }

    @Override
    public Long insertFormAnswerVariant(FormAnswerVariant formatVariant) {
        try (Connection connection = DataSourceFactory.getInstance().getConnection()) {
            Long generatedFormAnswerId = formAnswerVariantDao.insertFormAnswerVariant(formatVariant, connection);
            formatVariant.setId(generatedFormAnswerId);
            if (log.isTraceEnabled())
                log.trace("Inserted new " + formatVariant);
            if (!connection.getAutoCommit())
                connection.commit();
            return generatedFormAnswerId;
        } catch (SQLException e) {
            if (log.isTraceEnabled())
                log.trace("An exception appeared while trying to get connection to insert FormAnswerVariant" + e);
            return 0L;
        }
    }

    @Override
    public int updateFormAnswerVariant(FormAnswerVariant formAnswerVariant) {
        return formAnswerVariantDao.updateFormAnswerVariant(formAnswerVariant);
    }

    @Override
    public int deleteFormAnswerVariant(FormAnswerVariant formVariant) {
        return formAnswerVariantDao.deleteFormAnswerVariant(formVariant);
    }

    @Override
    public Set<FormAnswerVariant> getAll() {
        return formAnswerVariantDao.getAll();
    }
}
