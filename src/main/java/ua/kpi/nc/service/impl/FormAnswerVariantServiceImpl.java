package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.FormAnswerVariantDao;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.service.FormAnswerVariantService;

/**
 * @author Yaroslav Kruk on 4/23/16.
 *         e-mail: yakruck@gmail.com
 *         GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.7
 */
public class FormAnswerVariantServiceImpl implements FormAnswerVariantService {

    private FormAnswerVariantDao formAnswerVariantDao;


    public FormAnswerVariantServiceImpl(FormAnswerVariantDao formAnswerVariantDao) {
        this.formAnswerVariantDao = formAnswerVariantDao;
    }

    @Override
    public FormAnswerVariant getFormAnswerVariantById(Long id) {
        return formAnswerVariantDao.getById(id);
    }
}
