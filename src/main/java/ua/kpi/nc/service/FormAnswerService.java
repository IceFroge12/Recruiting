package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.*;

import java.util.Set;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface FormAnswerService {
    FormAnswer getFormAnswerByID(Long id);

    Set<FormAnswer> getByInterviewAndQuestion(Interview interview, FormQuestion question);

    int updateFormAnswer(FormAnswer formAnswer);

    int deleteFormAnswer(FormAnswer formAnswer);
}
