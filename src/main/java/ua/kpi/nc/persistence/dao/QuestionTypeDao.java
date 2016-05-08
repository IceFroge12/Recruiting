package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.QuestionType;

import java.util.List;

/**
 * Created by IO on 21.04.2016.
 */
public interface QuestionTypeDao {

    List<QuestionType> getAllQuestionType();

    QuestionType getById(Long id);

    QuestionType getByName(String name);

    Long persistQuestionType(QuestionType questionType);

    int deleteQuestionType(QuestionType questionType);

}
