package ua.kpi.nc.controller.admin;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.model.Decision;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.adapter.GsonFactory;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.service.DecisionService;
import ua.kpi.nc.service.FormQuestionService;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 23.04.16.
 */
@Controller
@RequestMapping("/admin")
public class AdminFormSettingsController {


    private DecisionService decisionService = ServiceFactory.getDecisionService();

    private FormQuestionService formQuestionService = ServiceFactory.getFormQuestionService();

    private RoleService roleService = ServiceFactory.getRoleService();


    @RequestMapping(value = "addmatrix")
    public void addDecisionMatrix(@RequestParam List<Decision> decisionList) {

        decisionService.truncateDecisionTable();

        for (Decision decision : decisionList) {
            decisionService.insertDecision(decision);
        }
    }

    @RequestMapping(value = "getapplicationquestions",method = RequestMethod.POST)
    @ResponseBody
    public List<String> getAppFormQuestions() {

        Role roleAdmin = roleService.getRoleByTitle(String.valueOf(RoleEnum.ADMIN));

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

    @RequestMapping(value = "addappformquestion")
    public void addAppFormQuestion(@RequestBody FormQuestion formQuestion) {

        Role roleAdmin = roleService.getRoleByTitle(String.valueOf(RoleEnum.ADMIN));

        formQuestionService.addRole(formQuestion, roleAdmin);

    }

    @RequestMapping(value = "updateappformquestion")
    public void updateAppFormQuestions(@RequestBody FormQuestion formQuestion) {
        formQuestionService.updateFormQuestion(formQuestion);
    }


}
