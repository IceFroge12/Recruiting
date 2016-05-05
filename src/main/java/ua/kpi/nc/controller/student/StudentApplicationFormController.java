package ua.kpi.nc.controller.student;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.dto.ApplicationFormDto;
import ua.kpi.nc.persistence.dto.QuestionVariantDto;
import ua.kpi.nc.persistence.dto.StudentAnswerDto;
import ua.kpi.nc.persistence.dto.StudentAppFormQuestionDto;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerImpl;
import ua.kpi.nc.service.*;

import javax.mail.MessagingException;
import java.util.Objects;

/**
 * Created by dima on 14.04.16.
 */
@Controller
@RequestMapping("/student")
public class StudentApplicationFormController {
    private FormAnswerService formAnswerService = ServiceFactory.getFormAnswerService();
    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
    private UserService userService = ServiceFactory.getUserService();
    private FormQuestionService formQuestionService =ServiceFactory.getFormQuestionService();
    private FormAnswerVariantService formAnswerVariantService = ServiceFactory.getFormAnswerVariantService();

    @RequestMapping(value ="appform", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("studentappform");
        return modelAndView;
    }

//    @RequestMapping(value = "applicationForm", method = RequestMethod.GET)
//    public ModelAndView studentpage() {
//        ModelAndView modelAndView = new ModelAndView("studentAppl");
//        return modelAndView;
//    }

    @RequestMapping(value = "appform", method = RequestMethod.POST)
    @ResponseBody
    public String getApplicationForm() {
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(1L);
        Gson applicationFormGson = GsonFactory.getApplicationFormGson();
        String jsonResult = applicationFormGson.toJson(applicationForm);
        System.out.println(jsonResult);
        return jsonResult;
    }
    // headers = {"Content-type=application/json"}
    @RequestMapping(value = "saveApplicationForm", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public void addUsername(@RequestBody ApplicationFormDto applicationFormDto) throws MessagingException {
        System.out.println(applicationFormDto.getId());
        System.out.println(applicationFormDto.getStatus());
        for (StudentAppFormQuestionDto q : applicationFormDto.getQuestions()){
            System.out.println(q.toString());
        }
        System.out.println(applicationFormDto.getUser().toString());

        User user = userService.getUserByID(49L);
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
                        System.out.println(questionVariantDto.toString());
                        if(String.valueOf(questionVariantDto.getVariant()).equals(String.valueOf(answerDto.getAnswer()))){
                            FormAnswerVariant formAnswerVariant = formAnswerVariantService.getAnswerVariantById(questionVariantDto.getId());
                            //formAnswer.setAnswer(null);
                            formAnswer.setFormAnswerVariant(formAnswerVariant);
                            System.out.println(formAnswer.toString());
                        }
                    }
                    formAnswer.setApplicationForm(applicationForm);
                    formAnswer.setFormQuestion(formQuestion);
                    System.out.println(formAnswer.toString());
                    formAnswerService.insertFormAnswerForApplicationForm(formAnswer);
                }

             //   }

                else if (Objects.equals(questionDto.getQuestionType(), "input")){
                FormAnswer formAnswer = formAnswerService.getFormAnswerByID(answerDto.getID());
                formAnswer.setAnswer(answerDto.getAnswer());
                formAnswerService.updateFormAnswer(formAnswer);
                }

            }

        }
        System.out.println("EENDDDDDDDD");

    }
}