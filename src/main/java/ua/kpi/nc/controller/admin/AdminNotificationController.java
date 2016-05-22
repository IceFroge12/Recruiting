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
        return notificationTypeService.getAll();
    }

    @RequestMapping(value = "/showTemplate", method = RequestMethod.POST)
    public EmailTemplate showTemplate(@RequestParam String title) {
        return emailTemplateService.getByTitle(title);
    }

    @RequestMapping(value = "/changeNotification", method = RequestMethod.POST)
    public void changeNotification(@RequestBody EmailTemplate emailTemplate) {
        emailTemplateService.updateEmailTemplate(emailTemplate);
    }
}
