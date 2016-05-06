package ua.kpi.nc.controller.admin;

import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.NotificationType;
import ua.kpi.nc.service.EmailTemplateService;
import ua.kpi.nc.service.NotificationTypeService;
import ua.kpi.nc.service.ServiceFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by dima on 06.05.16.
 */
@RestController
@RequestMapping("/admin")
public class AdminNotificationController {

    private NotificationTypeService notificationTypeService = ServiceFactory.getNotificationTypeService();
    private EmailTemplateService emailTemplateService = ServiceFactory.getEmailTemplateService();

    @RequestMapping(value = "/getAllNotificationType", method = RequestMethod.POST)
    public Set<NotificationType> getAllNotificationType() {
        Set<NotificationType> notificationTypes = notificationTypeService.getAll();
        return notificationTypes;
    }

    @RequestMapping(value = "/showTemplate", method = RequestMethod.POST)
    public EmailTemplate showTemplate(@RequestParam String title) {
        System.out.println(title);
        EmailTemplate emailTemplate = emailTemplateService.getByTitle(title);
        System.out.println(emailTemplate);
        return emailTemplate;
    }

    @RequestMapping(value = "/changeNotification", method = RequestMethod.POST)
    public void changeNotification(@RequestBody EmailTemplate emailTemplate) {
        System.out.println(emailTemplate);
        emailTemplateService.updateEmailTemplate(emailTemplate);
    }

}
