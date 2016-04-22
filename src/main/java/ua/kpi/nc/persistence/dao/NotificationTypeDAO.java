package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.NotificationType;

import java.util.Set;

/**
 * @author Korzh
 */
public interface NotificationTypeDAO {


    NotificationType getById(Long id);

    NotificationType getByTitle(String title);

    int updateNotificationType(NotificationType notificationType);

    int deleteNotificationType(NotificationType notificationType);

    Set<NotificationType> getAll();
}
