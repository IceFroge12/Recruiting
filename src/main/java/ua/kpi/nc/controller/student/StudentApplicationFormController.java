package ua.kpi.nc.controller.student;

import com.google.gson.Gson;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.controller.auth.UserAuthentication;
import ua.kpi.nc.persistence.dto.ApplicationFormDto;
import ua.kpi.nc.persistence.dto.MessageDto;
import ua.kpi.nc.persistence.dto.MessageDtoType;
import ua.kpi.nc.persistence.dto.QuestionVariantDto;
import ua.kpi.nc.persistence.dto.StudentAnswerDto;
import ua.kpi.nc.persistence.dto.StudentAppFormQuestionDto;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.persistence.model.enums.FormQuestionTypeEnum;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.enums.StatusEnum;
import ua.kpi.nc.persistence.model.impl.real.ApplicationFormImpl;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerImpl;
import ua.kpi.nc.service.*;

import javax.mail.MessagingException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
	// private User currentUser;

	private StatusService statusService = ServiceFactory.getStatusService();
	private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();

	private Gson gson = new Gson();

	public StudentApplicationFormController() {
		formAnswerService = ServiceFactory.getFormAnswerService();
		applicationFormService = ServiceFactory.getApplicationFormService();
		userService = ServiceFactory.getUserService();
		formQuestionService = ServiceFactory.getFormQuestionService();
		formAnswerVariantService = ServiceFactory.getFormAnswerVariantService();
		// currentUser = ((UserAuthentication)
		// SecurityContextHolder.getContext().getAuthentication()).getDetails();
		roleService = ServiceFactory.getRoleService();
	}

	@RequestMapping(value = "appform", method = RequestMethod.POST)
	@ResponseBody
	public String getApplicationForm() {
		User student = userService.getAuthorizedUser();
		ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(student.getId());
		if (applicationForm == null) {

			applicationForm = new ApplicationFormImpl();

			applicationForm.setUser(student);
			Status status = statusService.getStatusById(StatusEnum.REGISTERED.getId());
			Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
			applicationForm.setStatus(status);
			applicationForm.setActive(true);
			applicationForm.setDateCreate(new Timestamp(System.currentTimeMillis()));
			applicationForm.setRecruitment(recruitment);

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
		System.out.println(jsonResult);
		return jsonResult;
	}

	// headers = {"Content-type=application/json"}
	@RequestMapping(value = "saveApplicationForm", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	@ResponseBody
	public String addUsername(@RequestBody ApplicationFormDto applicationFormDto) throws MessagingException {
		System.out.println(applicationFormDto);
		User user = userService.getAuthorizedUser();

		// for (StudentAppFormQuestionDto q :
		// applicationFormDto.getQuestions()){
		// System.out.println(q.toString());
		// }
		// System.out.println(applicationFormDto.getUser().toString());

		user.setLastName(applicationFormDto.getUser().getLastName());
		user.setFirstName(applicationFormDto.getUser().getFirstName());
		user.setSecondName(applicationFormDto.getUser().getSecondName());
		userService.updateUser(user);
		ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(user.getId());
		if (applicationForm == null) {
			applicationForm = new ApplicationFormImpl();
			applicationForm.setUser(user);
			applicationForm.setActive(true);
			applicationForm.setDateCreate(new Timestamp(System.currentTimeMillis()));
			Status status = statusService.getStatusById(StatusEnum.REGISTERED.getId());
			Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
			applicationForm.setStatus(status);
			applicationForm.setRecruitment(recruitment);

			Set<FormQuestion> remainedQuestions = formQuestionService
					.getByRoleAsSet(roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT)));
			List<FormAnswer> answers = new ArrayList<FormAnswer>();
			for (StudentAppFormQuestionDto questionDto : applicationFormDto.getQuestions()) {
				FormQuestion formQuestion = formQuestionService.getById(questionDto.getId());
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
						if (questionDto.getAnswers().size() > answers.size()) {
							for (; i < questionDto.getAnswers().size(); i++) {
								StudentAnswerDto answerDto = questionDto.getAnswers().get(i);
								FormAnswer formAnswer = createFormAnswer(applicationForm, formQuestion);
								FormAnswerVariant variant = formAnswerVariantService
										.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
								formAnswer.setFormAnswerVariant(variant);
								formAnswerService.insertFormAnswerForApplicationForm(formAnswer);
							}
						} else {
							for (; i < answers.size(); i++) {
								FormAnswer answer = answers.get(i);
								formAnswerService.deleteFormAnswer(answer);
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

}