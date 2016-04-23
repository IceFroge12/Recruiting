package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.NotificationTypeDao;
import ua.kpi.nc.persistence.model.NotificationType;
import ua.kpi.nc.service.NotificationTypeService;

import java.util.Set;

/**
 * @author Korzh
 */
public class NotificationTypeServiceImpl implements NotificationTypeService {
    private NotificationTypeDao notificationTypeDao;

    public NotificationTypeServiceImpl(NotificationTypeDao notificationTypeDao) {
        this.notificationTypeDao = notificationTypeDao;
    }

    @Override
    public NotificationType getById(Long id) {
        return notificationTypeDao.getById(id);
    }

    @Override
    public NotificationType getByTitle(String title) {
        return notificationTypeDao.getByTitle(title);
    }

    @Override
    public int updateNotificationType(NotificationType notificationType) {
        return notificationTypeDao.updateNotificationType(notificationType);
    }

    @Override
    public int deleteNotificationType(NotificationType notificationType) {
        return notificationTypeDao.deleteNotificationType(notificationType);
    }

    @Override
    public Set<NotificationType> getAll() {
        return notificationTypeDao.getAll();
    }
}
