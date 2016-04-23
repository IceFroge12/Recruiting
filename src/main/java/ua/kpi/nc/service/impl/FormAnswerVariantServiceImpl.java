package ua.kpi.nc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.DataSourceFactory;
import ua.kpi.nc.persistence.dao.FormAnswerVariantDao;
import ua.kpi.nc.persistence.dao.FormQuestionDao;
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
    private FormQuestionDao formQuestionDao;


    public FormAnswerVariantServiceImpl(FormAnswerVariantDao formAnswerVariantDao,
                                        FormQuestionDao formQuestionDao) {
        this.formAnswerVariantDao = formAnswerVariantDao;
        this.formQuestionDao = formQuestionDao;
    }

    @Override
    public Set<FormAnswerVariant> getAnswerVariantsByQuestion(FormQuestion question) {
        return formAnswerVariantDao.getByQuestionId(question.getId());
    }

    @Override
    public Long addAnswerVariant(FormAnswerVariant formatVariant, FormQuestion formQuestion) {
        try (Connection connection = DataSourceFactory.getInstance().getConnection()) {
            Long generatedFormAnswerId = formAnswerVariantDao
                    .insertFormAnswerVariant(formatVariant, formQuestion, connection);
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
    public boolean changeAnswerVariant(FormAnswerVariant formAnswerVariant) {
        return formAnswerVariantDao.updateFormAnswerVariant(formAnswerVariant) != 0;
    }

    @Override
    public boolean deleteAnswerVariant(FormAnswerVariant formVariant) {
        return formAnswerVariantDao.deleteFormAnswerVariant(formVariant) != 0;
    }
    
}
