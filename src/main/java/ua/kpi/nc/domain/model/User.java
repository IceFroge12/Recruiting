package ua.kpi.nc.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface User extends Serializable {

    Long getId();

    void setId(Long id);

    String getEmail();

    void setEmail(String email);

    String getFirstName();

    void setFirstName(String firstName);

    String getSecondName();

    void setSecondName(String secondName);

    String getLastName();

    void setLastName(String lastName);

    boolean isActive();

    void setActive(boolean active);

    Timestamp getRegistrationDate();

    void setRegistrationDate(Timestamp registrationDate);

    String getConfirmToken();

    void setConfirmToken(String confirmToken);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);

    String getPassword();

    void setPassword(String password);

    Set<SocialInformation> getSocialInformations();

    void setSocialInformations(Set<SocialInformation> socialInformations);
}
