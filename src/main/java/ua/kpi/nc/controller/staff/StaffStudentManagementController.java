package ua.kpi.nc.controller.staff;

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

import ua.kpi.nc.persistence.dto.AssignedInterviewDto;
import ua.kpi.nc.persistence.dto.AssignedStudentDto;
import ua.kpi.nc.persistence.dto.AssigningDto;
import ua.kpi.nc.persistence.dto.MessageDto;
import ua.kpi.nc.persistence.dto.MessageDtoType;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.service.ApplicationFormService;
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
	
	private static final String STUDENT_NOT_FOUND_MESSAGE = gson.toJson(new MessageDto("Student not found.", MessageDtoType.WARNING));
	private static final String CANNOT_ASSIGN_MESSAGE = gson.toJson(new MessageDto("Cannot assign this student.", MessageDtoType.ERROR));
	private static final String MUST_CHOOSE_ROLE_MESSAGE = gson.toJson(new MessageDto("You must choose role to assign.", MessageDtoType.WARNING));
	private static final String ASSIGN_SUCCESS_MESSAGE = gson.toJson(new MessageDto("This student was assigned to you.", MessageDtoType.SUCCESS));
	private static final String UNASSIGN_SUCCESS_MESSAGE = gson.toJson(new MessageDto("Student was unassigned.", MessageDtoType.SUCCESS));
	private static final String UNASSIGN_ERROR_MESSAGE = gson.toJson(new MessageDto("Cannot deassign this application form.", MessageDtoType.ERROR));
	
	
	@RequestMapping(value = "assigned", method = RequestMethod.GET)
	public String getAssignedStudents() {
		User interviewer = userService.getAuthorizedUser();
		List<ApplicationForm> assignedForms = applicationFormService.getByInterviewer(interviewer);
		return gson.toJson(assignedFormsToJson(assignedForms, interviewer));
	}

	@RequestMapping(value = "getById/{id}", method = RequestMethod.GET)
	public String getStudentById(@PathVariable Long id) {
		User interviewer = userService.getAuthorizedUser();
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(id);
		if (applicationForm == null || !isApplicaionFormActual(applicationForm)) {
			return STUDENT_NOT_FOUND_MESSAGE;
		}
		List<Role> possibleRolesToInterviews = roleService.getPossibleInterviewsRoles(applicationForm, interviewer);
		if (possibleRolesToInterviews.isEmpty()) {
			if (interviewService.isFormAssigned(applicationForm, interviewer)) {
				return ASSIGNED_TO_YOU_MESSAGE;
			} else {
				return ASSIGNED_TO_ANOTHER_MESSAGE;
			}
		} else {
			return gson.toJson(studentToJson(applicationForm, possibleRolesToInterviews));
		}

	}

	@RequestMapping(value = "assign", method = RequestMethod.POST)
	public String assignStudent(@RequestBody AssigningDto assigningDto) {
		User interviewer = userService.getAuthorizedUser();
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(assigningDto.getId());
		if (applicationForm == null && !isApplicaionFormActual(applicationForm)) {
			return CANNOT_ASSIGN_MESSAGE;
		}
		if (assigningDto.getRoles().length == 0) {
			return MUST_CHOOSE_ROLE_MESSAGE;
		}
		for (Long roleId : assigningDto.getRoles()) {
			Role role = roleService.getRoleById(roleId);
			if (role != null) {
				interviewService.assignStudent(applicationForm, interviewer, role);
			}
		}
		return ASSIGN_SUCCESS_MESSAGE;
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

	private boolean isApplicaionFormActual(ApplicationForm applicationForm) {
		return applicationForm.isActive() && Objects.equals(applicationForm.getStatus().getId(),
				StatusEnum.APPROVED.getId());
	}
	
	private AssignedStudentDto applicationFormToJson(ApplicationForm applicationForm) {
		User student = applicationForm.getUser();
		AssignedStudentDto assignedStudentDto = new AssignedStudentDto();
		assignedStudentDto.setFirstName(student.getFirstName());
		assignedStudentDto.setId(applicationForm.getId());
		assignedStudentDto.setLastName(student.getLastName());
		assignedStudentDto.setPhotoScope(applicationForm.getPhotoScope());
		assignedStudentDto.setSecondName(student.getSecondName());
		return assignedStudentDto;
	}

	private List<AssignedInterviewDto> assignedInterviewsToJson(ApplicationForm applicationForm, User interviewer) {
		List<AssignedInterviewDto> assignedInterviews = new ArrayList<>();
		for (Interview interview : applicationForm.getInterviews()) {
			if (interview.getInterviewer().getId().equals(interviewer.getId())) {
				AssignedInterviewDto assignedInterview = new AssignedInterviewDto();
				assignedInterview.setId(interview.getId());
				assignedInterview.setRole(interview.getRole().getId());
				assignedInterview.setHasMark(interview.getMark() != null);
				assignedInterviews.add(assignedInterview);
			}
		}
		return assignedInterviews;
	}

	private List<AssignedInterviewDto> possibleInterviewsToJson(List<Role> possibleRoles) {
		List<AssignedInterviewDto> assignedInterviews = new ArrayList<>();
		for (Role role : possibleRoles) {
				AssignedInterviewDto assignedInterview = new AssignedInterviewDto();
				assignedInterview.setRole(role.getId());
				assignedInterviews.add(assignedInterview);
		}
		return assignedInterviews;
	}


	private List<AssignedStudentDto> assignedFormsToJson(List<ApplicationForm> assignedForms, User interviewer) {
		List<AssignedStudentDto> assignedStudents = new ArrayList<>();
		for (ApplicationForm applicationForm : assignedForms) {
			AssignedStudentDto assignedStudent = applicationFormToJson(applicationForm);
			assignedStudent.setInterviews(assignedInterviewsToJson(applicationForm, interviewer));
			assignedStudents.add(assignedStudent);
		}
		return assignedStudents;
	}

	private AssignedStudentDto studentToJson(ApplicationForm applicationForm, List<Role> possibleRolesToInterviews) {
		AssignedStudentDto assignedStudent = applicationFormToJson(applicationForm);
		assignedStudent.setInterviews(possibleInterviewsToJson(possibleRolesToInterviews));
		return assignedStudent;
	}
	
}
