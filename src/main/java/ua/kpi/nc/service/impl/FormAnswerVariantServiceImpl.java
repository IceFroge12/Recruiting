package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.FormAnswerVariantDao;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.service.FormAnswerVariantService;

/**
 * Created by Chalienko on 23.04.2016.
 */
public class FormAnswerVariantServiceImpl implements FormAnswerVariantService {
    private FormAnswerVariantDao formAnswerVariantDao;

    public FormAnswerVariantServiceImpl(FormAnswerVariantDao formAnswerVariantDao) {
        this.formAnswerVariantDao = formAnswerVariantDao;
    }

    @Override
    public FormAnswerVariant getFormAnswerVariantById(Long id) {
        return null;
    }
}
