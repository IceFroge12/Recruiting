package ua.kpi.nc.domain.model;

import java.io.Serializable;

/**
 * Created by Chalienko on 15.04.2016.
 */
public interface SocialInformation extends Serializable{

    Long getId();

    void setId(Long id);

    String getAccessInfo();

    void setAccessInfo(String accessInfo);

    User getUser();

    void setUser(User user);

    SocialNetwork getSocialNetwork();

    void setSocialNetwork(SocialNetwork socialNetwork);
}
