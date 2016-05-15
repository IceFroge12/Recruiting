package ua.kpi.nc.controller.admin;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.FormQuestionDto;
import ua.kpi.nc.persistence.dto.MessageDto;
import ua.kpi.nc.persistence.dto.MessageDtoType;
import ua.kpi.nc.persistence.dto.StudentAppFormDto;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.persistence.model.impl.real.FormQuestionImpl;
import ua.kpi.nc.service.*;
import ua.kpi.nc.service.util.SenderService;
import ua.kpi.nc.service.util.SenderServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

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
        List<StudentAppFormDto> studentAppFormDtoList = new ArrayList<>();
        List<ApplicationForm> applicationForms = applicationFormService.getApplicationFormsSorted(fromRow, rowsNum, sortingCol, increase);
        for (ApplicationForm applicationForm : applicationForms) {
            Interview interviewSoft = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm, RoleEnum.ROLE_SOFT.getId());
            Interview interviewTech = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm,RoleEnum.ROLE_TECH.getId());
            studentAppFormDtoList.add(new StudentAppFormDto(applicationForm.getUser().getId(),
                    applicationForm.getId(), applicationForm.getUser().getFirstName(),
                    applicationForm.getUser().getLastName(), applicationForm.getStatus().getTitle(),
                    null == interviewSoft ? 0 : interviewSoft.getMark(),
                    null == interviewTech ? 0 : interviewTech.getMark(),
                    null,//TODO:Final Mark Via decision Matrix
                    getPossibleStatus(applicationForm.getStatus())));
        }
        return studentAppFormDtoList;
    }

    @RequestMapping(value = "showFilteredStudents", method = RequestMethod.GET)
    public List<StudentAppFormDto> showFilteredStudents(@RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol,
                                                @RequestParam boolean increase, @RequestParam("restrictions") String[] restrictions) {
        Long fromRow = (pageNum - 1) * rowsNum;
        List<FormQuestion> questions = new ArrayList<>();
        Gson questionGson = GsonFactory.getFormQuestionGson();
        for(String question: restrictions) {
            questions.add(questionGson.fromJson(question, FormQuestionImpl.class));
        }
        System.out.println(questions);

        List<StudentAppFormDto> studentAppFormDtoList = new ArrayList<>();
        List<ApplicationForm> applicationForms = applicationFormService.getCurrentsApplicationFormsFiltered(fromRow, rowsNum, sortingCol, increase, questions);
        for (ApplicationForm applicationForm : applicationForms) {
            studentAppFormDtoList.add(new StudentAppFormDto(applicationForm.getUser().getId(),
                    applicationForm.getId(), applicationForm.getUser().getFirstName(),
                    applicationForm.getUser().getLastName(), applicationForm.getStatus().getTitle(),
                    getPossibleStatus(applicationForm.getStatus())));
            System.out.println(studentAppFormDtoList.get(studentAppFormDtoList.size()-1));
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
                                       @RequestParam List<Long>appFormIdList) {
        System.out.println(changeStatus);
        Status status = statusService.getByName(changeStatus);

       for (Long id:appFormIdList){
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

    @RequestMapping(value = "getGeneralCount", method = RequestMethod.GET)
    public Long getGeneralCount() {
        return applicationFormService.getCountGeneralAppForm();
    }

//    @RequestMapping(value = "confirmSelection", method = RequestMethod.POST)
//    public boolean changeStatus(@RequestParam Long id, @RequestBody Status status) {
//        System.out.println(id + "\n" + status);
////        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
////        af.setStatus(status);
//        return true;
//    }
    
    @RequestMapping(value = "confirmSelection", method = RequestMethod.POST)
  	public String confirmSelection() throws MessagingException {
  		Gson gson = new Gson();
  		Long appFormInReviewCount = applicationFormService.getCountInReviewAppForm();
  		if (appFormInReviewCount != 0) {
  			return gson.toJson(new MessageDto("You haven't reviewed all application forms. There are still "
  					+ appFormInReviewCount + " unreviewed forms.", MessageDtoType.ERROR));
  		}
  		ScheduleTimePointService timePointService = ServiceFactory.getScheduleTimePointService();
  		if (!timePointService.isScheduleExists()) {
  			return gson.toJson(new MessageDto("You have to choose dates for schedule before confirming selection.",
  					MessageDtoType.ERROR));
  		}
  		Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
  		processApprovedStudents(recruitment);
  		processRejectedStudents(recruitment);
  		return gson.toJson(new MessageDto("Selection confirmed.",
  				MessageDtoType.ERROR));
  	}

  	private void processApprovedStudents(Recruitment recruitment) throws MessagingException {
  		Status approvedStatus = statusService.getStatusById(StatusEnum.APPROVED.getId());
  		EmailTemplate approvedTemplate = emailTemplateService.getById(5L);
  		List<ApplicationForm> approvedForms = applicationFormService.getByStatusAndRecruitment(approvedStatus,
  				recruitment);
  		UserTimePriorityService userTimePriorityService = ServiceFactory.getUserTimePriorityService();
  		
  		for (ApplicationForm applicationForm : approvedForms) {
  			User student = applicationForm.getUser();
  			String subject = approvedTemplate.getTitle();
  			String text = emailTemplateService.showTemplateParams(approvedTemplate.getText(), student);
  			senderService.send(student.getEmail(), subject, text);
  			userTimePriorityService.createStudentTimePriotities(student);
  		}
  	}
  	
  	
  	private void processRejectedStudents(Recruitment recruitment) throws MessagingException {
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
    public List<User> searchStudentById(@RequestParam String lastName,
                                        @RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol) {
        Long fromRow = (pageNum - 1) * rowsNum;
        return userService.getStudentsByNameFromToRows(lastName, fromRow, rowsNum, sortingCol);
    }

}
