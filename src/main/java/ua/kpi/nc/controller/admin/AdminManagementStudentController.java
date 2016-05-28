package ua.kpi.nc.controller.admin;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.nc.persistence.dto.MessageDto;
import ua.kpi.nc.persistence.dto.MessageDtoType;
import ua.kpi.nc.persistence.dto.RecruitmentStatusDto;
import ua.kpi.nc.persistence.dto.StudentAppFormDto;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.*;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.persistence.model.impl.real.FormQuestionImpl;
import ua.kpi.nc.service.*;
import ua.kpi.nc.service.util.SenderService;
import ua.kpi.nc.service.util.SenderServiceImpl;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

import static ua.kpi.nc.persistence.model.enums.EmailTemplateEnum.*;
import static ua.kpi.nc.persistence.model.enums.RoleEnum.ROLE_SOFT;
import static ua.kpi.nc.persistence.model.enums.RoleEnum.ROLE_TECH;
import static ua.kpi.nc.persistence.model.enums.StatusEnum.*;
import static ua.kpi.nc.persistence.model.enums.StatusEnum.valueOf;

/**
 * Created by dima on 23.04.16.
 */
@RestController
@RequestMapping("/admin")
public class AdminManagementStudentController {

    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
    private UserService userService = ServiceFactory.getUserService();
    private FormAnswerService formAnswerService = ServiceFactory.getFormAnswerService();
    private FormQuestionService formQuestionService = ServiceFactory.getFormQuestionService();
    private StatusService statusService = ServiceFactory.getStatusService();
    private RoleService roleService = ServiceFactory.getRoleService();
    private EmailTemplateService emailTemplateService = ServiceFactory.getEmailTemplateService();
    private SenderService senderService = SenderServiceImpl.getInstance();
    private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
    private InterviewService interviewService = ServiceFactory.getInterviewService();
    private UserTimePriorityService userTimePriorityService = ServiceFactory.getUserTimePriorityService();
    private ScheduleTimePointService scheduleTimePointService = ServiceFactory.getScheduleTimePointService();
    private DecisionService decisionService = ServiceFactory.getDecisionService();


    @RequestMapping(value = "showAllStudents", method = RequestMethod.GET)
    public List<StudentAppFormDto> showStudents(@RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol,
                                                @RequestParam boolean increase) {
        Long fromRow = calculateStartRow(pageNum, rowsNum);
        List<ApplicationForm> applicationForms = applicationFormService.getApplicationFormsSorted(fromRow, rowsNum,
                sortingCol, increase);
        return getAllStudent(applicationForms);
    }

