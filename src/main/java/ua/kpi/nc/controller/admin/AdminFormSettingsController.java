package ua.kpi.nc.controller.admin;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.FormQuestionDto;
import ua.kpi.nc.persistence.dto.MessageDto;
import ua.kpi.nc.persistence.dto.MessageDtoType;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.persistence.model.impl.real.FormAnswerVariantImpl;
import ua.kpi.nc.persistence.model.impl.real.FormQuestionImpl;
import ua.kpi.nc.persistence.util.FormQuestionComparator;
import ua.kpi.nc.service.*;

import java.util.ArrayList;
import java.util.Collections;
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
        Role roleTitle = roleService.getRoleByTitle(role);

        System.out.println(roleTitle);
        List<FormQuestion> formQuestionList = formQuestionService.getByRole(roleTitle);

        Collections.sort(formQuestionList, new FormQuestionComparator());

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
                formQuestionDto.isEnable(), formQuestionDto.isMandatory(), roleList, formAnswerVariantList, formQuestionDto.getOrder());
        formQuestionService.insertFormQuestion(formQuestion, role, formAnswerVariantList);
    }

    @RequestMapping(value = "updateAppFormQuestion", method = RequestMethod.POST)
    public void updateAppFormQuestions(@RequestBody FormQuestionDto formQuestionDto) {
        System.out.println("ALE" + formQuestionDto.toString());
        QuestionType questionType = questionTypeService.getQuestionTypeByName(formQuestionDto.getType());

        List<FormAnswerVariant> formAnswerVariantList = new ArrayList<>();
        for (String s : formQuestionDto.getFormAnswerVariants()) {
            System.out.println("LOL" + s);
            FormAnswerVariant formAnswerVariant = new FormAnswerVariantImpl();
            formAnswerVariant.setAnswer(s);
            formAnswerVariantList.add(formAnswerVariant);
        }

        FormQuestion formQuestion = new FormQuestionImpl(formQuestionDto.getId(), formQuestionDto.getQuestion(), questionType,
                formAnswerVariantList, formQuestionDto.getOrder());
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

    @RequestMapping(value = "changeQuestionMandatoryStatus", method = RequestMethod.GET)
    public boolean changeQuestionMandatoryStatus(@RequestParam Long id) {
        FormQuestion formQuestion = formQuestionService.getById(id);
        formQuestion.setMandatory(!formQuestion.isMandatory());
        formQuestionService.updateFormQuestion(formQuestion);
        return formQuestion.isMandatory();
    }

	@RequestMapping(value = "getDecisionMatrix", method = RequestMethod.GET)
	public List<Decision> getDecisionMatrix() {
		return decisionService.getAll();
	}

	@RequestMapping(value = "saveDecisionMatrix", method = RequestMethod.POST)
	public String saveDecisionMatrix(@RequestBody List<Decision> decisionMatrix) {
		decisionService.updateDecisionMatrix(decisionMatrix);
		return new Gson().toJson(new MessageDto("Decision matrix was updated", MessageDtoType.SUCCESS));
	}
    
}
