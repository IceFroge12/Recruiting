package ua.kpi.nc.service.mail;


import ua.kpi.nc.domain.model.User;
import ua.kpi.nc.domain.model.impl.real.UserImpl;

import java.util.List;

/**
 * Created by dima on 09.04.16.
 */
public interface SenderService {

    public void send(String email, String subject, String text);

}
