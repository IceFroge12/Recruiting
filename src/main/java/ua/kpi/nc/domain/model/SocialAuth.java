package ua.kpi.nc.domain.model;

import ua.kpi.nc.domain.model.type.SocialEnum;

/**
 * Created by Chalienko on 14.04.2016.
 */
public interface SocialAuth extends Model{
    SocialEnum getAuthType();

    void setAuthType(SocialEnum authType);

    String getAuthKey();

    void setAuthKey(String authKey);
}
