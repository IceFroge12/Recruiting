package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.FormAnswerVariant;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface FormAnswerVariantDao {
    FormAnswerVariant getById(Long id);
    void deleteFormAnswerVariant(FormAnswerVariant formVariant);
    void insertFormAnswerVariant(FormAnswerVariant formatVariant);
}
