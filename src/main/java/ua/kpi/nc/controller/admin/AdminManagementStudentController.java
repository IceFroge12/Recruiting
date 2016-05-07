package ua.kpi.nc.controller.admin;

import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.service.*;

import java.util.List;
import java.util.Set;

/**
 * Created by dima on 23.04.16.
 */
@RestController
@RequestMapping("/admin")
public class AdminManagementStudentController {

    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
    private RoleService roleService = ServiceFactory.getRoleService();
    private UserService userService=ServiceFactory.getUserService();
    private FormAnswerService formAnswerService=ServiceFactory.getFormAnswerService();
    private FormQuestionService formQuestionService=ServiceFactory.getFormQuestionService();


    @RequestMapping(value = "getallstudent", method = RequestMethod.GET)
    public Set<User> getAllStudents() {
//        List<String> allAppFormList = new ArrayList<>();
//        List<ApplicationForm> applicationForms = applicationFormService.getAll();
//
//        for (ApplicationForm applicationForm : applicationForms) {
//            Gson applicationFormGson = GsonFactory.getApplicationFormGson();
//
//            String jsonResult = applicationFormGson.toJson(applicationForm);
//            allAppFormList.add(jsonResult);
//            System.out.println(jsonResult);
//        }
//        return allAppFormList;
        Role role=roleService.getRoleByTitle("ROLE_STUDENT");
        Set<User> students = role.getUsers();
        return students;
    }

    @RequestMapping(value="getStatus", method = RequestMethod.GET)
    public Status getStatusById(@RequestParam Long id){
        System.out.println("Request id ="+id);

        List<ApplicationForm>afl=applicationFormService.getByUserId(id);
        //Доделать выбор последней анкеты.
        ApplicationForm af=null;//Последняя анкета.
        return af.getStatus();
    }

    @RequestMapping(value="getUniverse", method = RequestMethod.GET)
    public FormAnswer getUniverseById(@RequestParam Long id){
        List<ApplicationForm>afl=applicationFormService.getByUserId(id);
        //Доделать выбор последней анкеты.
        ApplicationForm af=null;
       List<FormAnswer> formAnswer=formAnswerService.getByApplicationFormAndQuestion(af,formQuestionService.getById(3L));
        return formAnswer.get(0);
    }


    @RequestMapping(value="getCourse", method = RequestMethod.GET)
    public FormAnswer getCourseById(@RequestParam Long id){
        List<ApplicationForm>afl=applicationFormService.getByUserId(id);
        //Доделать выбор последней анкеты.
        ApplicationForm af=null;
        List<FormAnswer> formAnswer=formAnswerService.getByApplicationFormAndQuestion(af,formQuestionService.getById(1L));
        return formAnswer.get(0);
    }

    @RequestMapping(value="changeStudentsStatus", method = RequestMethod.GET)
    public boolean changeStatus(){
        return true;
    };


    @RequestMapping(value = "confirmSelection", method = RequestMethod.POST)
    @ResponseBody
    public void confirmSelection() {
        //TODO
    }


}
