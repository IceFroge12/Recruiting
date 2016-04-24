package ua.kpi.nc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.DataSourceFactory;
import ua.kpi.nc.persistence.dao.FormQuestionDao;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.QuestionType;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.service.FormQuestionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * @author Despareted Houseviwes
 */

public class FormQuestionServiceImpl implements FormQuestionService {

    private FormQuestionDao formQuestionDao;

    public FormQuestionServiceImpl(FormQuestionDao formQuestionDao) {
        this.formQuestionDao = formQuestionDao;
    }

    @Override
    public Long insertFormQuestion(FormQuestion formQuestion, QuestionType questionType, Connection connection) {
        return null;
    }

    @Override
    public int deleteFormQuestion(FormQuestion formQuestion) {
        return 0;
    }

    @Override
    public boolean addRole(FormQuestion formQuestion, Role role) {
        return false;
    }

    @Override
    public boolean addRole(FormQuestion formQuestion, Role role, Connection connection) {
        return false;
    }

    @Override
    public int deleteRole(FormQuestion formQuestion, Role role) {
        return 0;
    }

    @Override
    public FormQuestion getById(Long id) {
        return null;
    }

    @Override
    public Set<FormQuestion> getByRole(Role role) {
        return null;
    }

    @Override
    public Set<FormQuestion> getAll() {
        return null;
    }
}
