package ua.kpi.nc.controller.student;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import ua.kpi.nc.persistence.dto.ApplicationFormDto;
import ua.kpi.nc.persistence.dto.MessageDto;
import ua.kpi.nc.persistence.dto.MessageDtoType;
import ua.kpi.nc.persistence.dto.StudentAnswerDto;
import ua.kpi.nc.persistence.dto.StudentAppFormQuestionDto;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.FormAnswer;
import ua.kpi.nc.persistence.model.FormAnswerVariant;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.Status;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.persistence.model.enums.FormQuestionTypeEnum;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerImpl;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.FormAnswerService;
import ua.kpi.nc.service.FormAnswerVariantService;
import ua.kpi.nc.service.FormQuestionService;
import ua.kpi.nc.service.RecruitmentService;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.StatusService;
import ua.kpi.nc.service.UserService;

/**
 * Created by dima on 14.04.16.
 */
@Controller
@RequestMapping("/student")
public class StudentApplicationFormController {
	private FormAnswerService formAnswerService;
	private ApplicationFormService applicationFormService;
	private UserService userService;
	private FormQuestionService formQuestionService;
	private FormAnswerVariantService formAnswerVariantService;
	private RoleService roleService;

	private StatusService statusService = ServiceFactory.getStatusService();
	private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();

	private Gson gson = new Gson();

	public StudentApplicationFormController() {
		formAnswerService = ServiceFactory.getFormAnswerService();
		applicationFormService = ServiceFactory.getApplicationFormService();
		userService = ServiceFactory.getUserService();
		formQuestionService = ServiceFactory.getFormQuestionService();
		formAnswerVariantService = ServiceFactory.getFormAnswerVariantService();
		roleService = ServiceFactory.getRoleService();
	}

