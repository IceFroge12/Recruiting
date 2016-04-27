package ua.kpi.nc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.dao.DataSourceSingleton;
import ua.kpi.nc.persistence.dao.FormAnswerVariantDao;
import ua.kpi.nc.persistence.dao.FormQuestionDao;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.QuestionType;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.service.FormQuestionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * @author Korzh
 */
public class FormQuestionServiceImpl implements FormQuestionService {
    private static Logger log = LoggerFactory.getLogger(FormQuestionServiceImpl.class.getName());
    private FormQuestionDao formQuestionDao;

    public FormQuestionServiceImpl(FormQuestionDao formQuestionDao) {
        this.formQuestionDao = formQuestionDao;
    }

    @Override
    public FormQuestion getById(Long id) {
        return formQuestionDao.getById(id);
    }

    @Override
    public boolean insertFormQuestion(FormQuestion formQuestion, QuestionType questionType, Role role) {
        try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            Long generatedFormQuestionId = formQuestionDao.insertFormQuestion(formQuestion, questionType, connection);
            formQuestion.setId(generatedFormQuestionId);
            formQuestionDao.addRole(formQuestion, role, connection);
            connection.commit();
        } catch (SQLException e) {
            if (log.isWarnEnabled()) {
                log.warn("Transaction failed When Trying to add Form Question and Role");
            }
            return false;
        }
        return true;
    }


    @Override
    public boolean insertFormQuestion(FormQuestion formQuestion, QuestionType questionType, Role role, Set<FormAnswerVariant> formAnswerVariants) {
        try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            Long generatedFormQuestionId = formQuestionDao.insertFormQuestion(formQuestion, questionType, connection);
            formQuestion.setId(generatedFormQuestionId);
            formQuestionDao.addRole(formQuestion, role, connection);
            FormAnswerVariantDao formAnswerVariantDao = DaoFactory.getFormAnswerVariantDao();
            for (FormAnswerVariant formAnswerVariant : formAnswerVariants ) {
                formAnswerVariantDao.insertFormAnswerVariant(formAnswerVariant,formQuestion,connection);
            }
            connection.commit();
        } catch (SQLException e) {
            if (log.isWarnEnabled()) {
                log.warn("Transaction failed When Trying to add Form Question with Variants and Role");
            }
            return false;
        }
        return true;
    }

    @Override
    public int deleteFormQuestion(FormQuestion formQuestion) {
        return formQuestionDao.deleteFormQuestion(formQuestion);
    }

    @Override
    public boolean addRole(FormQuestion formQuestion, Role role) {
        return formQuestionDao.addRole(formQuestion, role);
    }

    @Override
    public int deleteRole(FormQuestion formQuestion, Role role) {
        return formQuestionDao.deleteRole(formQuestion, role);
    }


    @Override
    public Set<FormQuestion> getByRole(Role role) {
        return formQuestionDao.getByRole(role);
    }

    @Override
    public Set<FormQuestion> getAll() {
        return formQuestionDao.getAll();
    }
}
