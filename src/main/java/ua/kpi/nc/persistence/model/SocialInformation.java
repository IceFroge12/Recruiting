package ua.kpi.nc.persistence.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created by Chalienko on 15.04.2016.
 */
public interface SocialInformation extends Serializable{

    Long getId();

    void setId(Long id);

    @JsonIgnore
    String getAccessInfo();

    void setAccessInfo(String accessInfo);

    @JsonIgnore
    User getUser();

    void setUser(User user);
    @JsonIgnore
    SocialNetwork getSocialNetwork();

    void setSocialNetwork(SocialNetwork socialNetwork);
}
