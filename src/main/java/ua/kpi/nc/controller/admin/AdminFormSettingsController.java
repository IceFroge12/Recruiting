package ua.kpi.nc.controller.admin;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.model.Decision;
import ua.kpi.nc.persistence.model.FormQuestion;
import ua.kpi.nc.persistence.model.QuestionType;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.service.DecisionService;
import ua.kpi.nc.service.FormQuestionService;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.ServiceFactory;

import java.util.List;

/**
 * Created by dima on 23.04.16.
 */
@RequestMapping("/admin")
public class AdminFormSettingsController {


    private DecisionService decisionService = ServiceFactory.getDecisionService();

    private FormQuestionService formQuestionService = ServiceFactory.getFormQuestionService();

    private RoleService roleService = ServiceFactory.getRoleService();

    @RequestMapping(value = "adminformsettings", method = RequestMethod.GET)
    public ModelAndView studentManagement() {
        ModelAndView modelAndView = new ModelAndView("adminformsettings");
        return modelAndView;
    }

    @RequestMapping(value = "addmatrix")
    public void addDecisionMatrix(@RequestParam List<Decision> decisionList) {

        decisionService.truncateDecisionTable();

        for (Decision decision : decisionList) {
            decisionService.insertDecision(decision);
        }

    }

    @RequestMapping(value = "getapplicationquestions")
    @ResponseBody
    public List<FormQuestion> getAppFormQuestions() {

        Role roleAdmin = roleService.getRoleByTitle(String.valueOf(RoleEnum.ADMIN));

        List<FormQuestion> formQuestionList = formQuestionService.getByRole(roleAdmin);

        for (FormQuestion formQuestion : formQuestionList) {
            System.out.println(formQuestion);
        }
        return formQuestionList;
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
