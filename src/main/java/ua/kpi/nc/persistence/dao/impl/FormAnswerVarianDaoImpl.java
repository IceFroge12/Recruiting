package ua.kpi.nc.persistence.dao.impl;

import ua.kpi.nc.persistence.dao.FormAnswerVariantDao;
import ua.kpi.nc.persistence.model.FormAnswerVariant;

import java.sql.Connection;
import java.util.Set;

/**
 * Created by Алексей on 22.04.2016.
 */
public class FormAnswerVarianDaoImpl extends JdbcDaoSupport implements FormAnswerVariantDao {
    @Override
    public FormAnswerVariant getById(Long id) {
        return null;
    }

    @Override
    public FormAnswerVariant getByQuestionId(Long id) {
        return null;
    }

    @Override
    public int insertFormAnswerVariant(FormAnswerVariant formatVariant, Connection connection) {
        return 0;
    }

    @Override
    public int updateFormAnswerVariant(Long id, FormAnswerVariant formAnswerVariant) {
        return 0;
    }

    @Override
    public int deleteFormAnswerVariant(FormAnswerVariant formVariant) {
        return 0;
    }

    @Override
    public int deleteFormAnswerVariant(Long id) {
        return 0;
    }

    @Override
    public Set<FormAnswerVariantDao> getAll() {
        return null;
    }
}
