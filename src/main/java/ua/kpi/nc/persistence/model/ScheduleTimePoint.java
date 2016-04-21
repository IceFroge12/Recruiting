package ua.kpi.nc.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;


public interface ScheduleTimePoint extends Serializable {
	public Long getId();

	public void setId(Long id);

	public Timestamp getTimePoint();

	public void setTimePoint(Timestamp timePoint);

	public Set<User> getUsers();

	public void setUsers(Set<User> users);
}
