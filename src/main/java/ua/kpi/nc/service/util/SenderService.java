package ua.kpi.nc.service.util;


/**
 * Created by dima on 13.04.16.
 */
public interface SenderService {

    void send(String email, String subject, String text);

}
