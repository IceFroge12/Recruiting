package ua.kpi.nc.service;

import ua.kpi.nc.domain.model.User;

import java.util.List;

/**
 * Created by dima on 13.04.16.
 */
public interface SenderService {

    void send(User user, String subject, String text);

    void send(List<User> userList, String subject, String text);

}
