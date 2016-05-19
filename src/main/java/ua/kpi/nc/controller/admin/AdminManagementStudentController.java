package ua.kpi.nc.controller.admin;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.*;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.persistence.model.enums.EmailTemplateEnum;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.persistence.model.impl.real.FormQuestionImpl;
import ua.kpi.nc.service.*;
import ua.kpi.nc.service.util.SenderService;
import ua.kpi.nc.service.util.SenderServiceImpl;
import ua.kpi.nc.util.scheduling.StudentsScheduleCell;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import static ua.kpi.nc.persistence.model.enums.EmailTemplateEnum.*;
import static ua.kpi.nc.persistence.model.enums.StatusEnum.*;

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

//    @RequestMapping(value = "showAllStudents", method = RequestMethod.GET)
//    public List<StudentAppFormDto> showStudents(@RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol,
//                                                @RequestParam boolean increase) {
//        Long fromRow = (pageNum - 1) * rowsNum;
//        List<StudentAppFormDto> studentAppFormDtoList = new ArrayList<>();
//        List<ApplicationForm> applicationForms = applicationFormService.getCurrentsApplicationForms(fromRow, rowsNum, sortingCol, increase);
//        for (ApplicationForm applicationForm : applicationForms) {
//            studentAppFormDtoList.add(new StudentAppFormDto(applicationForm.getUser().getId(),
//                    applicationForm.getId(), applicationForm.getUser().getFirstName(),
//                    applicationForm.getUser().getLastName(), applicationForm.getStatus().getTitle(),
//                    getPossibleStatus(applicationForm.getStatus())));
//        }
//        return studentAppFormDtoList;
//    }

    @RequestMapping(value = "showAllStudents", method = RequestMethod.GET)
    public List<StudentAppFormDto> showStudents(@RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol,
                                                @RequestParam boolean increase) {
        Long fromRow = (pageNum - 1) * rowsNum;
        List<ApplicationForm> applicationForms = applicationFormService.getApplicationFormsSorted(fromRow, rowsNum,
                sortingCol, increase);
        return getAllStudent(applicationForms);
    }

    private List<StudentAppFormDto> getAllStudent(List<ApplicationForm> applicationForms) {
        List<StudentAppFormDto> studentAppFormDtoList = new ArrayList<>();
        DecisionService decisionService = ServiceFactory.getDecisionService();
        for (ApplicationForm applicationForm : applicationForms) {
            Interview interviewSoft = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm, RoleEnum.ROLE_SOFT.getId());
            Interview interviewTech = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm, RoleEnum.ROLE_TECH.getId());
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
                    finalMark,//TODO:Final Mark Via decision Matrix
                    getPossibleStatus(applicationForm.getStatus())));
        }
        return studentAppFormDtoList;
    }

    @RequestMapping(value = "showFilteredStudents", method = RequestMethod.GET)
    public List<StudentAppFormDto> showFilteredStudents(@RequestParam int pageNum, @RequestParam Long rowsNum,
                                                        @RequestParam Long sortingCol, @RequestParam boolean increase,
                                                        @RequestParam(value = "restrictions", required = false) String[] restrictions,
                                                        @RequestParam(value = "statuses", required = false) List<String> statuses) {
        System.out.println(statuses);
        Long fromRow = (pageNum - 1) * rowsNum;
        List<FormQuestion> questions = new ArrayList<>();
        Gson questionGson = GsonFactory.getFormQuestionGson();
        for (String question : restrictions) {
            questions.add(questionGson.fromJson(question, FormQuestionImpl.class));
        }
        List<StudentAppFormDto> studentAppFormDtoList = new ArrayList<>();
        List<ApplicationForm> applicationForms = applicationFormService.getCurrentsApplicationFormsFiltered(fromRow,
                rowsNum, sortingCol, increase, questions, statuses);
        for (ApplicationForm applicationForm : applicationForms) {
            studentAppFormDtoList.add(new StudentAppFormDto(applicationForm.getUser().getId(),
                    applicationForm.getId(), applicationForm.getUser().getFirstName(),
                    applicationForm.getUser().getLastName(), applicationForm.getStatus().getTitle(),
                    getPossibleStatus(applicationForm.getStatus())));
            System.out.println(studentAppFormDtoList.get(studentAppFormDtoList.size() - 1));
        }

        return studentAppFormDtoList;
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
    public void changeSelectedStatuses(@RequestParam String changeStatus,
                                       @RequestParam List<Long> appFormIdList) {
        System.out.println(changeStatus);
        Status status = statusService.getByName(changeStatus);

        for (Long id : appFormIdList) {
            ApplicationForm applicationForm = applicationFormService.getApplicationFormById(id);
            applicationForm.setStatus(status);
            applicationFormService.updateApplicationForm(applicationForm);
        }

    }

    private List<Status> getPossibleStatus(Status status) {
        List<Status> statusList = new ArrayList<>();

        if (status.getTitle().equals(valueOf(IN_REVIEW))) {
            statusList.add(new Status(valueOf(IN_REVIEW)));
            statusList.add(new Status(valueOf(APPROVED)));
            statusList.add(new Status(valueOf(REJECTED)));
        } else {
            statusList.add(new Status(valueOf(REJECTED)));
            statusList.add(new Status(valueOf(APPROVED)));
            statusList.add(new Status(valueOf(APPROVED_TO_ADVANCED_COURSES)));
            statusList.add(new Status(valueOf(APPROVED_TO_GENERAL_COURSES)));
            statusList.add(new Status(valueOf(APPROVED_TO_JOB)));
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

    @RequestMapping(value = "getUniverse", method = RequestMethod.GET)
    public FormAnswer getUniverseById(@RequestParam Long id) {
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        List<FormAnswer> formAnswer = formAnswerService.getByApplicationFormAndQuestion(af, formQuestionService.getById(8L));
        return formAnswer.get(0);
    }


    @RequestMapping(value = "getCourse", method = RequestMethod.GET)
    public FormAnswer getCourseById(@RequestParam Long id) {
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        List<FormAnswer> formAnswer = formAnswerService.getByApplicationFormAndQuestion(af, formQuestionService.getById(9L));
        return formAnswer.get(0);
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
    public void calulateStatuses() {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        Status approvedStatus = statusService.getStatusById(StatusEnum.APPROVED.getId());
        List<ApplicationForm> approvedForms = applicationFormService.getByStatusAndRecruitment(approvedStatus, recruitment);
        DecisionService decisionService = ServiceFactory.getDecisionService();
        for (ApplicationForm applicationForm : approvedForms) {
            Interview interviewSoft = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm,
                    RoleEnum.ROLE_SOFT.getId());
            Interview interviewTech = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm,
                    RoleEnum.ROLE_TECH.getId());
            if (interviewSoft != null && interviewSoft.getMark() != null && interviewTech != null
                    && interviewTech.getMark() != null) {
                Integer softMark = interviewSoft.getMark();
                Integer techMark = interviewTech.getMark();
                int finalMark = decisionService.getByMarks(softMark, techMark).getFinalMark();
                Status finalStatus = decisionService.getStatusByFinalMark(finalMark);
                applicationForm.setStatus(finalStatus);
                applicationFormService.updateApplicationForm(applicationForm);
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
        List<ApplicationForm> approvedToJobForms = applicationFormService.getByStatusAndRecruitment(approvedToJobStatus, recruitment);
        EmailTemplate approvedToJobTemplate = emailTemplateService.getById(INTERVIEW_RESULT_APPROVED_JOB.getId());
        sendMessage(approvedToJobForms, approvedToJobTemplate);

        Status approvedToAdvancedStatus = statusService.getStatusById(APPROVED_TO_ADVANCED_COURSES.getId());
        List<ApplicationForm> approvedToAdvancedForms = applicationFormService.getByStatusAndRecruitment(approvedToAdvancedStatus, recruitment);
        EmailTemplate approvedToAdvancedTemplate = emailTemplateService.getById(INTERVIEW_RESULT_APPROVED_ADVANCED.getId());
        sendMessage(approvedToAdvancedForms, approvedToAdvancedTemplate);

        Status approvedToGeneralStatus = statusService.getStatusById(APPROVED_TO_GENERAL_COURSES.getId());
        List<ApplicationForm> approvedToGeneralForms = applicationFormService.getByStatusAndRecruitment(approvedToGeneralStatus, recruitment);
        EmailTemplate approvedToGeneralTemplate = emailTemplateService.getById(INTERVIEW_RESULT_APPROVED.getId());
        sendMessage(approvedToGeneralForms, approvedToGeneralTemplate);

        return gson.toJson(new MessageDto("Results were announced.",
                MessageDtoType.SUCCESS));
    }

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
        if (userTimePriorityService.isSchedulePrioritiesExist()) {
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
        Long fromRow = (pageNum - 1) * rowsNum;
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
