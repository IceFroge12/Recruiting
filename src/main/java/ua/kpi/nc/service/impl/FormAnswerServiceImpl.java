package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.FormAnswerDao;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.service.FormAnswerService;

/**
 * @author Yaroslav Kruk on 4/23/16.
 *         e-mail: yakruck@gmail.com
 *         GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.7
 */

//TODO
public class FormAnswerServiceImpl implements FormAnswerService {

    private FormAnswerDao formAnswerDao;

    public FormAnswerServiceImpl(FormAnswerDao formAnswerDao) {
        this.formAnswerDao = formAnswerDao;
    }

    @Override
    public FormAnswer getFormAnswerByID(Long id) {
        return formAnswerDao.getById(id);
    }
}
