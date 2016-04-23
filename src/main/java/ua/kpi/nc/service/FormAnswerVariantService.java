package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.FormAnswerVariant;

import java.sql.Connection;
import java.util.Set;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface FormAnswerVariantService {

    FormAnswerVariant getFormAnswerVariantById(Long id);

    FormAnswerVariant getByQuestionId(Long id);

    Long insertFormAnswerVariant(FormAnswerVariant formatVariant);

    int updateFormAnswerVariant(FormAnswerVariant formAnswerVariant);

    int deleteFormAnswerVariant(FormAnswerVariant formVariant);

    Set<FormAnswerVariant> getAll();
}
