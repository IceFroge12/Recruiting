package ua.kpi.nc.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.config.PropertiesReader;
import ua.kpi.nc.persistence.model.Message;
import ua.kpi.nc.service.SendMessageService;
import ua.kpi.nc.service.ServiceFactory;

import javax.mail.MessagingException;

/**
 * Created by dima on 13.04.16.222
 */
public class SenderServiceImpl implements SenderService {

    private static Logger log = LoggerFactory.getLogger(SenderService.class.getName());

    private static volatile SenderServiceImpl instance;

    private SenderServiceImpl() {

    }

    public static SenderServiceImpl getInstance() {
        if (instance == null)
            synchronized (SenderServiceImpl.class) {
                if (instance == null)
                    instance = new SenderServiceImpl();
            }
        return instance;
    }


    private PropertiesReader propertiesReader = PropertiesReader.getInstance();
    private SendMessageService sendMessageService = ServiceFactory.getResendMessageService();

    private String email = propertiesReader.propertiesReader("sender.email");
    private String password = propertiesReader.propertiesReader("sender.password");

    private Sender tlsSender = new Sender(email, password);


    @Override
    public void send(String email, String subject, String text) {
        try {
            tlsSender.send(subject, text, email);
        } catch (MessagingException e) {
                log.info("Message not resend {}", e);
                Message message = new Message(subject, text, email, false);
                sendMessageService.insert(message);
        }
    }


}
