package ua.kpi.nc.controller.staff;

import com.google.gson.Gson;
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

import javax.servlet.http.HttpServletResponse;
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
		String jsonResult = applicationFormGson.toJson(applicationForm);
		return jsonResult;
	}

	@RequestMapping(value = "appForm/{applicationFormId}", method = RequestMethod.GET)
	public void exportAppform(@PathVariable Long applicationFormId, HttpServletResponse response) throws Exception {
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
		response.setHeader("Content-Disposition", String.format("inline; filename=ApplicationForm.pdf"));
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
		String jsonResult = interviewGson.toJson(interview);
		return jsonResult;
	}

	@RequestMapping(value = "submitInterview", method = RequestMethod.POST)
	public String saveInterview(@RequestBody InterviewDto interviewDto) {
		User interviewer = userService.getAuthorizedUser();
		Interview interview = interviewService.getById(interviewDto.getId());
		Gson gson = new Gson();
		if (interview.getInterviewer().getId().equals(interviewer.getId())) {
			interview.setAdequateMark(interviewDto.isAdequateMark());
			interview.setMark(interviewDto.getMark());
			for (StudentAppFormQuestionDto questionDto : interviewDto.getQuestions()) {
				FormQuestion formQuestion = formQuestionService.getById(questionDto.getId());
				List<FormAnswer> answers = formAnswerService.getByInterviewAndQuestion(interview, formQuestion);
				updateAnswers(formQuestion, answers, questionDto.getAnswers(), interview);
			}
			interviewService.updateInterview(interview);
			return gson.toJson(new MessageDto("Interview was updated.", MessageDtoType.SUCCESS));
		}
		return gson.toJson(new MessageDto("This student is not assigned to you.", MessageDtoType.ERROR));
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
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
        User interviewer = userService.getAuthorizedUser();
        return interviewService.haveNonAdequateMark(applicationFormId,interviewer.getId());
    }

	private void updateAnswers(FormQuestion formQuestion, List<FormAnswer> answers, List<StudentAnswerDto> answersDto,
			Interview interview) {
		String questionType = formQuestion.getQuestionType().getTypeTitle();
		if (FormQuestionTypeEnum.CHECKBOX.getTitle().equals(questionType)) {
			int i = 0;
			for (i = 0; i < answersDto.size() && i < answers.size(); i++) {
				StudentAnswerDto answerDto = answersDto.get(i);
				FormAnswer answer = answers.get(i);
				FormAnswerVariant variant = formAnswerVariantService
						.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
				answer.setFormAnswerVariant(variant);
				formAnswerService.updateFormAnswer(answer);
			}
			if (answersDto.size() < answers.size()) {
				for (; i < answers.size() - 1; i++) {
					FormAnswer answer = answers.get(i);
					formAnswerService.deleteFormAnswer(answer);
				}
				FormAnswer answer = answers.get(answers.size() - 1);
				answer.setFormAnswerVariant(null);
				formAnswerService.updateFormAnswer(answer);
			} else {
				for (; i < answersDto.size(); i++) {
					StudentAnswerDto answerDto = answersDto.get(i);
					FormAnswer formAnswer = createFormAnswer(interview, formQuestion);
					FormAnswerVariant variant = formAnswerVariantService
							.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
					formAnswer.setFormAnswerVariant(variant);
					formAnswerService.insertFormAnswerForInterview(formAnswer);
				}
			}
		} else {
			FormAnswer formAnswer = answers.get(0);
			if (FormQuestionTypeEnum.RADIO.getTitle().equals(questionType)
					|| FormQuestionTypeEnum.SELECT.getTitle().equals(questionType)) {

				StudentAnswerDto answerDto = answersDto.get(0);
				FormAnswerVariant variant = formAnswerVariantService
						.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
				formAnswer.setFormAnswerVariant(variant);
			} else {
				formAnswer.setAnswer(answersDto.get(0).getAnswer());
			}
			formAnswerService.updateFormAnswer(formAnswer);
		}
	}

	private FormAnswer createFormAnswer(Interview interview, FormQuestion question) {
		FormAnswer answer = new FormAnswerImpl();
		answer.setInterview(interview);
		answer.setFormQuestion(question);
		return answer;
	}
}
