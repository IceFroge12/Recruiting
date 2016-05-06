package ua.kpi.nc.controller.student;

import com.google.gson.Gson;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.controller.auth.UserAuthentication;
import ua.kpi.nc.persistence.dto.ApplicationFormDto;
import ua.kpi.nc.persistence.dto.QuestionVariantDto;
import ua.kpi.nc.persistence.dto.StudentAnswerDto;
import ua.kpi.nc.persistence.dto.StudentAppFormQuestionDto;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
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
    //private User currentUser;

    private StatusService statusService = ServiceFactory.getStatusService();
    private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
    
    public StudentApplicationFormController() {
        formAnswerService = ServiceFactory.getFormAnswerService();
        applicationFormService = ServiceFactory.getApplicationFormService();
        userService = ServiceFactory.getUserService();
        formQuestionService =ServiceFactory.getFormQuestionService();
        formAnswerVariantService = ServiceFactory.getFormAnswerVariantService();
        //currentUser = ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getDetails();
        roleService = ServiceFactory.getRoleService();
    }

    @RequestMapping(value ="appform", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("studentappform");
        return modelAndView;
    }


    @RequestMapping(value = "appform", method = RequestMethod.POST)
    @ResponseBody
    public String getApplicationForm() {
    	List<ApplicationForm> applicationForms = applicationFormService.getByUserId(155L);
        if(applicationForms.isEmpty()){
            User user = userService.getUserByID(155L);
            ApplicationForm applicationForm = new ApplicationFormImpl();
            List<FormQuestion>  formQuestions = formQuestionService.getByRole(roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.STUDENT)));
            applicationForm.setUser(user);
            Status status = statusService.getStatusById(StatusEnum.REGISTERED.getId());
            Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
            applicationForm.setStatus(status);
            applicationForm.setActive(true);
            applicationForm.setDateCreate(new Timestamp(System.currentTimeMillis()));
            applicationForm.setRecruitment(recruitment);
            
            List<FormAnswer> formAnswers = new ArrayList<FormAnswer>();
            System.out.println(applicationForm);
            for(FormQuestion formQuestion :formQuestions){
                FormAnswer formAnswer = new FormAnswerImpl();
                formAnswer.setFormQuestion(formQuestion);
                formAnswer.setApplicationForm(applicationForm);
                formAnswers.add(formAnswer);
            }
            applicationForm.setAnswers(formAnswers);
            applicationFormService.insertApplicationForm(applicationForm);
        }

        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(155L);
        Gson applicationFormGson = GsonFactory.getApplicationFormGson();
        String jsonResult = applicationFormGson.toJson(applicationForm);
        System.out.println(jsonResult);
        return jsonResult;
    }
    // headers = {"Content-type=application/json"}
    @RequestMapping(value = "saveApplicationForm", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public void addUsername(@RequestBody ApplicationFormDto applicationFormDto) throws MessagingException {
        User currentUser = ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getDetails();
        System.out.println(applicationFormDto.getId());
        System.out.println(applicationFormDto.getStatus());

//        for (StudentAppFormQuestionDto q : applicationFormDto.getQuestions()){
//            System.out.println(q.toString());
//        }
//        System.out.println(applicationFormDto.getUser().toString());

        User user = userService.getUserByID(155L);
        user.setLastName(applicationFormDto.getUser().getLastName());
        user.setFirstName(applicationFormDto.getUser().getFirstName());
        user.setSecondName(applicationFormDto.getUser().getSecondName());
        userService.updateUser(user);
        for (StudentAppFormQuestionDto questionDto : applicationFormDto.getQuestions()){
            FormQuestion formQuestion = formQuestionService.getById(questionDto.getId());
            ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormDto.getId());

            for (StudentAnswerDto answerDto : questionDto.getAnswers()) {


                if(answerDto.getID()==0L){
                    System.out.println(answerDto.toString());
                    FormAnswer formAnswer = new FormAnswerImpl();
                    for(QuestionVariantDto questionVariantDto : questionDto.getVariants()){
                        if(String.valueOf(questionVariantDto.getVariant()).equals(String.valueOf(answerDto.getAnswer()))){
                            FormAnswerVariant formAnswerVariant = formAnswerVariantService.getAnswerVariantById(questionVariantDto.getId());
                            formAnswer.setFormAnswerVariant(formAnswerVariant);
                        }
                    }
                    formAnswer.setApplicationForm(applicationForm);
                    formAnswer.setFormQuestion(formQuestion);
                    formAnswerService.insertFormAnswerForApplicationForm(formAnswer);
                }
                else if (Objects.equals(questionDto.getQuestionType(), "input")){
                FormAnswer formAnswer = formAnswerService.getFormAnswerByID(answerDto.getID());
                formAnswer.setAnswer(answerDto.getAnswer());
                formAnswerService.updateFormAnswer(formAnswer);
                }
            }
        }

    }
}