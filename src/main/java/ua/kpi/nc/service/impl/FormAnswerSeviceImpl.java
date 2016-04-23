package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.FormAnswerDao;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.service.FormAnswerService;

/**
 * Created by Chalienko on 23.04.2016.
 */
public class FormAnswerSeviceImpl implements FormAnswerService {
    private FormAnswerDao formAnswerDao;

    public FormAnswerSeviceImpl(FormAnswerDao formAnswerDao) {
        this.formAnswerDao = formAnswerDao;
    }

    @Override
    public FormAnswer getFormAnswerByID(Long id) {
        return null;
    }
}
