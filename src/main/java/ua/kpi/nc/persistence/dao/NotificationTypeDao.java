package ua.kpi.nc.persistence.dao;

import java.util.Set;

import ua.kpi.nc.persistence.model.NotificationType;

public interface NotificationTypeDao {

	  public NotificationType getById(Long id);
	  
	  public NotificationType getByTitle(String title);
	  
	  public int updateNotificationType(NotificationType notificationType);
	  
	  public int deleteNotificationType(NotificationType notificationType);
	  
	  public Set<NotificationType> getAll();
	
}
