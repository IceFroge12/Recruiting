package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.FormAnswerDao;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.service.FormAnswerService;

import java.util.List;

/**
 * @author Yaroslav Kruk on 4/23/16.
 *         e-mail: yakruck@gmail.com
 *         GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.7
 */
public class FormAnswerServiceImpl implements FormAnswerService {

    private FormAnswerDao formAnswerDao;

    public FormAnswerServiceImpl(FormAnswerDao formAnswerDao) {
        this.formAnswerDao = formAnswerDao;
    }

    @Override
    public FormAnswer getFormAnswerByID(Long id) {
        return formAnswerDao.getById(id);
    }

    @Override
    public int deleteFormAnswer(FormAnswer formAnswer) {
        return formAnswerDao.deleteFormAnswer(formAnswer);
    }

    @Override
    public List<FormAnswer> getByInterviewAndQuestion(Interview interview, FormQuestion question) {
        return formAnswerDao.getByInterviewAndQuestion(interview,question);
    }

    @Override
    public Long insertBlankFormAnswerForApplicationForm(FormAnswer formAnswer) {
        return formAnswerDao.insertBlankFormAnswerForApplicationForm(formAnswer);
    }

    @Override
    public int updateFormAnswer(FormAnswer formAnswer) {
        return formAnswerDao.updateFormAnswer(formAnswer);
    }

    @Override
    public Long insertFormAnswerForApplicationForm(FormAnswer formAnswer) {
        return formAnswerDao.insertFormAnswerForApplicationForm(formAnswer);
    }

	@Override
	public List<FormAnswer> getByApplicationFormAndQuestion(ApplicationForm applicationForm, FormQuestion question) {
		return formAnswerDao.getByApplicationFormAndQuestion(applicationForm, question);
	}
}
