package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.FormAnswerVariant;

import java.sql.Connection;
import java.util.Set;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface FormAnswerVariantDao {
    FormAnswerVariant getById(Long id);

    FormAnswerVariant getByQuestionId(Long id);

    int insertFormAnswerVariant(FormAnswerVariant formatVariant, Connection connection);

    int updateFormAnswerVariant(Long id, FormAnswerVariant formAnswerVariant);

    int deleteFormAnswerVariant(FormAnswerVariant formVariant);

    int deleteFormAnswerVariant(Long id);

    Set<FormAnswerVariantDao> getAll();
}
