package ua.kpi.nc.service.mail;

import org.springframework.stereotype.Service;
import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.domain.model.impl.real.UserImpl;

import java.util.List;

/**
 * Created by dima on 09.04.16.
 */
@Service
public class SenderServiceImpl implements SenderService {

    private static Sender tlsSender = new Sender("dmytromyna@gmail.com", "3921555Op");

    @Override
    public void send(String email, String subject, String text) {
        tlsSender.send(subject, text, email);
    }

}
