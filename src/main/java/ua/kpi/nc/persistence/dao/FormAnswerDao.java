package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.*;

import java.util.Set;

/**
 * @author Korzh
 */
public interface FormAnswerDao {

    FormAnswer getById(Long id);

    Set<FormAnswer> getByInterviewAndQuestion(Interview interview, FormQuestion question);

    Long insertFormAnswer(FormAnswer formAnswer, Interview interview, FormQuestion question,
                          FormAnswerVariant answerVariant, ApplicationForm applicationForm);
    
    Long insertFormAnswerForApplicationForm(FormAnswer formAnswer,FormQuestion question,
            FormAnswerVariant answerVariant, ApplicationForm applicationForm);
    
    Long insertFormAnswerForInterview(FormAnswer formAnswer,FormQuestion question,
            FormAnswerVariant answerVariant, Interview interview);

    int updateFormAnswer(FormAnswer formAnswer);

    int deleteFormAnswer(FormAnswer formAnswer);

}
