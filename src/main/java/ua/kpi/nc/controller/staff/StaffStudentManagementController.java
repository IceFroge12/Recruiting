package ua.kpi.nc.controller.staff;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import ua.kpi.nc.persistence.dto.AssigningDto;
import ua.kpi.nc.persistence.dto.MessageDto;
import ua.kpi.nc.persistence.dto.MessageDtoType;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerImpl;
import ua.kpi.nc.persistence.model.impl.real.InterviewImpl;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.FormQuestionService;
import ua.kpi.nc.service.InterviewService;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

@RestController
@RequestMapping("/staff")
public class StaffStudentManagementController {

	private InterviewService interviewService = ServiceFactory.getInterviewService();
	private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
	private UserService userService = ServiceFactory.getUserService();
	private RoleService roleService = ServiceFactory.getRoleService();

	private static Gson gson = new Gson();

	private static final String ASSIGNED_TO_YOU_MESSAGE = gson
			.toJson(new MessageDto("This student is already assigned to you.", MessageDtoType.WARNING));
	
	private static final String ASSIGNED_TO_ANOTHER_MESSAGE = gson.toJson(
			new MessageDto("This student is already assigned to another interviewer.", MessageDtoType.WARNING));
	
	private static final String CANNOT_ASSIGN_MESSAGE = gson.toJson(new MessageDto("Cannot assign this student.", MessageDtoType.ERROR));
	private static final String MUST_CHOOSE_ROLE_MESSAGE =gson.toJson(new MessageDto("You must choose role to assign.", MessageDtoType.WARNING));
	private static final String ASSIGN_SUCCESS_MESSAGE = gson.toJson(new MessageDto("This student was assigned to you.", MessageDtoType.SUCCESS));
	private static final String UNASSIGN_SUCCESS_MESSAGE = gson.toJson(new MessageDto("Student was unassigned.", MessageDtoType.SUCCESS));;
	private static final String UNASSIGN_ERROR_MESSAGE = gson.toJson(new MessageDto("Cannot deassign this application form.", MessageDtoType.ERROR));
	
	
	@RequestMapping(value = "assigned", method = RequestMethod.GET)
	public String getAssignedStudents() {
		User interviewer = userService.getAuthorizedUser();
		List<ApplicationForm> assignedForms = applicationFormService.getByInterviewer(interviewer);
		JsonArray jsonStudents = new JsonArray();
		for (ApplicationForm applicationForm : assignedForms) {
			JsonObject jsonStudent = applicationFormToJson(applicationForm, interviewer);
			jsonStudent.add("interviews", assignedInterviewsToJson(applicationForm, interviewer));
			jsonStudents.add(jsonStudent);
		}
		return gson.toJson(jsonStudents);
	}

	@RequestMapping(value = "getById/{id}", method = RequestMethod.GET)
	public String getStudentById(@PathVariable Long id) {
		User interviewer = userService.getAuthorizedUser();
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(id);
		if (applicationForm == null || !isApplicaionFormActual(applicationForm)) {
			return null;
		}
		JsonArray jsonInterviews = possibleInterviewsToJson(applicationForm, interviewer);
		if (jsonInterviews.size() == 0) {
			if (isFormAssigned(applicationForm, interviewer)) {
				return ASSIGNED_TO_YOU_MESSAGE;
			}
			return ASSIGNED_TO_ANOTHER_MESSAGE;
		} else {
			JsonObject jsonStudent = applicationFormToJson(applicationForm, interviewer);
			jsonStudent.add("interviews", jsonInterviews);
			return gson.toJson(jsonStudent);
		}

	}

	@RequestMapping(value = "assign", method = RequestMethod.POST)
	public String assignStudent(@RequestBody AssigningDto assigningDto) {
		User interviewer = userService.getAuthorizedUser();
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(assigningDto.getId());
		if (!isApplicaionFormActual(applicationForm)) {
			return CANNOT_ASSIGN_MESSAGE;
		}
		if (assigningDto.getRoles().length == 0) {
			return MUST_CHOOSE_ROLE_MESSAGE;
		}
		for (Long roleId : assigningDto.getRoles()) {
			Role role = roleService.getRoleById(roleId);
			if (roleService.isInterviewerRole(role)
					&& !applicationFormService.isAssignedForThisRole(applicationForm, role)) {
				createInterview(applicationForm, interviewer, role);
			}
		}
		return ASSIGN_SUCCESS_MESSAGE;
	}

