package ua.kpi.nc.controller.staff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

@Controller
@RequestMapping("/staff")
public class StudentManagementController {

	private Gson gson = new Gson();

	@RequestMapping(value = "assigned", method = RequestMethod.GET)
	@ResponseBody
	public String getAssignedStudents() {
		User interviewer = ServiceFactory.getUserService().getUserByID(33L);//GET CURRENT USER
		InterviewService interviewService = ServiceFactory.getInterviewService();
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
		ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(id);
		return gson.toJson(applicationFormToJson(applicationForm));
	}
	//TODO deasign and assign mapped - "assign/{id}", method - Post
//	@RequestMapping(value = "assign/{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public String assignStudent(@PathVariable Long id) {
//		User interviewer = ServiceFactory.getUserService().getUserByID(33L);//GET CURRENT USER
//		ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
//		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(id);
//		if (!isApplicaionFormActual(applicationForm)) {
//			return null;
//		}
//		InterviewService interviewService = ServiceFactory.getInterviewService();
//		List<Interview> interviews = interviewService.getByApplicationForm(applicationForm);
//		for (Interview interview : interviews) {
//			for (Role interviewRole : interviewer.getRoles()) {
//				if (Objects.equals(interview.getRole(), interviewRole)) {
//					return "This student was already assigned to this type of interviewer";
//				}
//			}
//		}
//		Interview interview = new InterviewImpl();
//		interview.setInterviewer(interviewer);
//		interview.setApplicationForm(applicationForm);
//		for (Role role : interviewer.getRoles()) {
//			if (role.getRoleName().equals(RoleEnum.SOFT.name()) || role.getRoleName().equals(RoleEnum.TECH.name())) {
//				interview.setRole(role);
//			}
//		}
//		FormQuestionService questionService = ServiceFactory.getFormQuestionService();
//		List<FormQuestion> questions = questionService.getByRole(interview.getRole());
//		List<FormAnswer> answers = new ArrayList<>();
//		for (FormQuestion formQuestion : questions) {
//			if (formQuestion.isEnable()) {
//				FormAnswer formAnswer = new FormAnswerImpl();
//				formAnswer.setFormQuestion(formQuestion);
//				formAnswer.setInterview(interview);
//				answers.add(formAnswer);
//			}
//		}
//		interviewService.insertInterviewWithAnswers(interview, answers);
//		return "Student assigned";
//	}

	@RequestMapping(value = "assign/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String deassignStudent(@PathVariable Long id) {
		User interviewer = ServiceFactory.getUserService().getUserByID(33L);//GET CURRENT USER
		ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(id);
		if (!isApplicaionFormActual(applicationForm)) {
			return null;
		}
		InterviewService interviewService = ServiceFactory.getInterviewService();
		List<Interview> interviews = interviewService.getByApplicationForm(applicationForm);
		for (Interview interview : interviews) {
			if (Objects.equals(interview.getInterviewer().getId(), interviewer.getId())) {
				interviewService.deleteInterview(interview);
			}
		}
		return null;
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

}