    private List<StudentAppFormDto> getAllStudent(List<ApplicationForm> applicationForms) {
        List<StudentAppFormDto> studentAppFormDtoList = new ArrayList<>();
        DecisionService decisionService = ServiceFactory.getDecisionService();
        for (ApplicationForm applicationForm : applicationForms) {
            Interview interviewSoft = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm,
                    ROLE_SOFT.getId());
            Interview interviewTech = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm,
                    ROLE_TECH.getId());
            Integer softMark = null;
            Integer techMark = null;
            Integer finalMark = null;
            if (interviewSoft != null) {
                softMark = interviewSoft.getMark();
            }
            if (interviewTech != null) {
                techMark = interviewTech.getMark();
            }
            if (softMark != null && techMark != null) {
                softMark = interviewSoft.getMark();
                techMark = interviewTech.getMark();
                finalMark = decisionService.getByMarks(softMark, techMark).getFinalMark();
            }
            studentAppFormDtoList.add(new StudentAppFormDto(applicationForm.getUser().getId(),
                    applicationForm.getId(), applicationForm.getUser().getFirstName(),
                    applicationForm.getUser().getLastName(), applicationForm.getStatus().getTitle(),
                    softMark,
                    techMark,
                    finalMark,
                    getPossibleStatus(applicationForm.getStatus())));
        }
        return studentAppFormDtoList;
    }

    @RequestMapping(value = "showFilteredStudents", method = RequestMethod.POST)
    public List<StudentAppFormDto> showFilteredStudents(@RequestBody StudentFiltrationParamsDto studentFiltrationParamsDto) {

        Long fromRow = calculateStartRow(studentFiltrationParamsDto.getPageNum(), studentFiltrationParamsDto.getRowsNum());
        List<FormQuestion> questions = new ArrayList<>();

        Gson questionGson = GsonFactory.getFormQuestionGson();
        for (String question : studentFiltrationParamsDto.getRestrictions()) {
            questions.add(questionGson.fromJson(question, FormQuestionImpl.class));
        }

        List<StudentAppFormDto> studentAppFormDtoList = new ArrayList<>();
        List<ApplicationForm> applicationForms = applicationFormService.getCurrentsApplicationFormsFiltered(fromRow,
                studentFiltrationParamsDto.getRowsNum(), studentFiltrationParamsDto.getSortingCol(), studentFiltrationParamsDto.isIncrease(),
                questions, studentFiltrationParamsDto.getStatuses(), studentFiltrationParamsDto.isActive());
        for (ApplicationForm applicationForm : applicationForms) {
            StudentAppFormDto studentAppFormDto = new StudentAppFormDto();

            studentAppFormDto.setId(applicationForm.getUser().getId());
            studentAppFormDto.setAppFormId(applicationForm.getId());
            studentAppFormDto.setFirstName(applicationForm.getUser().getFirstName());
            studentAppFormDto.setLastName(applicationForm.getUser().getLastName());
            studentAppFormDto.setStatus(applicationForm.getStatus().getTitle());
            studentAppFormDto.setPossibleStatus(getPossibleStatus(applicationForm.getStatus()));

            studentAppFormDtoList.add(studentAppFormDto);
        }
        return studentAppFormDtoList;
    }

    private Long calculateStartRow(int pageNum, Long rowsNum) {
        return (pageNum - 1) * rowsNum;
    }

    @RequestMapping(value = "getapplicationquestionsnontext", method = RequestMethod.POST)
    public List<String> getAppFormQuestionsNonText(@RequestParam String role) {
        Role roleTitle = roleService.getRoleByTitle(role);
        List<FormQuestion> formQuestionList = formQuestionService.getByRoleNonText(roleTitle);
        List<String> adapterFormQuestionList = new ArrayList<>();
        for (FormQuestion formQuestion : formQuestionList) {
            Gson questionGson = GsonFactory.getFormQuestionGson();
            String jsonResult = questionGson.toJson(formQuestion);
            adapterFormQuestionList.add(jsonResult);
        }
        return adapterFormQuestionList;
    }


    @RequestMapping(value = "changeSelectedStatuses", method = RequestMethod.POST)
    public ResponseEntity changeSelectedStatuses(@RequestParam String changeStatus,
                                                 @RequestParam List<Long> appFormIdList) {
        Status status = statusService.getByName(changeStatus);
        for (Long id : appFormIdList) {
            ApplicationForm applicationForm = applicationFormService.getApplicationFormById(id);
            applicationForm.setStatus(status);
            applicationFormService.updateApplicationForm(applicationForm);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    private List<Status> getPossibleStatus(Status status) {
        List<Status> statusList = new ArrayList<>();

        if (status.getTitle().equals(valueOf(REGISTERED))) {
            statusList.add(new Status(valueOf(REGISTERED)));
        } else if (status.getTitle().equals(valueOf(IN_REVIEW))) {
            statusList.add(new Status(valueOf(IN_REVIEW)));
            statusList.add(new Status(valueOf(APPROVED)));
            statusList.add(new Status(valueOf(REJECTED)));
        } else {
            statusList.add(new Status(valueOf(APPROVED)));
            statusList.add(new Status(valueOf(APPROVED_TO_ADVANCED_COURSES)));
            statusList.add(new Status(valueOf(APPROVED_TO_GENERAL_COURSES)));
            statusList.add(new Status(valueOf(APPROVED_TO_JOB)));
            statusList.add(new Status(valueOf(REJECTED)));
        }

        return statusList;
    }

    @RequestMapping(value = "changeStatus", method = RequestMethod.POST)
    public void changeStatuse(@RequestParam String changeStatus, @RequestParam Long appFormId) {
        Status status = statusService.getByName(changeStatus);
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(appFormId);
        applicationForm.setStatus(status);
        applicationFormService.updateApplicationForm(applicationForm);
    }

    @RequestMapping(value = "getCountOfStudents", method = RequestMethod.GET)
    public Long getCountOfStudents() {

        return userService.getAllStudentCount();
    }

    @RequestMapping(value = "getAllStatuses", method = RequestMethod.GET)
    public List<Status> getAllStatuses() {
        return statusService.getAllStatuses();
    }

    @RequestMapping(value = "getStatus", method = RequestMethod.GET)
    public Status getStatusById(@RequestParam Long id) {
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        return af.getStatus();
    }

    @RequestMapping(value = "getQuestionAnswer", method = RequestMethod.GET)
    public ResponseEntity<FormAnswer> getUniverseById(@RequestParam Long userId, @RequestParam Long questionId) {
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(userId);
        List<FormAnswer> formAnswer = formAnswerService.getByApplicationFormAndQuestion(af, formQuestionService.getById(questionId));
        if (null == formAnswer.get(0))
            return new ResponseEntity<FormAnswer>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<FormAnswer>(formAnswer.get(0), HttpStatus.OK);
    }

    @RequestMapping(value = "getRejectCount", method = RequestMethod.GET)
    public Long getRejectCount() {
        return applicationFormService.getCountRejectedAppForm();
    }

    @RequestMapping(value = "getJobCount", method = RequestMethod.GET)
    public Long getJobCount() {
        return applicationFormService.getCountToWorkAppForm();
    }

    @RequestMapping(value = "getAdvancedCount", method = RequestMethod.GET)
    public Long getAdvancedCount() {
        return applicationFormService.getCountAdvancedAppForm();
    }

    @RequestMapping(value = "getApprovedCount", method = RequestMethod.GET)
    public Long getApprovedCount() {
        return applicationFormService.getCountApprovedAppForm();
    }

    @RequestMapping(value = "getGeneralCount", method = RequestMethod.GET)
    public Long getGeneralCount() {
        return applicationFormService.getCountGeneralAppForm();
    }

    @RequestMapping(value = "calculateStatuses", method = RequestMethod.POST)
    public void calculateStatuses() {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        Status approvedStatus = statusService.getStatusById(StatusEnum.APPROVED.getId());
        List<ApplicationForm> approvedForms = applicationFormService.getByStatusAndRecruitment(approvedStatus, recruitment);

        for (ApplicationForm applicationForm : approvedForms) {
            Interview interviewSoft = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm,
                    ROLE_SOFT.getId());
            Interview interviewTech = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm,
                    ROLE_TECH.getId());
            if (interviewSoft != null && interviewSoft.getMark() != null && interviewTech != null
                    && interviewTech.getMark() != null) {
                Integer softMark = interviewSoft.getMark();
                Integer techMark = interviewTech.getMark();
                int finalMark = decisionService.getByMarks(softMark, techMark).getFinalMark();
                if (finalMark > 0) {
                    Status finalStatus = decisionService.getStatusByFinalMark(finalMark);
                    applicationForm.setStatus(finalStatus);
                    applicationFormService.updateApplicationForm(applicationForm);
                }
            }
        }
    }


    @RequestMapping(value = "announceResults", method = RequestMethod.POST)
    public String announceResults() throws MessagingException {
        Gson gson = new Gson();
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();

        EmailTemplate rejectedTemplate = emailTemplateService.getById(INTERVIEW_RESULT_REJECTED.getId());
        List<ApplicationForm> rejectedForms = applicationFormService.getRejectedAfterInterview(recruitment);
        sendMessage(rejectedForms, rejectedTemplate);

        Status approvedToJobStatus = statusService.getStatusById(APPROVED_TO_JOB.getId());
        List<ApplicationForm> approvedToJobForms = applicationFormService.getByStatusAndRecruitment(
                approvedToJobStatus, recruitment);
        EmailTemplate approvedToJobTemplate = emailTemplateService.getById(INTERVIEW_RESULT_APPROVED_JOB.getId());
        sendMessage(approvedToJobForms, approvedToJobTemplate);

        Status approvedToAdvancedStatus = statusService.getStatusById(APPROVED_TO_ADVANCED_COURSES.getId());
        List<ApplicationForm> approvedToAdvancedForms = applicationFormService.getByStatusAndRecruitment(
                approvedToAdvancedStatus, recruitment);
        EmailTemplate approvedToAdvancedTemplate = emailTemplateService.getById(INTERVIEW_RESULT_APPROVED_ADVANCED.getId());
        sendMessage(approvedToAdvancedForms, approvedToAdvancedTemplate);

        Status approvedToGeneralStatus = statusService.getStatusById(APPROVED_TO_GENERAL_COURSES.getId());
        List<ApplicationForm> approvedToGeneralForms = applicationFormService.getByStatusAndRecruitment(
                approvedToGeneralStatus, recruitment);
        EmailTemplate approvedToGeneralTemplate = emailTemplateService.getById(INTERVIEW_RESULT_APPROVED.getId());
        sendMessage(approvedToGeneralForms, approvedToGeneralTemplate);
        return gson.toJson(new MessageDto("Results were announced.",
                MessageDtoType.SUCCESS));
    }

    //TODO duplicate
    private void sendMessage(List<ApplicationForm> applicationForms, EmailTemplate emailTemplate) throws MessagingException {
        for (ApplicationForm applicationForm : applicationForms) {
            User student = applicationForm.getUser();
            String subject = emailTemplate.getTitle();
            String text = emailTemplateService.showTemplateParams(emailTemplate.getText(), student);
            senderService.send(student.getEmail(), subject, text);
        }
    }

    @RequestMapping(value = "scheduleDatesExist", method = RequestMethod.GET)
    public boolean isScheduleDatesExist() {
        return ServiceFactory.getScheduleTimePointService().isScheduleDatesExists();
    }

    @RequestMapping(value = "confirmSelection", method = RequestMethod.POST)
    public String confirmSelection() throws MessagingException {
        Gson gson = new Gson();
        if (userTimePriorityService.isSchedulePrioritiesExistStudent()) {
            return gson.toJson(new MessageDto("Selection is already confirmed.",
                    MessageDtoType.ERROR));
        }
        Long appFormInReviewCount = applicationFormService.getCountInReviewAppForm();
        if (appFormInReviewCount != 0) {
            return gson.toJson(new MessageDto("You haven't reviewed all application forms. There are still "
                    + appFormInReviewCount + " unreviewed forms.", MessageDtoType.ERROR));
        }
        ScheduleTimePointService timePointService = ServiceFactory.getScheduleTimePointService();
        if (!timePointService.isScheduleDatesExists()) {
            return gson.toJson(new MessageDto("You have to choose dates for schedule before confirming selection.",
                    MessageDtoType.ERROR));
        }
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        processApprovedStudents(recruitment);
        processRejectedStudentsSelection(recruitment);
        return gson.toJson(new MessageDto("Selection confirmed.",
                MessageDtoType.SUCCESS));
    }

    private void processApprovedStudents(Recruitment recruitment) throws MessagingException {
        Status approvedStatus = statusService.getStatusById(StatusEnum.APPROVED.getId());
        EmailTemplate approvedTemplate = emailTemplateService.getById(5L);
        List<ApplicationForm> approvedForms = applicationFormService.getByStatusAndRecruitment(approvedStatus,
                recruitment);

        for (ApplicationForm applicationForm : approvedForms) {
            User student = applicationForm.getUser();
            String subject = approvedTemplate.getTitle();
            String text = emailTemplateService.showTemplateParams(approvedTemplate.getText(), student);
            senderService.send(student.getEmail(), subject, text);
            userTimePriorityService.createStudentTimePriotities(student);
        }
    }

    //TODO duplicate
    private void processRejectedStudentsSelection(Recruitment recruitment) throws MessagingException {
        Status rejectedStatus = statusService.getStatusById(StatusEnum.REJECTED.getId());
        EmailTemplate rejectedTemplate = emailTemplateService.getById(6L);
        List<ApplicationForm> rejectedForms = applicationFormService.getByStatusAndRecruitment(rejectedStatus,
                recruitment);
        for (ApplicationForm applicationForm : rejectedForms) {
            User student = applicationForm.getUser();
            String subject = rejectedTemplate.getTitle();
            String text = emailTemplateService.showTemplateParams(rejectedTemplate.getText(), student);
            senderService.send(student.getEmail(), subject, text);
        }
    }


    @RequestMapping(value = "searchStudent", method = RequestMethod.POST)
    public List<StudentAppFormDto> searchStudentById(@RequestParam String lastName,
                                                     @RequestParam int pageNum, @RequestParam Long rowsNum) {
        Long fromRow = calculateStartRow(pageNum, rowsNum);
        List<ApplicationForm> applicationForms = applicationFormService.getSearchAppFormByNameFromToRows(lastName,
                fromRow, rowsNum);
        return getAllStudent(applicationForms);
    }

    @RequestMapping(value = "getRecruitmentStatus", method = RequestMethod.GET)
    public String getRecruitmentStatus() {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        if (recruitment != null) {
            RecruitmentStatusDto recruitmentStatusDto = new RecruitmentStatusDto();
            recruitmentStatusDto.setRecruitmentExists(true);
            recruitmentStatusDto.setScheduleExists(scheduleTimePointService.isScheduleExists());
            return new Gson().toJson(recruitmentStatusDto);
        }
        return null;
    }

    @RequestMapping(value = "getTimePoints", method = RequestMethod.GET)
    public List<ScheduleTimePoint> getTimePoints() {
        return scheduleTimePointService.getAll();
    }
}
