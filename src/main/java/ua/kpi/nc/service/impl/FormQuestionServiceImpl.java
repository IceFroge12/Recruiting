package ua.kpi.nc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.dao.DataSourceSingleton;
import ua.kpi.nc.persistence.dao.FormAnswerVariantDao;
import ua.kpi.nc.persistence.dao.FormQuestionDao;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.enums.FormQuestionTypeEnum;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.service.FormQuestionService;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.ServiceFactory;

/**
 * @author Korzh
 */
public class FormQuestionServiceImpl implements FormQuestionService {
	private static Logger log = LoggerFactory.getLogger(FormQuestionServiceImpl.class.getName());
	private FormQuestionDao formQuestionDao;
	private FormAnswerVariantDao formAnswerVariantDao;

	public FormQuestionServiceImpl(FormQuestionDao formQuestionDao) {
		this.formQuestionDao = formQuestionDao;
	}

	@Override
	public FormQuestion getById(Long id) {
		return formQuestionDao.getById(id);
	}

	@Override
	public boolean insertFormQuestion(FormQuestion formQuestion, Role role) {
		try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
			connection.setAutoCommit(false);
			Long generatedFormQuestionId = formQuestionDao.insertFormQuestion(formQuestion, connection);
			formQuestion.setId(generatedFormQuestionId);
			formQuestionDao.addRole(formQuestion, role, connection);
			connection.commit();
		} catch (SQLException e) {
			if (log.isWarnEnabled()) {
				log.warn("Transaction failed When Trying to add Form Question and Role");
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean insertFormQuestion(FormQuestion formQuestion, Role role,
			List<FormAnswerVariant> formAnswerVariants) {
		try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
			connection.setAutoCommit(false);
			Long generatedFormQuestionId = formQuestionDao.insertFormQuestion(formQuestion, connection);
			formQuestion.setId(generatedFormQuestionId);
			formQuestionDao.addRole(formQuestion, role, connection);
			FormAnswerVariantDao formAnswerVariantDao = DaoFactory.getFormAnswerVariantDao();
			for (FormAnswerVariant formAnswerVariant : formAnswerVariants) {
				formAnswerVariant.setFormQuestion(formQuestion);
				formAnswerVariantDao.insertFormAnswerVariant(formAnswerVariant, connection);
			}
			connection.commit();
		} catch (SQLException e) {
			if (log.isWarnEnabled()) {
				log.warn("Transaction failed When Trying to add Form Question with Variants and Role");
			}
			return false;
		}
		return true;
	}

	@Override
	public int deleteFormQuestion(FormQuestion formQuestion) {
		return formQuestionDao.deleteFormQuestion(formQuestion);
	}

	@Override
	public boolean addRole(FormQuestion formQuestion, Role role) {
		return formQuestionDao.addRole(formQuestion, role);

	}

	@Override
	public int deleteRole(FormQuestion formQuestion, Role role) {
		return formQuestionDao.deleteRole(formQuestion, role);
	}

	@Override
	public List<FormQuestion> getByRole(Role role) {
		return formQuestionDao.getByRole(role);
	}

	@Override
	public List<FormQuestion> getByRoleNonText(Role role) {
		return formQuestionDao.getByRoleNonText(role);
	}

	@Override
	public List<FormQuestion> getAll() {
		return formQuestionDao.getAll();
	}

	@Override
	public int updateFormQuestion(FormQuestion formQuestion) {
		return formQuestionDao.updateFormQuestion(formQuestion);
	}

	@Override
	public boolean updateQuestions(FormQuestion formQuestion, List<FormAnswerVariant> formAnswerVariants) {
		try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
			connection.setAutoCommit(false);
			formQuestionDao.updateFormQuestion(formQuestion, connection);
			formAnswerVariantDao = DaoFactory.getFormAnswerVariantDao();
			for (FormAnswerVariant formAnswerVariantFromDb : formAnswerVariantDao.getByQuestionId(formQuestion.getId())) {
				formAnswerVariantDao.deleteFormAnswerVariant(formAnswerVariantFromDb,connection);
			}
			if (formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.CHECKBOX.getTitle()) ||
					formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.SELECT.getTitle()) ||
					formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.RADIO.getTitle())) {
				for (FormAnswerVariant formAnswerVariant : formAnswerVariants) {
					formAnswerVariant.setFormQuestion(formQuestion);
					formAnswerVariantDao.insertFormAnswerVariant(formAnswerVariant, connection);
				}
			}
			connection.commit();
		} catch (SQLException e) {
			if (log.isWarnEnabled()) {
				log.warn("Transaction failed When Trying to update Form Question with Variants and Role");
			}
			return false;
		}
		return true;
	}

	@Override
	public Set<FormQuestion> getByEnableRoleAsSet(Role role) {
		return formQuestionDao.getEnableByRoleAsSet(role);
	}

	@Override
	public List<FormQuestion> getEnableByRole(Role role) {
		return formQuestionDao.getEnableByRole(role);
	}

	@Override
	public Set<FormQuestion> getByApplicationFormAsSet(ApplicationForm applicationForm) {
		return formQuestionDao.getByApplicationFormAsSet(applicationForm);
	}

	@Override
	public List<FormQuestion> getEnableUnconnectedQuestion(ApplicationForm applicationForm) {
		RoleService roleService = ServiceFactory.getRoleService();
		Role role = roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT));
		return formQuestionDao.getEnableUnconnectedQuestion(role, applicationForm);
	}

	@Override
	public List<FormQuestion> getWithVariantsByRole(Role role) {
		return formQuestionDao.getWithVariantsByRole(role);
	}
}
