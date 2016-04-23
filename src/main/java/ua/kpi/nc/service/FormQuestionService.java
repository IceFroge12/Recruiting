package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.FormQuestionType;
import ua.kpi.nc.persistence.model.Role;

import java.util.Set;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface FormQuestionService {
    Long insertFormQuestion(FormQuestion formQuestion);

    int deleteFormQuestion(FormQuestion formQuestion);

    int updateFormQuestion(FormQuestion formQuestion);

    boolean addAnswer(FormQuestion formQuestion, FormAnswer formAnswer);

    boolean removeAnswer(FormQuestion formQuestion, FormAnswer formAnswer);

    boolean addRole(FormAnswer formAnswer, Role role);

    boolean deleteRole(FormAnswer formAnswer, Role role);

    boolean setFormQuestionType(FormQuestion formQuestion, FormQuestionType formQuestionType);

    boolean setStatusQuestionType(FormQuestion formQuestion, boolean status);

    boolean setMandatory(FormQuestion formQuestion, boolean mandatory);

    FormQuestion getById(Long id);

    Set<FormQuestion> getEnableFormAnswer();

    Set<FormQuestion> getDisableFormAnswer();

    Set<FormQuestion> getAll();
}
