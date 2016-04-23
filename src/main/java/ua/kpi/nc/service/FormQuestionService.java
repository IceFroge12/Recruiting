package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.FormQuestionType;
import ua.kpi.nc.persistence.model.Role;

import java.sql.Connection;
import java.util.Set;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface FormQuestionService {
    Long insertFormQuestion(FormQuestion formQuestion, Connection connection);

    int deleteFormQuestion(FormQuestion formQuestion);

    int updateFormQuestion(FormQuestion formQuestion);

    boolean addAnswer(FormQuestion formQuestion, FormAnswer formAnswer, Connection connection);

    boolean removeAnser(FormQuestion formQuestion, FormAnswer formAnswer, Connection connection);

    boolean addRole(FormAnswer formAnswer, Role role, Connection connection);

    boolean deleteRole(FormAnswer formAnswer, Role role, Connection connection);

    boolean setFormQuestionType(FormQuestion formQuestion, FormQuestionType formQuestionType);

    boolean setStatusQuestionType(FormQuestion formQuestion, boolean status);

    boolean setMandatory(FormQuestion formQuestion, boolean mandatory);

    FormQuestion getById(Long id);

    Set<FormQuestion> getEnableFormAnswer();

    Set<FormQuestion> getDisableFormAnswer();

    Set<FormQuestion> getAll();
}
