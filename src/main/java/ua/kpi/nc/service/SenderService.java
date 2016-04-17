package ua.kpi.nc.service;


import ua.kpi.nc.domain.model.User;

import java.util.List;
import java.util.Set;

/**
 * Created by dima on 13.04.16.
 */
public interface SenderService {

    public void send(User user);

    public void send(List<User> userList);

}
