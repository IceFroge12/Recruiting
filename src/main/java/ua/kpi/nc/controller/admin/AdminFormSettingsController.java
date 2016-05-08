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
        System.out.println(role);
        Role roleAdmin = roleService.getRoleByTitle(role);

        System.out.println(roleAdmin);
        List<FormQuestion> formQuestionList = formQuestionService.getByRole(roleAdmin);

        List<String> adapterFormQuestionList = new ArrayList<>();

        for (FormQuestion formQuestion : formQuestionList) {
            System.out.println(formQuestion);
            Gson questionGson = GsonFactory.getFormQuestionGson();
            String jsonResult = questionGson.toJson(formQuestion);
            adapterFormQuestionList.add(jsonResult);
        }
        return adapterFormQuestionList;
    }

    @RequestMapping(value = "addAppFormQuestion")
    public void addAppFormQuestion(@RequestBody FormQuestionDto formQuestionDto) {
        System.out.println(formQuestionDto);
        Role role = roleService.getRoleByTitle(formQuestionDto.getRole());
        System.out.println(role.getRoleName());
        QuestionType questionType = questionTypeService.getQuestionTypeByName(formQuestionDto.getType());
        System.out.println(questionType);

        List<Role> roleList = new ArrayList<>();//TODO change list
        roleList.add(role);
        List<FormAnswerVariant> formAnswerVariantList = new ArrayList<>();
        for (String s : formQuestionDto.getFormAnswerVariants()) {
            System.out.println(s);
            FormAnswerVariant formAnswerVariant = new FormAnswerVariantImpl(s);
            formAnswerVariantList.add(formAnswerVariant);
        }

        FormQuestion formQuestion = new FormQuestionImpl(formQuestionDto.getQuestion(), questionType,
                formQuestionDto.isEnable(), true, roleList, formAnswerVariantList);
        formQuestionService.insertFormQuestion(formQuestion,role,formAnswerVariantList);
    }

    @RequestMapping(value = "updateappformquestion")
    public void updateAppFormQuestions(@RequestBody FormQuestion formQuestion) {
        formQuestionService.updateFormQuestion(formQuestion);
    }

    @RequestMapping(value = "getAllQuestionType")
    public List<QuestionType> getAllQuestionType() {
        return questionTypeService.getAllQuestionType();
    }

}
