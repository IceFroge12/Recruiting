package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.QuestionType;
import ua.kpi.nc.persistence.model.Role;

import java.util.Set;

/**
 * QuestionForm service
 *
 * @author Yaroslav Kruk on 4/23/16.
 *         e-mail: yakruck@gmail.com
 *         GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.7
 */
public interface FormQuestionService {

    /**
     * Add new question to the form
     * @param formQuestion
     * @return
     */
    Long addQuestion(FormQuestion formQuestion, Role role);

    boolean deleteQuestion(FormQuestion formQuestion);

    boolean changeQuestion(FormQuestion formQuestion);

    Long addAnswer(FormQuestion formQuestion, FormAnswer formAnswer);

    boolean removeAnswer(FormQuestion formQuestion, FormAnswer formAnswer);

    boolean addRole(FormAnswer formAnswer, Role role);

    boolean deleteRole(FormAnswer formAnswer, Role role);

    boolean setFormQuestionType(FormQuestion formQuestion, QuestionType formQuestionType);

    boolean setStatusQuestionType(FormQuestion formQuestion, boolean status);

    boolean setMandatory(FormQuestion formQuestion, boolean mandatory);

    FormQuestion getById(Long id);

    Set<FormQuestion> getEnableFormAnswer();

    Set<FormQuestion> getDisableFormAnswer();

    Set<FormQuestion> getAll();
}
