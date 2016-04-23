package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.QuestionType;
import ua.kpi.nc.persistence.model.Role;

import java.sql.Connection;
import java.util.Set;

/**
 * Created by IO on 21.04.2016.
 */
public interface FormQuestionDao {

    Long insertFormQuestion(FormQuestion formQuestion, QuestionType questionType, Connection connection);

    int deleteFormQuestion(FormQuestion formQuestion);

    boolean addRole(FormQuestion formQuestion, Role role);

    boolean addRole(FormQuestion formQuestion, Role role, Connection connection);

    int deleteRole(FormQuestion formQuestion, Role role);

    FormQuestion getById(Long id);

    Set<FormQuestion> getByRole(Role role);

    Set<FormQuestion> getAll();


}
