package ua.kpi.nc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.DataSourceFactory;
import ua.kpi.nc.persistence.dao.FormQuestionDao;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.FormQuestionType;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.service.FormQuestionService;

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

//TODO check update & add
public class FormQuestionServiceImpl implements FormQuestionService {

    private static Logger log = LoggerFactory.getLogger(FormQuestionServiceImpl.class.getName());

    private FormQuestionDao formQuestionDao;

    public FormQuestionServiceImpl(FormQuestionDao formQuestionDao) {
        this.formQuestionDao = formQuestionDao;
    }

    @Override
    public Long insertFormQuestion(FormQuestion formQuestion) {
        try (Connection connection = DataSourceFactory.getInstance().getConnection()) {
            Long generatedFormQuestionID = formQuestionDao.insertFormQuestion(formQuestion, connection);
            if (!connection.getAutoCommit())
                connection.commit();
            if (log.isTraceEnabled())
                log.trace("Inserted " + formQuestion);
            return generatedFormQuestionID;
        } catch (SQLException e) {
            if (log.isTraceEnabled())
                log.trace("An exception appeared while trying to insert " + formQuestion
                        + "\n" + e);
            return 0L;
        }
    }

    @Override
    public int deleteFormQuestion(FormQuestion formQuestion) {
        return formQuestionDao.deleteFormQuestion(formQuestion);
    }

    @Override
    public int updateFormQuestion(FormQuestion formQuestion) {
        try (Connection connection = DataSourceFactory.getInstance().getConnection()) {
            int response = 0;
    //TODO ????        response = formQuestionDao.updateFormQuestion(formQuestion, connection);
            if (!connection.getAutoCommit())
                connection.commit();
            if (log.isTraceEnabled())
                log.trace("Updated " + formQuestion);
            return response;
        } catch (SQLException e) {
            if (log.isTraceEnabled())
                log.trace("An exception appeared while trying to update " + formQuestion
                        + "\n" + e);
            return 0;
        }
    }

    @Override
    public boolean addAnswer(FormQuestion formQuestion, FormAnswer formAnswer) {
        try (Connection connection = DataSourceFactory.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            formAnswer.setFormQuestion(formQuestion);
            boolean response = formQuestionDao.addAnswer(formQuestion, formAnswer, connection);

            if (log.isTraceEnabled())
                log.trace("Added " + formAnswer + " to " + formQuestion);
            connection.commit();
            return response;
        } catch (SQLException e) {
            if (log.isTraceEnabled())
                log.trace("An exception appeared while trying to add " + formAnswer
                        + "to" + formAnswer + "\n" + e);
            return false;
        }
    }

    @Override
    public boolean removeAnswer(FormQuestion formQuestion, FormAnswer formAnswer) {
        try (Connection connection = DataSourceFactory.getInstance().getConnection()) {
            boolean response = formQuestionDao.removeAnser(formQuestion, formAnswer, connection);
            if (!connection.getAutoCommit())
                connection.commit();
            if (log.isTraceEnabled())
                log.trace("Removed " + formAnswer + " from " + formQuestion);
            return response;
        } catch (SQLException e) {
            if (log.isTraceEnabled())
                log.trace("An exception appeared while trying to remove " + formAnswer
                        + "from" + formAnswer + "\n" + e);
            return false;
        }
    }

    @Override
    public boolean addRole(FormAnswer formAnswer, Role role) {
        try (Connection connection = DataSourceFactory.getInstance().getConnection()) {
            boolean response = formQuestionDao.addRole(formAnswer, role, connection);
            if (!connection.getAutoCommit())
                connection.commit();
            if (log.isTraceEnabled())
                log.trace("Added " + role + " to " + formAnswer);
            return  response;
        } catch (SQLException e) {
            if (log.isTraceEnabled())
                log.trace("An exception appeared while trying to add " + role
                        + " to the " + formAnswer + "\n" + e);
            return false;
        }
    }

    @Override
    public boolean deleteRole(FormAnswer formAnswer, Role role) {
        try (Connection connection = DataSourceFactory.getInstance().getConnection()) {
            boolean response = formQuestionDao.deleteRole(formAnswer, role, connection);
            if (!connection.getAutoCommit())
                connection.commit();
            if (log.isTraceEnabled())
                log.trace("Deleted " + role + " from " + formAnswer);
            return response;
        } catch (SQLException e) {
            if (log.isTraceEnabled())
                log.trace("An exception appeared while trying to remove " +
                        "a role from the Answer form" + e);
            return false;
        }
    }

    @Override
    public boolean setFormQuestionType(FormQuestion formQuestion, FormQuestionType formQuestionType) {
        return formQuestionDao.setFormQuestionType(formQuestion, formQuestionType);
    }

    @Override
    public boolean setStatusQuestionType(FormQuestion formQuestion, boolean status) {
        return formQuestionDao.setStatusQuestionType(formQuestion, status);
    }

    @Override
    public boolean setMandatory(FormQuestion formQuestion, boolean mandatory) {
        return formQuestionDao.setMandatory(formQuestion, mandatory);
    }

    @Override
    public FormQuestion getById(Long id) {
        return formQuestionDao.getById(id);
    }

    @Override
    public Set<FormQuestion> getEnableFormAnswer() {
        return formQuestionDao.getEnableFormAnswer();
    }

    @Override
    public Set<FormQuestion> getDisableFormAnswer() {
        return formQuestionDao.getDisableFormAnswer();
    }

    @Override
    public Set<FormQuestion> getAll() {
        return formQuestionDao.getAll();
    }
}
