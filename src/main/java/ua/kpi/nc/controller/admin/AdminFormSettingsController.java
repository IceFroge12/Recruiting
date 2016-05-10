package ua.kpi.nc.controller.admin;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.FormQuestionDto;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.persistence.model.enums.FormQuestionTypeEnum;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerVariantImpl;
import ua.kpi.nc.persistence.model.impl.real.FormQuestionImpl;
import ua.kpi.nc.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 23.04.16.
 */
@RestController
@RequestMapping("/admin")
public class AdminFormSettingsController {

    private DecisionService decisionService = ServiceFactory.getDecisionService();

    private FormQuestionService formQuestionService = ServiceFactory.getFormQuestionService();

    private RoleService roleService = ServiceFactory.getRoleService();

    private QuestionTypeService questionTypeService = ServiceFactory.getQuestionTypeService();


    @RequestMapping(value = "addmatrix")
    public void addDecisionMatrix(@RequestParam List<Decision> decisionList) {
        decisionService.truncateDecisionTable();
        for (Decision decision : decisionList) {
            decisionService.insertDecision(decision);
        }
    }

    @RequestMapping(value = "getapplicationquestions", method = RequestMethod.POST)
    public List<String> getAppFormQuestions(@RequestParam String role) {
        Role roleAdmin = roleService.getRoleByTitle(role);

        System.out.println(roleAdmin);
        List<FormQuestion> formQuestionList = formQuestionService.getByRole(roleAdmin);

        List<String> adapterFormQuestionList = new ArrayList<>();

        for (FormQuestion formQuestion : formQuestionList) {
            Gson questionGson = GsonFactory.getFormQuestionGson();
            String jsonResult = questionGson.toJson(formQuestion);
            adapterFormQuestionList.add(jsonResult);
        }
        return adapterFormQuestionList;
    }

    @RequestMapping(value = "addAppFormQuestion")
    public void addAppFormQuestion(@RequestBody FormQuestionDto formQuestionDto) {
        Role role = roleService.getRoleByTitle(formQuestionDto.getRole());
        QuestionType questionType = questionTypeService.getQuestionTypeByName(formQuestionDto.getType());

        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        List<FormAnswerVariant> formAnswerVariantList = new ArrayList<>();
        for (String s : formQuestionDto.getFormAnswerVariants()) {
            FormAnswerVariant formAnswerVariant = new FormAnswerVariantImpl(s);
            formAnswerVariantList.add(formAnswerVariant);
        }

        FormQuestion formQuestion = new FormQuestionImpl(formQuestionDto.getQuestion(), questionType,
                formQuestionDto.isEnable(), true, roleList, formAnswerVariantList);
        formQuestionService.insertFormQuestion(formQuestion, role, formAnswerVariantList);
    }

    @RequestMapping(value = "updateAppFormQuestion", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public void updateAppFormQuestions(@RequestBody FormQuestionDto formQuestionDto) {

        QuestionType questionType = questionTypeService.getQuestionTypeByName(formQuestionDto.getType());
        List<FormAnswerVariant> formAnswerVariantList = new ArrayList<>();
        for (String s : formQuestionDto.getFormAnswerVariants()) {
            FormAnswerVariant formAnswerVariant = new FormAnswerVariantImpl(s);
            formAnswerVariantList.add(formAnswerVariant);
        }

        FormQuestion formQuestion = new FormQuestionImpl(formQuestionDto.getId(),formQuestionDto.getQuestion(), questionType ,formAnswerVariantList);
        formQuestionService.updateFormQuestion(formQuestion);
    }

    @RequestMapping(value = "getAllQuestionType")
    public List<QuestionType> getAllQuestionType() {
        return questionTypeService.getAllQuestionType();
    }

    @RequestMapping(value = "changeQuestionStatus", method = RequestMethod.GET)
    public boolean changeQuestionStatus(@RequestParam Long id) {
        FormQuestion formQuestion = formQuestionService.getById(id);
        formQuestion.setEnable(!formQuestion.isEnable());
        formQuestionService.updateFormQuestion(formQuestion);
        return formQuestion.isEnable();
    }

}
