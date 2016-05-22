package ua.kpi.nc.controller.student;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ua.kpi.nc.persistence.dto.UserDto;
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

    private static Logger log = LoggerFactory.getLogger(StudentApplicationFormController.class);

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

    private static final String WRONG_INPUT_MESSAGE = gson.toJson(new MessageDto("Wrong input.", MessageDtoType.ERROR));

    private static final String MUST_UPLOAD_PHOTO_MESSAGE = gson
            .toJson(new MessageDto("You must upload photo.", MessageDtoType.ERROR));

    private static final String WAS_UPDATED_MESSAGE = gson
            .toJson(new MessageDto("Your application form was updated.", MessageDtoType.SUCCESS));

    private static final String WAS_CREATED_MESSAGE = gson
            .toJson(new MessageDto("Your application form was created.", MessageDtoType.SUCCESS));
    private static final String CANNOT_UPLOAD_MESSAGE = gson
            .toJson(new MessageDto("Cannot upload photo", MessageDtoType.ERROR));
    private static final String PHOTO_HAS_WRONG_FORMAT_MESSAGE = gson
            .toJson(new MessageDto("Photo has wrong format", MessageDtoType.ERROR));
    private static final String MUST_FILL_IN_MANDATORY_MESSAGE = gson
            .toJson(new MessageDto("You must fill in all mandatory fields.", MessageDtoType.ERROR));
    private static final String REGISTRATION_DEADLINE = gson.toJson(new MessageDto(
            "You cannot update your application form after registration deadline.", MessageDtoType.ERROR));

    private static final String[] AVAILABLE_PHOTO_EXTENSIONS = {"jpg", "jpeg", "png"};

    @RequestMapping(value = "appform", method = RequestMethod.POST)
    public String getApplicationForm() {
        User student = userService.getAuthorizedUser();
        ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(student.getId());
        boolean newForm = false;
        if (applicationForm == null) {
            applicationForm = applicationFormService.getLastApplicationFormByUserId(student.getId());
            if (applicationForm == null || !applicationForm.isActive()) {
                newForm = true;
                applicationForm = createApplicationFormFromOld(applicationForm, student);
            }
        }
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        if (recruitment == null || (recruitment != null && !isRegistrationDeadlineEnded(recruitment))) {
            applicationForm.setRecruitment(recruitment);
        }
        if (!newForm && recruitment == null) {
            addNewFormQuestions(applicationForm);
        }
        Gson applicationFormGson = GsonFactory.getApplicationFormGson();
        return applicationFormGson.toJson(applicationForm);
    }

    private ApplicationForm createApplicationFormFromOld(ApplicationForm oldApplicationForm, User user) {
        ApplicationForm applicationForm = createApplicationForm(user);

        List<FormAnswer> formAnswers = new ArrayList<>();

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
                FormAnswer formAnswer = createFormAnswer(oldApplicationForm, formQuestion);
                formAnswers.add(formAnswer);
            }
        }
        applicationForm.setAnswers(formAnswers);
        return applicationForm;
    }

    private void addNewFormQuestions(ApplicationForm applicationForm) {
        List<FormQuestion> unconnectedQuestion = formQuestionService.getEnableUnconnectedQuestion(applicationForm);
        for (FormQuestion formQuestion : unconnectedQuestion) {
            FormAnswer formAnswer = createFormAnswer(applicationForm, formQuestion);
            applicationForm.getAnswers().add(formAnswer);
        }
    }

    @RequestMapping(value = "saveApplicationForm", method = RequestMethod.POST)
    public String saveApplicationForm(@RequestParam("applicationForm") String jsonApplicationFormDto,
                                      @RequestParam("file") MultipartFile file) {
        ApplicationFormDto applicationFormDto = gson.fromJson(jsonApplicationFormDto, ApplicationFormDto.class);
        User user = userService.getAuthorizedUser();
        updateUser(user, applicationFormDto.getUser());
        Recruitment currentRecruitment = recruitmentService.getCurrentRecruitmnet();
        ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(user.getId());
        boolean newForm = false;
        if (applicationForm == null) {
            applicationForm = applicationFormService.getLastApplicationFormByUserId(user.getId());
            if (applicationForm == null) {
                newForm = true;
                if (file.isEmpty()) {
                    return MUST_UPLOAD_PHOTO_MESSAGE;
                }

            } else {
                Recruitment recruitment = applicationForm.getRecruitment();
                if (recruitment != null && currentRecruitment != null
                        && !currentRecruitment.getId().equals(recruitment.getId())) {
                    newForm = true;
                }
            }
        }
        Set<FormQuestion> remainedQuestions;
        List<FormAnswer> answers = null;

        if (newForm) {
            applicationForm = createApplicationForm(user);
            answers = new ArrayList<>();
            remainedQuestions = formQuestionService
                    .getByEnableRoleAsSet(roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT)));
        } else {
            Recruitment recruitment = applicationForm.getRecruitment();
            if (recruitment != null) {
                if (isRegistrationDeadlineEnded(recruitment)) {
                    return REGISTRATION_DEADLINE;
                }
                remainedQuestions = formQuestionService.getByApplicationFormAsSet(applicationForm);
            } else {
                remainedQuestions = formQuestionService
                        .getByEnableRoleAsSet(roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT)));
            }
        }
        String errorMessage = processAnswers(applicationForm, answers, applicationFormDto.getQuestions(),
                remainedQuestions, newForm);
        if (errorMessage != null) {
            return errorMessage;
        }
        if (newForm) {
            applicationForm.setAnswers(answers);
            applicationFormService.insertApplicationForm(applicationForm);
        } else {
            if (connectCurrentRecruitment(applicationForm)) {
                applicationFormService.updateApplicationForm(applicationForm);
            }
        }
        if (!file.isEmpty()) {
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (!hasPhotoValidFormat(fileExtension)) {
                return PHOTO_HAS_WRONG_FORMAT_MESSAGE;
            }
            if (!savePhoto(applicationForm, file, fileExtension)) {
                return CANNOT_UPLOAD_MESSAGE;
            }
        }
        if (newForm) {
            return WAS_CREATED_MESSAGE;
        } else {
            return WAS_UPDATED_MESSAGE;
        }
    }

    private void updateUser(User user, UserDto userDto) {
        user.setLastName(userDto.getLastName());
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        userService.updateUser(user);
    }

    private String processAnswers(ApplicationForm applicationForm, List<FormAnswer> answers,
                                  List<StudentAppFormQuestionDto> questionsDto, Set<FormQuestion> remainedQuestions,
                                  boolean newForm) {
        for (StudentAppFormQuestionDto questionDto : questionsDto) {
            FormQuestion formQuestion = formQuestionService.getById(questionDto.getId());
            String questionType = formQuestion.getQuestionType().getTypeTitle();
            if (!isFormQuestionValid(formQuestion)) {
                return WRONG_INPUT_MESSAGE;
            }
            if (formQuestion.isMandatory() && !isFilled(questionDto)) {
                return MUST_FILL_IN_MANDATORY_MESSAGE;
            }
            if (!remainedQuestions.remove(formQuestion)) {
                return WRONG_INPUT_MESSAGE;
            }
            if (!newForm && containsQuestionWithId(applicationForm.getQuestions(), formQuestion.getId())) {
                answers = formAnswerService.getByApplicationFormAndQuestion(applicationForm, formQuestion);
                updateAnswers(formQuestion, answers, questionDto.getAnswers(), applicationForm, questionType);
            } else {
                if (!newForm) {
                    FormAnswer answer = insertNewAnswers(formQuestion, questionDto.getAnswers(), applicationForm,
                            questionType).get(0);
                    formAnswerService.insertBlankFormAnswerForApplicationForm(answer);
                } else {
                    answers.addAll(
                            insertNewAnswers(formQuestion, questionDto.getAnswers(), applicationForm, questionType));
                }
            }
        }
        if (!remainedQuestions.isEmpty()) {
            return WRONG_INPUT_MESSAGE;
        }
        return null;
    }

    private boolean savePhoto(ApplicationForm applicationForm, MultipartFile file, String fileExtension) {
        try {
            String photoScope = applicationForm.getId() + "." + fileExtension;
            String photoDirPath = PropertiesReader.getInstance().propertiesReader("photodir.path");
            File photoFile = new File(photoDirPath, photoScope);
            file.transferTo(photoFile);
            applicationForm.setPhotoScope(photoScope);
            applicationFormService.updateApplicationForm(applicationForm);
            return true;
        } catch (IOException e) {
            log.error("Cannot save photo {}", e);
            return false;
        }
    }
    //TODO duplicate
    private void updateAnswers(FormQuestion formQuestion, List<FormAnswer> answers, List<StudentAnswerDto> answersDto,
                               ApplicationForm applicationForm, String questionType) {
        if (FormQuestionTypeEnum.CHECKBOX.getTitle().equals(questionType)) {
            int i;
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
    //TODO duplicate
    private List<? extends FormAnswer> insertNewAnswers(FormQuestion formQuestion, List<StudentAnswerDto> answersDto,
                                                        ApplicationForm applicationForm, String questionType) {
        if (FormQuestionTypeEnum.CHECKBOX.getTitle().equals(questionType)) {
            List<FormAnswer> answers = new ArrayList<>();
            for (StudentAnswerDto answerDto : answersDto) {
                FormAnswerVariant variant = formAnswerVariantService
                        .getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
                if (variant != null) {
                    FormAnswer formAnswer = createFormAnswer(applicationForm, formQuestion);
                    formAnswer.setFormAnswerVariant(variant);
                    answers.add(formAnswer);
                }
            }
            if (answers.isEmpty()) {
                answers.add(createFormAnswer(applicationForm, formQuestion));
            }
            return answers;
        } else {
            FormAnswer formAnswer = createFormAnswer(applicationForm, formQuestion);
            if (FormQuestionTypeEnum.RADIO.getTitle().equals(questionType)
                    || FormQuestionTypeEnum.SELECT.getTitle().equals(questionType)) {
                StudentAnswerDto answerDto = answersDto.get(0);
                FormAnswerVariant variant = formAnswerVariantService
                        .getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
                formAnswer.setFormAnswerVariant(variant);
            } else {
                formAnswer.setAnswer(answersDto.get(0).getAnswer());
            }
            return Collections.singletonList(formAnswer);
        }
    }

    @RequestMapping(value = "appform/{applicationFormId}", method = RequestMethod.GET)
    public void exportAppform(@PathVariable Long applicationFormId, HttpServletResponse response) throws Exception {
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=ApplicationForm.pdf");
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
        if (recruitment == null || (recruitment != null && !isRegistrationDeadlineEnded(recruitment))) {
            applicationForm.setRecruitment(recruitment);
        }
        return applicationForm;
    }

    private boolean isFormQuestionValid(FormQuestion formQuestion) {
        return !(formQuestion == null || !formQuestion.isEnable());
    }

    private boolean isFilled(StudentAppFormQuestionDto questionDto) {
        List<StudentAnswerDto> answersDto = questionDto.getAnswers();
        if (answersDto == null) {
            return false;
        }
        for (StudentAnswerDto answer : answersDto) {
            if (answer != null && answer.getAnswer() != null && !answer.getAnswer().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPhotoValidFormat(String fileExtension) {
        for (String photoExtension : AVAILABLE_PHOTO_EXTENSIONS) {
            if (photoExtension.equals(fileExtension)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsQuestionWithId(List<FormQuestion> questions, Long id) {
        for (FormQuestion formQuestion : questions) {
            if (formQuestion.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRegistrationDeadlineEnded(Recruitment recruitment) {
        Timestamp deadline = recruitment.getRegistrationDeadline();
        return deadline != null && deadline.before(new Date());
    }

    private boolean connectCurrentRecruitment(ApplicationForm applicationForm) {
        if (applicationForm.getRecruitment() == null) {
            Recruitment currentRecruitment = recruitmentService.getCurrentRecruitmnet();
            if (currentRecruitment != null && !isRegistrationDeadlineEnded(currentRecruitment)) {
                applicationForm.setRecruitment(currentRecruitment);
                applicationFormService.updateApplicationForm(applicationForm);
                return true;
            }
        }
        return false;
    }
}