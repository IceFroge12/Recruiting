package ua.kpi.nc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.FormAnswerVariantDao;
import ua.kpi.nc.persistence.dao.FormQuestionDao;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.service.FormAnswerVariantService;

/**
 * @author Yaroslav Kruk on 4/23/16. e-mail: yakruck@gmail.com GitHub:
 *         https://github.com/uakruk
 * @version 1.0
 * @since 1.7
 */
public class FormAnswerVariantServiceImpl implements FormAnswerVariantService {

	private static Logger log = LoggerFactory.getLogger(FormAnswerVariantServiceImpl.class.getName());

	private FormAnswerVariantDao formAnswerVariantDao;

	public FormAnswerVariantServiceImpl(FormAnswerVariantDao formAnswerVariantDao, FormQuestionDao formQuestionDao) {
		this.formAnswerVariantDao = formAnswerVariantDao;
	}

	@Override
	public List<FormAnswerVariant> getAnswerVariantsByQuestion(FormQuestion question) {
		return formAnswerVariantDao.getByQuestionId(question.getId());
	}

	@Override
	public Long addAnswerVariant(FormAnswerVariant formatVariant) {
		return formAnswerVariantDao.insertFormAnswerVariant(formatVariant);
	}

	@Override
	public boolean changeAnswerVariant(FormAnswerVariant formAnswerVariant) {
		return formAnswerVariantDao.updateFormAnswerVariant(formAnswerVariant) != 0;
	}

	@Override
	public boolean deleteAnswerVariant(FormAnswerVariant formVariant) {
		return formAnswerVariantDao.deleteFormAnswerVariant(formVariant) != 0;
	}

	/**
	 * Get Answer variant by id	 *
	 * @param id the id by which answer variant will be taken
	 * @return Answer variant
	 */
	@Override
	public FormAnswerVariant getAnswerVariantById(Long id) {
		return formAnswerVariantDao.getById(id);
	}
}
