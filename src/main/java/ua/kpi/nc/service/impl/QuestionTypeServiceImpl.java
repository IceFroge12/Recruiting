package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.QuestionTypeDao;
import ua.kpi.nc.persistence.model.QuestionType;
import ua.kpi.nc.service.QuestionTypeService;

/**
 * Created by Алексей on 23.04.2016.
 */
public class QuestionTypeServiceImpl implements QuestionTypeService {

    QuestionTypeDao questionTypeDao;

    public QuestionTypeServiceImpl(QuestionTypeDao questionTypeDao) {
        this.questionTypeDao = questionTypeDao;
    }

    @Override
    public QuestionType getQuestionTypeById(Long id) {
        return questionTypeDao.getById(id);
    }

    @Override
    public Long persistQuestionType(QuestionType questionType) {
        return questionTypeDao.persistQuestionType(questionType);
    }

    @Override
    public int deleteQuestionType(QuestionType questionType) {
        return questionTypeDao.deleteQuestionType(questionType);
    }
}
