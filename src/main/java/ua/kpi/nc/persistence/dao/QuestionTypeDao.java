package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.QuestionType;

/**
 * Created by IO on 21.04.2016.
 */
public interface QuestionTypeDao {

    QuestionType getById(Long id);

    Long persistQuestionType(QuestionType questionType);

    int deleteQuestionType(QuestionType questionType);

}
