package ua.kpi.nc.controller.staff;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import ua.kpi.nc.persistence.dto.MessageDto;
import ua.kpi.nc.persistence.dto.MessageDtoType;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerImpl;
import ua.kpi.nc.persistence.model.impl.real.InterviewImpl;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.FormQuestionService;
import ua.kpi.nc.service.InterviewService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

@Controller
@RequestMapping("/staff")
public class StaffStudentManagementController {

	private Gson gson = new Gson();
	private InterviewService interviewService = ServiceFactory.getInterviewService();
	private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
	private UserService userService = ServiceFactory.getUserService();

	@RequestMapping(value = "assigned", method = RequestMethod.GET)
	@ResponseBody
	public String getAssignedStudents() {
		User interviewer = getAuthorizedUser();
		List<Interview> interviews = interviewService.getByInterviewer(interviewer);
		JsonArray jsonStudents = new JsonArray();
		for (Interview interview : interviews) {
			ApplicationForm applicationForm = interview.getApplicationForm();
			if (isApplicaionFormActual(applicationForm)) {
				jsonStudents.add(applicationFormToJson(applicationForm));
			}
		}
		return gson.toJson(jsonStudents);
	}

	@RequestMapping(value = "getById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getStudentById(@PathVariable Long id) {
		User interviewer = getAuthorizedUser();
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(id);
		if (applicationForm == null || !isApplicaionFormActual(applicationForm)
				|| isFormAssigned(applicationForm, interviewer)) {
			return null;
		}
		return gson.toJson(applicationFormToJson(applicationForm));
	}

	@RequestMapping(value = "assign/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String assignStudent(@PathVariable Long id) {
		User interviewer = getAuthorizedUser();
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(id);
		if (!isApplicaionFormActual(applicationForm)) {
			return gson.toJson(new MessageDto("Cannot assign this student.", MessageDtoType.ERROR));
		}
		List<Interview> interviews = interviewService.getByApplicationForm(applicationForm);
		for (Interview interview : interviews) {
			for (Role interviewRole : interviewer.getRoles()) {
				if (Objects.equals(interview.getRole(), interviewRole)) {
					return gson.toJson(new MessageDto("This student was already assigned to this type of interviewer",
							MessageDtoType.ERROR));
				}
			}
		}
		if (createInterview(applicationForm, interviewer)) {
			return gson.toJson(new MessageDto("This student was assigned to you.", MessageDtoType.SUCCESS));
		} else {
			return gson.toJson(new MessageDto("Cannot assign this student.", MessageDtoType.ERROR));
		}
	}

	private boolean createInterview(ApplicationForm applicationForm, User interviewer) {
		Interview interview = new InterviewImpl();
		interview.setInterviewer(interviewer);
		interview.setApplicationForm(applicationForm);
		interview.setDate(new Timestamp(System.currentTimeMillis()));
		for (Role role : interviewer.getRoles()) {
			if (role.getRoleName().equals(RoleEnum.SOFT.name()) || role.getRoleName().equals(RoleEnum.TECH.name())) {
				interview.setRole(role);
			}
		}
		FormQuestionService questionService = ServiceFactory.getFormQuestionService();
		List<FormQuestion> questions = questionService.getByRole(interview.getRole());
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
	@ResponseBody
	public String deassignStudent(@PathVariable Long id) {
		User interviewer = getAuthorizedUser();
		ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(id);
		if (!isApplicaionFormActual(applicationForm)) {
			return gson.toJson(new MessageDto("Cannot deassign this application form.", MessageDtoType.ERROR));
		}
		InterviewService interviewService = ServiceFactory.getInterviewService();
		List<Interview> interviews = interviewService.getByApplicationForm(applicationForm);
		if (isFormAssigned(applicationForm, interviewer))
			for (Interview interview : interviews) {
				if (Objects.equals(interview.getInterviewer().getId(), interviewer.getId())) {
					interviewService.deleteInterview(interview);
					return gson.toJson(new MessageDto("Student was deassigned.", MessageDtoType.SUCCESS));
				}
			}
		return gson.toJson(new MessageDto("This student is not assigned to you.", MessageDtoType.ERROR));
	}

	private JsonObject applicationFormToJson(ApplicationForm applicationForm) {
		User student = applicationForm.getUser();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", applicationForm.getId());
		jsonObject.addProperty("photoScope", applicationForm.getPhotoScope());
		jsonObject.addProperty("firstName", student.getFirstName());
		jsonObject.addProperty("secondName", student.getSecondName());
		jsonObject.addProperty("lastName", student.getLastName());
		return jsonObject;
	}

	private boolean isApplicaionFormActual(ApplicationForm applicationForm) {
		if (!applicationForm.isActive()) {
			return false;
		}
		if (!Objects.equals(applicationForm.getStatus().getId(), StatusEnum.APPROVED.getId())) {
			return false;
		}
		return true;
	}

	private boolean isFormAssigned(ApplicationForm applicationForm, User interviewer) {
		for (Interview interview : interviewService.getByApplicationForm(applicationForm)) {
			if (Objects.equals(interview.getInterviewer().getId(), interviewer.getId())) {
				return true;
			}
		}
		return false;
	}

	private User getAuthorizedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		System.out.println("!!!!!!!" + name);
		return userService.getUserByUsername(name);
	}
}
