package ua.kpi.nc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.ApplicationFormDao;
import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.dao.DataSourceSingleton;
import ua.kpi.nc.persistence.dao.FormAnswerDao;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.service.ApplicationFormService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ApplicationFormServiceImpl implements ApplicationFormService {

    private static Logger log = LoggerFactory.getLogger(ApplicationFormServiceImpl.class);

    private ApplicationFormDao applicationFormDao;

    public ApplicationFormServiceImpl(ApplicationFormDao applicationFormDao) {
        super();
        this.applicationFormDao = applicationFormDao;
    }

    @Override
    public ApplicationForm getApplicationFormById(Long id) {
        return applicationFormDao.getById(id);
    }

    @Override
    public List<ApplicationForm> getByUserId(Long id) {
        return applicationFormDao.getByUserId(id);
    }

    @Override
    public List<ApplicationForm> getByStatus(String status) {
        return applicationFormDao.getByStatus(status);
    }

    @Override
    public List<ApplicationForm> getByState(boolean state) {
        return applicationFormDao.getByState(state);
    }

    @Override
    public int deleteApplicationForm(ApplicationForm applicationForm) {
        return applicationFormDao.deleteApplicationForm(applicationForm);
    }

    @Override
    public boolean insertApplicationForm(ApplicationForm applicationForm) {
        try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            Long generatedId = applicationFormDao.insertApplicationForm(applicationForm, connection);
            applicationForm.setId(generatedId);
            FormAnswerDao formAnswerDao = DaoFactory.getFormAnswerDao();
            for (FormAnswer formAnswer : applicationForm.getAnswers()) {
                formAnswerDao.insertFormAnswerForApplicationForm(formAnswer, formAnswer.getFormQuestion(),
                        formAnswer.getFormAnswerVariant(), applicationForm, connection);
            }
            connection.commit();
        } catch (SQLException e) {
            if (log.isWarnEnabled()) {
                log.warn("Cannot insert Application form");
            }
            return false;
        }
        return true;
    }

    @Override
    public List<ApplicationForm> getAll() {
        return applicationFormDao.getAll();
    }

    @Override
    public int updateApplicationForm(ApplicationForm applicationForm) {
        return applicationFormDao.updateApplicationForm(applicationForm);
    }

}
