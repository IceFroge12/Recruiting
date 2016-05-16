package ua.kpi.nc.service;

import java.util.List;

import ua.kpi.nc.persistence.model.*;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface ApplicationFormService {

    ApplicationForm getApplicationFormById(Long id);

    ApplicationForm getCurrentApplicationFormByUserId(Long id);

    ApplicationForm getLastApplicationFormByUserId(Long id);

    List<ApplicationForm> getByUserId(Long id);

    List<ApplicationForm> getByStatus(String status);

    List<ApplicationForm> getByState(boolean state);

    Long getCountRejectedAppForm();

    Long getCountToWorkAppForm();

    Long getCountGeneralAppForm();

    Long getCountAdvancedAppForm();

    int deleteApplicationForm(ApplicationForm applicationForm);

    boolean insertApplicationForm(ApplicationForm applicationForm);

    int updateApplicationForm(ApplicationForm applicationForm);

    List<ApplicationForm> getAll();

    List<ApplicationForm> getByInterviewer(User interviewer);

    boolean isAssignedForThisRole(ApplicationForm applicationForm, Role role);

    int changeCurrentsAppFormStatus(Long fromIdStatus, Long toIdStatus);

    Long getCountRecruitmentStudents(Long id);

    List<ApplicationForm> getCurrentsApplicationForms(Long fromRow, Long rowsNum, Long sortingCol, boolean increase);

    List<ApplicationForm> getApplicationFormsSorted(Long fromRow, Long rowsNum, Long sortingCol, boolean increase);

    List<ApplicationForm> getSearchAppFormByNameFromToRows(String lastName, Long fromRows, Long rowsNum);

    List<ApplicationForm> getCurrentsApplicationFormsFiltered(Long fromRow, Long rowsNum, Long sortingCol, boolean increase, List<FormQuestion> questions, List<String> statuses);

	Long getCountInReviewAppForm();

    List<ApplicationForm>getCurrentApplicationForms();

	List<ApplicationForm> getByStatusAndRecruitment(Status status, Recruitment recruitment);

    List<ApplicationForm> getByRecruitment(Recruitment recruitment);
    
    List<ApplicationForm> getRejectedAfterInterview(Recruitment recruitment);
}
