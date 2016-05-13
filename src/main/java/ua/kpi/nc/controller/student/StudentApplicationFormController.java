package ua.kpi.nc.controller.student;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import ua.kpi.nc.config.PropertiesReader;
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
import ua.kpi.nc.util.export.ExportApplicationForm;
import ua.kpi.nc.util.export.ExportApplicationFormImp;

/**
 * Created by dima on 14.04.16.
 */
@RestController
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

	public StudentApplicationFormController() {
		formAnswerService = ServiceFactory.getFormAnswerService();
		applicationFormService = ServiceFactory.getApplicationFormService();
		userService = ServiceFactory.getUserService();
		formQuestionService = ServiceFactory.getFormQuestionService();
		formAnswerVariantService = ServiceFactory.getFormAnswerVariantService();
		roleService = ServiceFactory.getRoleService();
	}

	private static Gson gson = new Gson();

	private static final String JSON_WRONG_INPUT_MESSAGE = gson
			.toJson(new MessageDto("You must fill in all mandatory fields.", MessageDtoType.ERROR));

	private static final String[] AVAILABLE_PHOTO_EXTENSIONS = { "jpg", "jpeg", "png" };

	@RequestMapping(value = "appform", method = RequestMethod.POST)
	public String getApplicationForm() {
		User student = userService.getAuthorizedUser();
		ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(student.getId());
		if (applicationForm == null) {

			applicationForm = createApplicationForm(student);

			List<FormAnswer> formAnswers = new ArrayList<FormAnswer>();
			ApplicationForm oldApplicationForm = applicationFormService.getLastApplicationFormByUserId(student.getId());
			List<FormQuestion> formQuestions = formQuestionService
					.getEnableByRole(roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT)));
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

	@RequestMapping(value = "saveApplicationForm", method = RequestMethod.POST)
	public String saveApplicationForm(@RequestParam("applicationForm") String jsonApplicationFormDto,
			@RequestParam("file") MultipartFile file) {
		ApplicationFormDto applicationFormDto = gson.fromJson(jsonApplicationFormDto, ApplicationFormDto.class);
		User user = userService.getAuthorizedUser();
		user.setLastName(applicationFormDto.getUser().getLastName());
		user.setFirstName(applicationFormDto.getUser().getFirstName());
		user.setSecondName(applicationFormDto.getUser().getSecondName());
		userService.updateUser(user);
		ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(user.getId());
		Set<FormQuestion> remainedQuestions;

		List<FormAnswer> answers = null;
		boolean newForm = false;
		if (applicationForm == null) {
			if (file.isEmpty()) {
				return gson.toJson(new MessageDto("You must upload photo.", MessageDtoType.ERROR));
			}
			applicationForm = createApplicationForm(user);
			newForm = true;
			answers = new ArrayList<FormAnswer>();
			remainedQuestions = formQuestionService
					.getByEnableRoleAsSet(roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT)));
		} else {
			if (applicationForm.getRecruitment().getRegistrationDeadline().before(new Date())) {
				return gson.toJson(new MessageDto(
						"You cannot update your application form after registration deadline.", MessageDtoType.ERROR));
			}
			remainedQuestions = formQuestionService.getByApplicationFormAsSet(applicationForm);
		}
		for (StudentAppFormQuestionDto questionDto : applicationFormDto.getQuestions()) {
			FormQuestion formQuestion = formQuestionService.getById(questionDto.getId());
			if (!isFormQuestionValid(formQuestion)) {
				return JSON_WRONG_INPUT_MESSAGE;
			}
			if (formQuestion.isMandatory() && !isFilled(questionDto)) {
				return gson.toJson(new MessageDto("You must fill in all mandatory fields.", MessageDtoType.ERROR));
			}
			if (!remainedQuestions.remove(formQuestion)) {
				return JSON_WRONG_INPUT_MESSAGE;
			}
			if (!newForm) {
				answers = formAnswerService.getByApplicationFormAndQuestion(applicationForm, formQuestion);
				updateAnswers(formQuestion, answers, questionDto.getAnswers(), applicationForm);
			} else {
				answers.addAll(insertNewAnswers(formQuestion, questionDto.getAnswers(), applicationForm));
			}
		}
		if (!remainedQuestions.isEmpty()) {
			return JSON_WRONG_INPUT_MESSAGE;
		}
		if (newForm) {
			applicationForm.setAnswers(answers);
			applicationFormService.insertApplicationForm(applicationForm);
		}
		if (!file.isEmpty()) {
			try {
				String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
				if (!hasPhotoValidFormat(fileExtension)) {
					return gson.toJson(new MessageDto("Photo has wrong format", MessageDtoType.ERROR));
				}
				String photoScope = applicationForm.getId() + "." + fileExtension;
				String photoDirPath = PropertiesReader.getInstance().propertiesReader("photodir.path");
				File photoFile = new File(photoDirPath, photoScope);
				file.transferTo(photoFile);
				applicationForm.setPhotoScope(photoScope);
				applicationFormService.updateApplicationForm(applicationForm);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				return gson.toJson(new MessageDto("Cannot upload photo", MessageDtoType.ERROR));
			}
		}
		System.out.println("PEREMOGA");
		if (newForm) {
			return gson.toJson(new MessageDto("Your application form was created.", MessageDtoType.SUCCESS));
		} else {
			return gson.toJson(new MessageDto("Your application form was updated.", MessageDtoType.SUCCESS));
		}
	}

	private void updateAnswers(FormQuestion formQuestion, List<FormAnswer> answers, List<StudentAnswerDto> answersDto,
			ApplicationForm applicationForm) {
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

	private Collection<? extends FormAnswer> insertNewAnswers(FormQuestion formQuestion,
			List<StudentAnswerDto> answersDto, ApplicationForm applicationForm) {
		String questionType = formQuestion.getQuestionType().getTypeTitle();
		if (FormQuestionTypeEnum.CHECKBOX.getTitle().equals(questionType)) {
			List<FormAnswer> answers = new ArrayList<FormAnswer>();
			for (StudentAnswerDto answerDto : answersDto) {
				FormAnswerVariant variant = formAnswerVariantService
						.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
				if (variant != null) {
					FormAnswer formAnswer = createFormAnswer(applicationForm, formQuestion);
					formAnswer.setFormAnswerVariant(variant);
					answers.add(formAnswer);
				}
			}
			return answers;
		} else {
			FormAnswer formAnswer = createFormAnswer(applicationForm, formQuestion);
			;
			if (FormQuestionTypeEnum.RADIO.getTitle().equals(questionType)
					|| FormQuestionTypeEnum.SELECT.getTitle().equals(questionType)) {
				StudentAnswerDto answerDto = answersDto.get(0);
				FormAnswerVariant variant = formAnswerVariantService
						.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
				formAnswer.setFormAnswerVariant(variant);
			} else {
				formAnswer.setAnswer(answersDto.get(0).getAnswer());
			}
			return Arrays.asList(formAnswer);
		}
	}

	@RequestMapping(value = "appform{applicationFormId}", method = RequestMethod.GET)
	public void exportAppform(@PathVariable Long applicationFormId, HttpServletResponse response) throws Exception {
		ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", String.format("inline; filename=ApplicationForm.pdf"));
		ExportApplicationForm pdfAppForm = new ExportApplicationFormImp();
		pdfAppForm.export(applicationForm, response);

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
		if (recruitment != null && !recruitment.getRegistrationDeadline().before(new Date())) {
			applicationForm.setRecruitment(recruitment);
		}
		return applicationForm;
	}

	private boolean isFormQuestionValid(FormQuestion formQuestion) {
		if (formQuestion == null || !formQuestion.isEnable()) {
			return false;
		}
		return true;
	}

	private boolean isFilled(StudentAppFormQuestionDto questionDto) {
		List<StudentAnswerDto> answersDto = questionDto.getAnswers();
		if (answersDto == null) {
			return false;
		}
		for (int i = 0; i < answersDto.size(); i++) {
			StudentAnswerDto answer = answersDto.get(i);
			if (answer != null && answer.getAnswer() != null && !answer.getAnswer().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	private boolean hasPhotoValidFormat(String fileExtension) {
		for (int i = 0; i < AVAILABLE_PHOTO_EXTENSIONS.length; i++) {
			if (AVAILABLE_PHOTO_EXTENSIONS[i].equals(fileExtension)) {
				return true;
			}
		}
		return false;
	}

}