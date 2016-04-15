package ua.kpi.nc.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.service.Sender;
import ua.kpi.nc.service.SenderService;

import java.util.Set;

/**
 * Created by dima on 13.04.16.
 */
@Service
public class SenderServiceImpl implements SenderService {

    @Value("${sender.email}")
    private static String email;

    @Value("${sender.password}")
    private static String password;

    private static Sender tlsSender = new Sender(email, password);

    @Override
    public void send(User user, String subject, String text) {
        tlsSender.send(subject, text, user.getEmail());
    }

    @Override
    public void send(Set<User> userList, String subject, String text) {
        for (User user : userList) {
            tlsSender.send(subject, text, user.getEmail());
        }
    }

}
