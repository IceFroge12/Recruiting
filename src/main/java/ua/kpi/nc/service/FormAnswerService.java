package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.*;

import java.util.List;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface FormAnswerService {
    FormAnswer getFormAnswerByID(Long id);

    List<FormAnswer> getByInterviewAndQuestion(Interview interview, FormQuestion question);

    int updateFormAnswer(FormAnswer formAnswer);

    int deleteFormAnswer(FormAnswer formAnswer);
}
