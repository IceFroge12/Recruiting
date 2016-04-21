package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.FormQuestionType;

/**
 * Created by IO on 21.04.2016.
 */
public interface FormQuestionTypeDao {

    Long getById(Long id);

    Long persistFormQuestionType(FormQuestionType formQuestionType);

    int deleteFormQuestionType(FormQuestionType formQuestionType);

}
