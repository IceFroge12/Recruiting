package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.ResendMessage;

import java.util.List;

/**
 * @author Korzh
 */
public interface ResendMessageDao {

    ResendMessage getById(Long id);

    ResendMessage getBySubject(String subject);

    Long insert(ResendMessage resendMessage);

    int delete(ResendMessage  resendMessage);

    List<ResendMessage> getAll();


}