	@RequestMapping(value = "appform", method = RequestMethod.POST)
	@ResponseBody
	public String getApplicationForm() {
		User student = userService.getAuthorizedUser();
		ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(student.getId());
		if (applicationForm == null) {

			applicationForm = createApplicationForm(student);

			List<FormAnswer> formAnswers = new ArrayList<FormAnswer>();
			ApplicationForm oldApplicationForm = applicationFormService.getLastApplicationFormByUserId(student.getId());
			List<FormQuestion> formQuestions = formQuestionService
					.getByRole(roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT)));
			for (FormQuestion formQuestion : formQuestions) {
				boolean wasInOldForm = false;
				if (oldApplicationForm != null) {
					List<FormAnswer> oldAnswers = formAnswerService.getByApplicationFormAndQuestion(oldApplicationForm,
							formQuestion);
					wasInOldForm = formAnswers.addAll(oldAnswers);
				}
				if (!wasInOldForm) {
					FormAnswer formAnswer = new FormAnswerImpl();
					formAnswer.setFormQuestion(formQuestion);
					formAnswer.setApplicationForm(applicationForm);
					formAnswers.add(formAnswer);
				}
			}
			applicationForm.setAnswers(formAnswers);
		}
		Gson applicationFormGson = GsonFactory.getApplicationFormGson();
		String jsonResult = applicationFormGson.toJson(applicationForm);
		return jsonResult;
	}

	// headers = {"Content-type=application/json"}
	@RequestMapping(value = "saveApplicationForm", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	@ResponseBody
	public String addUsername(@RequestBody ApplicationFormDto applicationFormDto) {
		User user = userService.getAuthorizedUser();
		user.setLastName(applicationFormDto.getUser().getLastName());
		user.setFirstName(applicationFormDto.getUser().getFirstName());
		user.setSecondName(applicationFormDto.getUser().getSecondName());
		userService.updateUser(user);
		ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(user.getId());
		if (applicationForm == null) {
			applicationForm = createApplicationForm(user);

			Set<FormQuestion> remainedQuestions = formQuestionService
					.getByRoleAsSet(roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT)));
			List<FormAnswer> answers = new ArrayList<FormAnswer>();
			for (StudentAppFormQuestionDto questionDto : applicationFormDto.getQuestions()) {
				FormQuestion formQuestion = formQuestionService.getById(questionDto.getId());
				if (formQuestion == null) {
					return gson.toJson(new MessageDto("Wrong input.", MessageDtoType.ERROR));
				}
				if (formQuestion.isMandatory() && !isFilled(questionDto)) {
					return gson.toJson(new MessageDto("You must fill in all mandatory fields.", MessageDtoType.ERROR));
				}
				String questionType = formQuestion.getQuestionType().getTypeTitle();
				if (formQuestion.isEnable()) {
					if (FormQuestionTypeEnum.CHECKBOX.getTitle().equals(questionType)) {
						for (StudentAnswerDto answerDto : questionDto.getAnswers()) {
							FormAnswerVariant variant = formAnswerVariantService
									.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
							if (variant != null) {
								FormAnswer formAnswer = createFormAnswer(applicationForm, formQuestion);
								formAnswer.setFormAnswerVariant(variant);
								answers.add(formAnswer);
							}
						}
					} else {
						if (FormQuestionTypeEnum.RADIO.getTitle().equals(questionType)
								|| FormQuestionTypeEnum.SELECT.getTitle().equals(questionType)) {
							FormAnswer formAnswer = createFormAnswer(applicationForm, formQuestion);
							StudentAnswerDto answerDto = questionDto.getAnswers().get(0);
							FormAnswerVariant variant = formAnswerVariantService
									.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
							formAnswer.setFormAnswerVariant(variant);
							answers.add(formAnswer);
						} else {
							FormAnswer formAnswer = createFormAnswer(applicationForm, formQuestion);
							formAnswer.setAnswer(questionDto.getAnswers().get(0).getAnswer());
							answers.add(formAnswer);
						}
					}
					if (!remainedQuestions.remove(formQuestion)) {
						return gson.toJson(new MessageDto("Wrong input.", MessageDtoType.ERROR));
					}
				}
			}
			if (!remainedQuestions.isEmpty()) {
				return gson.toJson(new MessageDto("Wrong input.", MessageDtoType.ERROR));
			}
			applicationForm.setAnswers(answers);
			applicationFormService.insertApplicationForm(applicationForm);
			System.out.println("PEREMOGA");
		} else {
			Set<FormQuestion> remainedQuestions = formQuestionService
					.getByRoleAsSet(roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT)));

			for (StudentAppFormQuestionDto questionDto : applicationFormDto.getQuestions()) {
				FormQuestion formQuestion = formQuestionService.getById(questionDto.getId());
				if (formQuestion == null) {
					return gson.toJson(new MessageDto("Wrong input.", MessageDtoType.ERROR));
				}
				if (formQuestion.isMandatory() && !isFilled(questionDto)) {
					return gson.toJson(new MessageDto("You must fill in all mandatory fields.", MessageDtoType.ERROR));
				}
				String questionType = formQuestion.getQuestionType().getTypeTitle();
				List<FormAnswer> answers = formAnswerService.getByApplicationFormAndQuestion(applicationForm,
						formQuestion);

				if (formQuestion.isEnable()) {
					if (FormQuestionTypeEnum.CHECKBOX.getTitle().equals(questionType)) {
						int i = 0;
						for (i = 0; i < questionDto.getAnswers().size() && i < answers.size(); i++) {
							StudentAnswerDto answerDto = questionDto.getAnswers().get(i);
							FormAnswer answer = answers.get(i);
							FormAnswerVariant variant = formAnswerVariantService
									.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
							answer.setFormAnswerVariant(variant);
							formAnswerService.updateFormAnswer(answer);
						}
						if (questionDto.getAnswers().size() < answers.size()) {
							for (; i < answers.size() - 1; i++) {
								FormAnswer answer = answers.get(i);
								formAnswerService.deleteFormAnswer(answer);
							}
							FormAnswer answer = answers.get(answers.size() - 1);
							answer.setFormAnswerVariant(null);
							formAnswerService.updateFormAnswer(answer);
						} else {
							for (; i < questionDto.getAnswers().size(); i++) {
								StudentAnswerDto answerDto = questionDto.getAnswers().get(i);
								FormAnswer formAnswer = createFormAnswer(applicationForm, formQuestion);
								FormAnswerVariant variant = formAnswerVariantService
										.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
								formAnswer.setFormAnswerVariant(variant);
								formAnswerService.insertFormAnswerForApplicationForm(formAnswer);
							}
						}
					} else {
						FormAnswer formAnswer = answers.get(0);
						if (FormQuestionTypeEnum.RADIO.getTitle().equals(questionType)
								|| FormQuestionTypeEnum.SELECT.getTitle().equals(questionType)) {

							StudentAnswerDto answerDto = questionDto.getAnswers().get(0);
							FormAnswerVariant variant = formAnswerVariantService
									.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
							formAnswer.setFormAnswerVariant(variant);
						} else {
							formAnswer.setAnswer(questionDto.getAnswers().get(0).getAnswer());
						}
						formAnswerService.updateFormAnswer(formAnswer);
					}
					if (!remainedQuestions.remove(formQuestion)) {
						return gson.toJson(new MessageDto("Wrong input.", MessageDtoType.ERROR));
					}
				}
			}
			if (!remainedQuestions.isEmpty()) {
				return gson.toJson(new MessageDto("Wrong input.", MessageDtoType.ERROR));
			}
			System.out.println("PEREMOGA2");
		}
		return null;
	}

	private FormAnswer createFormAnswer(ApplicationForm applicationForm, FormQuestion question) {
		FormAnswer answer = new FormAnswerImpl();
		answer.setApplicationForm(applicationForm);
		answer.setFormQuestion(question);
		return answer;
	}

	private ApplicationForm createApplicationForm(User user) {
		ApplicationForm applicationForm = new ApplicationFormImpl();
		applicationForm.setUser(user);
		Status status = statusService.getStatusById(StatusEnum.REGISTERED.getId());
		Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
		applicationForm.setStatus(status);
		applicationForm.setActive(true);
		applicationForm.setDateCreate(new Timestamp(System.currentTimeMillis()));
		applicationForm.setRecruitment(recruitment);
		return applicationForm;
	}

	private boolean isFilled(StudentAppFormQuestionDto questionDto) {
		List<StudentAnswerDto> answersDto = questionDto.getAnswers();
		if (answersDto.isEmpty())
			return false;
		if (answersDto.size() == 1 && answersDto.get(0).getAnswer() == null)
			return false;
		if ("".equals(answersDto.get(0).getAnswer()))
			return false;
		return true;
	}

}