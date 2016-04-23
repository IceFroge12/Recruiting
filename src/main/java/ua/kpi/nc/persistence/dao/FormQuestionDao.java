package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.QuestionType;
import ua.kpi.nc.persistence.model.Role;

import java.sql.Connection;
import java.util.Set;

/**
 * Created by IO on 21.04.2016.
 */
public interface FormQuestionDao {

    Long insertFormQuestion(FormQuestion formQuestion, Connection connection);

    int deleteFormQuestion(FormQuestion formQuestion);

    //TODO need update?

    boolean addAnswer(FormQuestion formQuestion, FormAnswer formAnswer, Connection connection);

    boolean removeAnser(FormQuestion formQuestion, FormAnswer formAnswer, Connection connection);

    boolean addRole(FormAnswer formAnswer, Role role, Connection connection);

    boolean deleteRole(FormAnswer formAnswer, Role role, Connection connection);

    boolean setFormQuestionType(FormQuestion formQuestion, QuestionType questionType);

    boolean setStatusQuestionType(FormQuestion formQuestion, boolean status);

    boolean setMandatory(FormQuestion formQuestion, boolean mandatory);

    FormQuestion getById(Long id);

    Set<FormQuestion> getEnableFormAnswer();

    Set<FormQuestion> getDisableFormAnswer();

    Set<FormQuestion> getAll();



}
