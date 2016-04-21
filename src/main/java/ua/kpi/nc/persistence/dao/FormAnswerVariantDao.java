package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.FormAnswerVariant;

import java.sql.Connection;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface FormAnswerVariantDao {
    FormAnswerVariant getById(Long id);

    FormAnswerVariant getByQuestionId(Long id);

    void insertFormAnswerVariant(FormAnswerVariant formatVariant, Connection connection);

    void updateFormAnswerVariant(Long id, FormAnswerVariant formAnswerVariant);

    void deleteFormAnswerVariant(FormAnswerVariant formVariant);

    void deleteFormAnswerVariant(Long id);
}
