package ua.kpi.nc.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Chalienko on 15.04.2016.
 */
public class RegistrationConfirm  implements Serializable{

    private static final long serialVersionUID = 238426716708974952L;

    private User user;

    private Timestamp expirationTime;

    public RegistrationConfirm() {
    }

    public RegistrationConfirm(User user, Timestamp expirationTime) {
        this.user = user;
        this.expirationTime = expirationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Timestamp expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public String toString() {
        return "RegistrationConfirm: " +
                "user=" + user +
                ", expirationTime=" + expirationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RegistrationConfirm that = (RegistrationConfirm) o;

        return new EqualsBuilder()
                .append(user, that.user)
                .append(expirationTime, that.expirationTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(user)
                .append(expirationTime)
                .toHashCode();
    }
}
