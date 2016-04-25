package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.QuestionType;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface QuestionTypeService {
    QuestionType getQuestionTypeById(Long id);

    Long persistQuestionType(QuestionType questionType);

    int deleteQuestionType(QuestionType questionType);
}
