package ua.kpi.nc.persistence.model;

import org.springframework.security.core.userdetails.UserDetails;
import ua.kpi.nc.service.UserService;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface User extends Serializable, UserDetails {

    Long getId();

    void setId(Long id);

    String getEmail();

    void setEmail(String email);

    String getFirstName();

    void setFirstName(String firstName);

    String getConfirmToken();

    void setConfirmToken(String confirmToken);

    boolean isActive();

    void setActive(boolean active);

    Timestamp getRegistrationDate();

    void setRegistrationDate(Timestamp registrationDate);

    String getSecondName();

    void setSecondName(String secondName);

    String getLastName();

    void setLastName(String lastName);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);

    String getPassword();

    void setPassword(String password);

    Set<SocialInformation> getSocialInformations();

    void setSocialInformations(Set<SocialInformation> socialInformations);

    Long getExpireDate();

    List<ScheduleTimePoint> getScheduleTimePoint();

    void setScheduleTimePoint(List<ScheduleTimePoint> scheduleTimePoint);
}
