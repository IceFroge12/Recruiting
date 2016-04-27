package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.NotificationType;

import java.util.Set;

/**
 * Created by dima on 27.04.16.
 */
public interface NotificationTypeDao {

    public NotificationType getById(Long id);

    public NotificationType getByTitle(String title);

    public int updateNotificationType(NotificationType notificationType);

    public int deleteNotificationType(NotificationType notificationType);

    public Set<NotificationType> getAll();
}
