package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.NotificationType;

import java.util.Set;

/**
 * @author Korzh
 */
public interface NotificationTypeService {
    NotificationType getById(Long id);

    NotificationType getByTitle(String title);

    int updateNotificationType(NotificationType notificationType);

    int deleteNotificationType(NotificationType notificationType);

    Set<NotificationType> getAll();
}
