package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.ResendMessageDao;
import ua.kpi.nc.persistence.model.ResendMessage;
import ua.kpi.nc.service.ResendMessageService;

import java.util.List;

/**
 * @author Korzh
 */
public class ResendMessageServiceImpl implements ResendMessageService{
    private ResendMessageDao resendMessageDao;

    public ResendMessageServiceImpl(ResendMessageDao resendMessageDao) {
        this.resendMessageDao = resendMessageDao;
    }

    @Override
    public ResendMessage getById(Long id) {
        return resendMessageDao.getById(id);
    }

    @Override
    public ResendMessage getBySubject(String subject) {
        return resendMessageDao.getBySubject(subject);
    }

    @Override
    public Long insert(ResendMessage resendMessage) {
        return resendMessageDao.insert(resendMessage);
    }

    @Override
    public int delete(ResendMessage resendMessage) {
        return resendMessageDao.delete(resendMessage);
    }

    @Override
    public List<ResendMessage> getAll() {
        return resendMessageDao.getAll();
    }
}
