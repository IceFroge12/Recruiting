package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.Role;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

/**
 * Created by IO on 21.04.2016.
 */
public interface FormQuestionDao {

    Long insertFormQuestion(FormQuestion formQuestion, Connection connection);

    int deleteFormQuestion(FormQuestion formQuestion);

    boolean addRole(FormQuestion formQuestion, Role role);

    boolean addRole(FormQuestion formQuestion, Role role, Connection connection);

    int deleteRole(FormQuestion formQuestion, Role role);

    FormQuestion getById(Long id);

    List<FormQuestion> getByRole(Role role);

    List<FormQuestion> getByRoleNonText(Role role);

    List<FormQuestion> getAll();

	int updateFormQuestion(FormQuestion formQuestion);

	Set<FormQuestion> getEnableByRoleAsSet(Role role);

	List<FormQuestion> getEnableByRole(Role role);

	Set<FormQuestion> getByApplicationFormAsSet(ApplicationForm applicationForm);


}
