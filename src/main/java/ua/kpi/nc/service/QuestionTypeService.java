package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.QuestionType;

import java.util.List;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface QuestionTypeService {

    List<QuestionType> getAllQuestionType();

    QuestionType getQuestionTypeById(Long id);

    QuestionType getQuestionTypeByName(String name);

    Long persistQuestionType(QuestionType questionType);

    int deleteQuestionType(QuestionType questionType);
}
