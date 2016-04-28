package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.Message;

import java.util.List;

/**
 * @author Korzh
 */
public interface SendMessageService {

    Message getById(Long id);

    Message getBySubject(String subject);

    Long insert(Message message);

    int delete(Message message);

    List<Message> getAll();

    int update(Message message);

    boolean isExsist(Message message);

}
