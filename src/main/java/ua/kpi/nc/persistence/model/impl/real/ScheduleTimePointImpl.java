package ua.kpi.nc.persistence.model.impl.real;

import java.sql.Timestamp;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;

public class ScheduleTimePointImpl implements ScheduleTimePoint {

	private static final long serialVersionUID = -8906473195615020302L;

	private Long id;

	private Timestamp timePoint;

	private Set<User> users;

	public ScheduleTimePointImpl() {
	}

	public ScheduleTimePointImpl(Long id, Timestamp timePoint, Set<User> users) {
		super();
		this.id = id;
		this.timePoint = timePoint;
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(Timestamp timePoint) {
		this.timePoint = timePoint;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "ScheduleTimePointImpl [id=" + id + ", timePoint=" + timePoint + ", users=" + users + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).append(timePoint).append(users).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduleTimePointImpl other = (ScheduleTimePointImpl) obj;
		return new EqualsBuilder().append(id, other.id).append(timePoint, other.timePoint).append(users, other.users)
				.isEquals();
	}

}
