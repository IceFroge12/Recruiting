package ua.kpi.nc.service.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by dima on 13.04.16.222
 */
public class SenderServiceImpl implements SenderService {

    @Value("${sender.email}")
    private String email;

    @Value("${sender.password}")
    private String password;

    private Sender tlsSender = new Sender("dmytromyna@gmail.com", "");

    @Override
    public void send(String email, String subject, String text) {
        tlsSender.send(subject, text, email);
    }


}
