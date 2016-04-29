package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.SendMessageDao;
import ua.kpi.nc.persistence.model.Message;
import ua.kpi.nc.service.SendMessageService;

import java.util.List;

/**
 * @author Korzh
 */
public class SendMessageServiceImpl implements SendMessageService {
    private SendMessageDao sendMessageDao;

    public SendMessageServiceImpl(SendMessageDao sendMessageDao) {
        this.sendMessageDao = sendMessageDao;
    }

    @Override
    public Message getById(Long id) {
        return sendMessageDao.getById(id);
    }

    @Override
    public Message getBySubject(String subject) {
        return sendMessageDao.getBySubject(subject);
    }

    @Override
    public Long insert(Message message) {
        return sendMessageDao.insert(message);
    }

    @Override
    public int delete(Message message) {
        return sendMessageDao.delete(message);
    }

    @Override
    public List<Message> getAll() {
        return sendMessageDao.getAll();
    }

    @Override
    public int update(Message message) {
        return sendMessageDao.update(message);
    }

    @Override
    public boolean isExsist(Message message) {
        return sendMessageDao.isExist(message);
    }

}
