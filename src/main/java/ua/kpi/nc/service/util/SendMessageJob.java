package ua.kpi.nc.service.util;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import ua.kpi.nc.persistence.model.Message;
import ua.kpi.nc.service.SendMessageService;
import ua.kpi.nc.service.ServiceFactory;

import javax.mail.MessagingException;
import java.util.List;

/**
 * Created by dima on 27.04.16.
 */
@DisallowConcurrentExecution
public class SendMessageJob extends QuartzJobBean {

    private static Logger log = LoggerFactory.getLogger(SendMessageJob.class.getName());

    private SendMessageService sendMessageService = ServiceFactory.getResendMessageService();

    private SenderService senderService = SenderServiceImpl.getInstance();

    @Override
    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
        List<Message> messageServiceList = sendMessageService.getAll();
        for (Message message : messageServiceList) {
            try {
                sendMessageService.delete(message);
                senderService.send(message.getEmail(), message.getSubject(), message.getText());
            } catch (MessagingException e) {
                log.error("Cannot send message {}",e);
            }
        }

        JobKey jobKey = ctx.getJobDetail().getKey();
        log.info("Job key: {}", jobKey);
    }

}