	private boolean createInterview(ApplicationForm applicationForm, User interviewer, Role role) {
		Interview interview = new InterviewImpl();
		interview.setInterviewer(interviewer);
		interview.setApplicationForm(applicationForm);
		interview.setDate(new Timestamp(System.currentTimeMillis()));
		interview.setRole(role);
		FormQuestionService questionService = ServiceFactory.getFormQuestionService();
		List<FormQuestion> questions = questionService.getEnableByRole(role);
		List<FormAnswer> answers = new ArrayList<>();
		for (FormQuestion formQuestion : questions) {
			if (formQuestion.isEnable()) {
				FormAnswer formAnswer = new FormAnswerImpl();
				formAnswer.setFormQuestion(formQuestion);
				formAnswer.setInterview(interview);
				answers.add(formAnswer);
			}
		}
		return interviewService.insertInterviewWithAnswers(interview, answers);
	}

	@RequestMapping(value = "deassign/{id}", method = RequestMethod.GET)
	public String deassignStudent(@PathVariable Long id) {
		User interviewer = userService.getAuthorizedUser();
		Interview interview = interviewService.getById(id);
		if (interview.getInterviewer().getId().equals(interviewer.getId())
				&& isApplicaionFormActual(interview.getApplicationForm())) {
			interviewService.deleteInterview(interview);
			return UNASSIGN_SUCCESS_MESSAGE;
		} else {
			return UNASSIGN_ERROR_MESSAGE;
		}
	}

	//TODO delete interviewer
	private JsonObject applicationFormToJson(ApplicationForm applicationForm, User interviewer) {
		User student = applicationForm.getUser();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", applicationForm.getId());
		jsonObject.addProperty("photoScope", applicationForm.getPhotoScope());
		jsonObject.addProperty("firstName", student.getFirstName());
		jsonObject.addProperty("secondName", student.getSecondName());
		jsonObject.addProperty("lastName", student.getLastName());
		return jsonObject;
	}

	private JsonArray assignedInterviewsToJson(ApplicationForm applicationForm, User interviewer) {
		JsonArray jsonInterviews = new JsonArray();
		for (Interview interview : applicationForm.getInterviews()) {
			if (interview.getInterviewer().getId().equals(interviewer.getId())) {
				JsonObject jsonInterview = new JsonObject();
				jsonInterview.addProperty("id", interview.getId());
				jsonInterview.addProperty("role", interview.getRole().getId());
				jsonInterview.addProperty("hasMark", interview.getMark() != null);
				jsonInterviews.add(jsonInterview);
			}
		}
		return jsonInterviews;
	}

	private JsonArray possibleInterviewsToJson(ApplicationForm applicationForm, User interviewer) {
		JsonArray jsonInterviews = new JsonArray();
		for (Role role : interviewer.getRoles()) {
			if (roleService.isInterviewerRole(role)
					&& !applicationFormService.isAssignedForThisRole(applicationForm, role)) {
				JsonObject jsonInterview = new JsonObject();
				jsonInterview.addProperty("role", role.getId());
				jsonInterviews.add(jsonInterview);
			}
		}
		return jsonInterviews;
	}

	private boolean isApplicaionFormActual(ApplicationForm applicationForm) {
		return applicationForm.isActive() && Objects.equals(applicationForm.getStatus().getId(),
				StatusEnum.APPROVED.getId());
	}

	private boolean isFormAssigned(ApplicationForm applicationForm, User interviewer) {
		for (Interview interview : interviewService.getByApplicationForm(applicationForm)) {
			if (!Objects.equals(interview.getInterviewer().getId(), interviewer.getId())) {
				return false;
			}
		}
		return true;
	}

}
