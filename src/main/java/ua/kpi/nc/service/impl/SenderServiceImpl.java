package ua.kpi.nc.service.impl;

import org.springframework.stereotype.Service;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.service.Sender;
import ua.kpi.nc.service.SenderService;

import java.util.List;

/**
 * Created by dima on 13.04.16.
 */
@Service
public class SenderServiceImpl implements SenderService {

    private static Sender tlsSender = new Sender("nckpiua@gmail.com", "nckpiua2016");

    @Override
    public void send(User user) {
        tlsSender.send("Учебно-научный центр NetCracker при НТУУ «КПИ»", "ADMEN", user.getEmail());
    }

    @Override
    public void send(List<User> userList) {

    }
}
