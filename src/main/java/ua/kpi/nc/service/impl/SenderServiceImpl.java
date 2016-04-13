package ua.kpi.nc.service.impl;

import org.springframework.stereotype.Service;
import ua.kpi.nc.service.Sender;

import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.service.SenderService;

import java.util.List;

/**
 * Created by dima on 13.04.16.
 */
@Service
public class SenderServiceImpl implements SenderService {

    private static Sender tlsSender = new Sender("nckpiua@gmail.com", "*******");

    @Override
    public void send(User user, String subject, String text ) {
        tlsSender.send(subject, text, user.getUsername());
    }

    @Override
    public void send(List<User> userList, String subject, String text ) {
        for (User user : userList) {
            tlsSender.send(subject, text, user.getUsername());
        }
    }

}
