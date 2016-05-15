package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.Status;
import ua.kpi.nc.persistence.model.User;

import java.sql.Connection;
import java.util.List;

public interface ApplicationFormDao {

    ApplicationForm getById(Long id);

    List<ApplicationForm> getByUserId(Long id);

    List<ApplicationForm> getByStatus(String status);

    List<ApplicationForm> getByState(boolean state);

    int deleteApplicationForm(ApplicationForm applicationForm);

    Long insertApplicationForm(ApplicationForm applicationForm, Connection connection);

    int updateApplicationForm(ApplicationForm applicationForm);

    List<ApplicationForm> getAll();

    Long getCountRejectedAppForm();

    Long getCountToWorkAppForm();

    Long getCountGeneralAppForm();

    Long getCountAdvancedAppForm();

	ApplicationForm getCurrentApplicationFormByUserId(Long id);

	ApplicationForm getLastApplicationFormByUserId(Long id);

	List<ApplicationForm> getByInterviewer(User interviewer);

	boolean isAssignedForThisRole(ApplicationForm applicationForm, Role role);

    int changeCurrentsAppFormStatus(Long fromIdStatus,Long toIdStatus);

    Long getCountRecruitmentStudents(Long id);

    List<ApplicationForm> getCurrentApplicationForms(Long fromRow, Long rowsNum, Long sortingCol, boolean increase);

    List<ApplicationForm> getCurrentApplicationForms();

    List<ApplicationForm> getCurrentApplicationFormsFiltered(Long fromRow, Long rowsNum, Long sortingCol, boolean increase, List<FormQuestion> questions);

    List<ApplicationForm> getApplicationFormsSorted(Long fromRow, Long rowsNum, Long sortingCol, boolean increase);

    Long getCountInReviewAppForm();

	List<ApplicationForm> getByStatusAndRecruitment(Status status, Recruitment recruitment);
}
