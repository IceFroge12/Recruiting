package ua.kpi.nc.controller.admin;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.nc.DeadlineController;
import ua.kpi.nc.persistence.dto.RecruitmentSettingsDto;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.EmailTemplateEnum;
import ua.kpi.nc.persistence.model.impl.real.RecruitmentImpl;
import ua.kpi.nc.service.*;
import ua.kpi.nc.service.util.SenderService;
import ua.kpi.nc.service.util.SenderServiceImpl;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dima on 30.04.16.
 */
@RestController
@RequestMapping("/admin")
public class AdminRecruitmentSettingsController {

    private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();
    private DeadlineController deadlineController = DeadlineController.getInstance();
    private EmailTemplateService emailTemplateService = ServiceFactory.getEmailTemplateService();
    private UserService userService = ServiceFactory.getUserService();
    private SenderService senderService = SenderServiceImpl.getInstance();

    @RequestMapping(value = "/addRecruitment", method = RequestMethod.POST)
    public void addRecruitmentSettings(@RequestBody RecruitmentSettingsDto recruitmentDto) throws MessagingException {
        if (null == recruitmentService.getCurrentRecruitmnet()) {
            Recruitment recruitment = new RecruitmentImpl();
            recruitment.setName(recruitmentDto.getName());
            recruitment.setEndDate(Timestamp.valueOf(recruitmentDto.getEndDate()));
            recruitment.setStartDate(new Timestamp(System.currentTimeMillis()));
            recruitment.setRegistrationDeadline(Timestamp.valueOf(recruitmentDto.getRegistrationDeadline()));
            recruitment.setScheduleChoicesDeadline(Timestamp.valueOf(recruitmentDto.getScheduleChoicesDeadline()));
            recruitment.setMaxAdvancedGroup(recruitmentDto.getMaxAdvancedGroup());
            recruitment.setMaxGeneralGroup(recruitmentDto.getMaxGeneralGroup());

            recruitmentService.addRecruitment(recruitment);
            EmailTemplate newRecruitmentTemplate = emailTemplateService.getById(EmailTemplateEnum.CONFIRM_PARTICIPATE.getId());
            List<User> users = userService.getStudentsWithNotconnectedForms();
            for (User student : users) {
                String subject = newRecruitmentTemplate.getTitle();
                String text = emailTemplateService.showTemplateParams(newRecruitmentTemplate.getText(), student);
                senderService.send(student.getEmail(), subject, text);
            }
            deadlineController.setRegisteredDeadline(recruitment.getRegistrationDeadline());
            deadlineController.setEndOfRecruitingDeadLine(recruitment.getEndDate());
        }
    }

    @RequestMapping(value = "/editRecruitment", method = RequestMethod.POST)
    public void editRecruitment(@RequestBody RecruitmentSettingsDto recruitmentDto){
        Recruitment recruitment = recruitmentService.getRecruitmentById(recruitmentDto.getId());
        recruitment.setName(recruitmentDto.getName());
        recruitment.setRegistrationDeadline(Timestamp.valueOf(recruitmentDto.getRegistrationDeadline()));
        recruitment.setScheduleChoicesDeadline(Timestamp.valueOf(recruitmentDto.getScheduleChoicesDeadline()));
        recruitment.setEndDate(Timestamp.valueOf(recruitmentDto.getEndDate()));
        recruitment.setMaxGeneralGroup(recruitmentDto.getMaxGeneralGroup());
        recruitment.setMaxAdvancedGroup(recruitmentDto.getMaxAdvancedGroup());
        recruitmentService.updateRecruitment(recruitment);
        deadlineController.setRegisteredDeadline(recruitment.getRegistrationDeadline());
        deadlineController.setEndOfRecruitingDeadLine(recruitment.getEndDate());
    }

    @RequestMapping(value = "/getCurrentRecruitment", method = RequestMethod.GET)
    public Recruitment getCurrentRecruitment() {
        return recruitmentService.getCurrentRecruitmnet();
    }

}
