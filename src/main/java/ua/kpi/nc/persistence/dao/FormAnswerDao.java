package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.*;

import java.sql.Connection;
import java.util.List;

/**
 * @author Desparete Houseviwes
 */
public interface FormAnswerDao {

	FormAnswer getById(Long id);

	List<FormAnswer> getByInterviewAndQuestion(Interview interview, FormQuestion question);

	Long insertFormAnswer(FormAnswer formAnswer, Interview interview, FormQuestion question,
						  FormAnswerVariant answerVariant, ApplicationForm applicationForm, Connection connection);

	Long insertBlankFormAnswerForApplicationForm(FormAnswer formAnswer);

	Long insertFormAnswerForApplicationForm(FormAnswer formAnswer, FormQuestion question,
											 ApplicationForm applicationForm, Connection connection);

	Long insertFormAnswerForApplicationForm(FormAnswer formAnswer);

	Long insertFormAnswerForInterview(FormAnswer formAnswer, FormQuestion question, FormAnswerVariant answerVariant,
									  Interview interview, Connection connection);

	int updateFormAnswer(FormAnswer formAnswer);

	int deleteFormAnswer(FormAnswer formAnswer);

	List<FormAnswer> getByApplicationFormAndQuestion(ApplicationForm applicationForm, FormQuestion question);

	Long insertFormAnswerForInterview(FormAnswer formAnswer);

}
