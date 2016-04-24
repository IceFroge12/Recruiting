package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.QuestionType;
import ua.kpi.nc.persistence.model.Role;

import java.sql.Connection;
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

    Long insertFormQuestion(FormQuestion formQuestion, QuestionType questionType, Connection connection);

    int deleteFormQuestion(FormQuestion formQuestion);

    boolean addRole(FormQuestion formQuestion, Role role);

    boolean addRole(FormQuestion formQuestion, Role role, Connection connection);

    int deleteRole(FormQuestion formQuestion, Role role);

    FormQuestion getById(Long id);

    Set<FormQuestion> getByRole(Role role);

    Set<FormQuestion> getAll();
}
