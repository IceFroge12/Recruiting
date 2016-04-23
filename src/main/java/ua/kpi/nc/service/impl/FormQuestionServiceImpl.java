package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.FormQuestionDao;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.service.FormQuestionService;

/**
 * Created by Chalienko on 23.04.2016.
 */
public class FormQuestionServiceImpl implements FormQuestionService {
    private FormQuestionDao formQuestionDao;

    public FormQuestionServiceImpl(FormQuestionDao formQuestionDao) {
        this.formQuestionDao = formQuestionDao;
    }

    @Override
    public FormQuestion getFormQuestionById(Long id) {
        return null;
    }
}
