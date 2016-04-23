package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;

import java.sql.Connection;
import java.util.Set;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface FormAnswerVariantDao {
    FormAnswerVariant getById(Long id);

    Set<FormAnswerVariant> getByQuestionId(Long id);

    Long insertFormAnswerVariant(FormAnswerVariant formatVariant, FormQuestion formQuestion,
                                 Connection connection);

    int updateFormAnswerVariant(FormAnswerVariant formAnswerVariant);

    int deleteFormAnswerVariant(FormAnswerVariant formVariant);

    Set<FormAnswerVariant> getAll();
}
