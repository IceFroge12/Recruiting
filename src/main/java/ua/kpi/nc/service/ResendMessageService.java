package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.ResendMessage;

import java.util.List;

/**
 * @author Korzh
 */
public interface ResendMessageService {

    ResendMessage getById(Long id);

    ResendMessage getBySubject(String subject);

    Long insert(ResendMessage resendMessage);

    int delete(ResendMessage  resendMessage);

    List<ResendMessage> getAll();
}
