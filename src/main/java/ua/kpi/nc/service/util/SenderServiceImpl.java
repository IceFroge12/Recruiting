package ua.kpi.nc.service.util;

import ua.kpi.nc.config.PropertiesReader;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Created by dima on 13.04.16.222
 */
public class SenderServiceImpl implements SenderService {

    private PropertiesReader propertiesReader = PropertiesReader.getInstance();

    String email = propertiesReader.propertiesReader("sender.email");
    String password = propertiesReader.propertiesReader("sender.password");

    private Sender tlsSender = new Sender(email, password);

    public SenderServiceImpl(){
    }

    @Override
    public void send(String email, String subject, String text) {
        try {
            tlsSender.send(subject, text, email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
