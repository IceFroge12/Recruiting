package ua.kpi.nc.controller.staff;

import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;
import org.springframework.web.bind.annotation.*;

import ua.kpi.nc.persistence.dto.InterviewDto;
import ua.kpi.nc.persistence.dto.MessageDto;
import ua.kpi.nc.persistence.dto.MessageDtoType;
import ua.kpi.nc.persistence.dto.StudentAnswerDto;
import ua.kpi.nc.persistence.dto.StudentAppFormQuestionDto;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.Interview;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.persistence.model.enums.FormQuestionTypeEnum;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerImpl;
import ua.kpi.nc.service.*;
import ua.kpi.nc.util.export.ExportApplicationForm;
import ua.kpi.nc.util.export.ExportApplicationFormImp;
import util.form.FormAnswerProcessor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Korzh
 */
@RestController
@RequestMapping("/staff")
public class StaffInterviewController {

	private FormAnswerService formAnswerService;
	private ApplicationFormService applicationFormService;
	private UserService userService;
	private FormQuestionService formQuestionService;
	private FormAnswerVariantService formAnswerVariantService;
	private InterviewService interviewService;

	private static final Gson gson = new Gson();
	
	private static final String INTERVIEW_UPDATED_MESSAGE = gson.toJson(new MessageDto(
			"Interview was updated.", MessageDtoType.SUCCESS));
	private static final String NOT_ASSIGNED_MESSAGE = gson.toJson(new MessageDto(
			"This student is not assigned to you.", MessageDtoType.ERROR));
	
	public StaffInterviewController() {
		formAnswerService = ServiceFactory.getFormAnswerService();
		applicationFormService = ServiceFactory.getApplicationFormService();
		userService = ServiceFactory.getUserService();
		formQuestionService = ServiceFactory.getFormQuestionService();
		formAnswerVariantService = ServiceFactory.getFormAnswerVariantService();
		interviewService = ServiceFactory.getInterviewService();
	}

	@RequestMapping(value = "getApplicationForm/{applicationFormId}", method = RequestMethod.POST)
	public String getApplicationForm(@PathVariable Long applicationFormId) {
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
		Gson applicationFormGson = GsonFactory.getApplicationFormGson();
		return applicationFormGson.toJson(applicationForm);
	}

	@RequestMapping(value = "appForm/{applicationFormId}", method = RequestMethod.GET)
	public void exportAppform(@PathVariable Long applicationFormId, HttpServletResponse response) throws IOException, DocumentException {
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
		response.setHeader("Content-Disposition", "inline; filename=ApplicationForm.pdf");
		response.setContentType("application/pdf");
		ExportApplicationForm pdfAppForm = new ExportApplicationFormImp();
		pdfAppForm.export(applicationForm, response);
	}

	@RequestMapping(value = "getInterview/{applicationFormId}/{role}", method = RequestMethod.POST)
	public String getInterview(@PathVariable Long applicationFormId, @PathVariable Long role) {
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
		List<Interview> interviews = interviewService.getByApplicationForm(applicationForm);
		Interview interview = null;
		for (Interview i : interviews) {
			if (i.getRole().getId().equals(role)) {
				interview = i;
			}
		}
		Gson interviewGson = GsonFactory.getInterviewGson();
		return interviewGson.toJson(interview);
	}

	@RequestMapping(value = "submitInterview", method = RequestMethod.POST)
	public String saveInterview(@RequestBody InterviewDto interviewDto) {
		User interviewer = userService.getAuthorizedUser();
		Interview interview = interviewService.getById(interviewDto.getId());
		if (interview.getInterviewer().getId().equals(interviewer.getId())) {
			interview.setAdequateMark(interviewDto.isAdequateMark());
			interview.setMark(interviewDto.getMark());
			FormAnswerProcessor formAnswerProcessor = new FormAnswerProcessor(interview);
			for (StudentAppFormQuestionDto questionDto : interviewDto.getQuestions()) {
				FormQuestion formQuestion = formQuestionService.getById(questionDto.getId());
				formAnswerProcessor.setFormQuestion(formQuestion);
				List<FormAnswer> answers = formAnswerService.getByInterviewAndQuestion(interview, formQuestion);
				formAnswerProcessor.updateAnswers(questionDto.getAnswers(), answers);
			}
			interview.setAnswers(formAnswerProcessor.getAnswers());
			interviewService.updateInterview(interview);
			return INTERVIEW_UPDATED_MESSAGE;
		} else {
			return NOT_ASSIGNED_MESSAGE;
		}
	}

	@RequestMapping(value = "getRoles/{applicationFormId}", method = RequestMethod.GET)
	public Set<Role> getRoles(@PathVariable Long applicationFormId) {
		User interviwer = userService.getAuthorizedUser();
		List<Interview> interviews = new ArrayList<>();
		for (Interview interview : interviewService.getByInterviewer(interviwer)) {
			if (interview.getApplicationForm().getId().equals(applicationFormId)) {
				interviews.add(interview);
			}
		}
		Set<Role> interviwerRoles = new HashSet<>();
		for (Role role : interviwer.getRoles()) {
			for (Interview i : interviews) {
				if (i.getRole().getId().equals(role.getId())) {
					interviwerRoles.add(role);
				}
			}
		}
		return interviwerRoles;
	}

    @RequestMapping(value = "getAdequateMark/{applicationFormId}", method = RequestMethod.GET)
    public boolean getAdequateMark(@PathVariable Long applicationFormId) {
        User interviewer = userService.getAuthorizedUser();
        return interviewService.haveNonAdequateMark(applicationFormId,interviewer.getId());
    }
}
