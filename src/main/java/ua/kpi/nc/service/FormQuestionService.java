package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.*;

import java.util.List;

/**
 * QuestionForm service
 *
 * @author Yaroslav Kruk on 4/23/16. e-mail: yakruck@gmail.com GitHub:
 *         https://github.com/uakruk
 * @version 1.0
 * @since 1.7
 */
public interface FormQuestionService {

	public boolean insertFormQuestion(FormQuestion formQuestion, Role role);

	int updateFormQuestion(FormQuestion formQuestion);

	boolean insertFormQuestion(FormQuestion formQuestion, Role role, List<FormAnswerVariant> formAnswerVariants);

	int deleteFormQuestion(FormQuestion formQuestion);

	boolean addRole(FormQuestion formQuestion, Role role);

	int deleteRole(FormQuestion formQuestion, Role role);

	FormQuestion getById(Long id);

	List<FormQuestion> getByRole(Role role);

	List<FormQuestion> getAll();
}
