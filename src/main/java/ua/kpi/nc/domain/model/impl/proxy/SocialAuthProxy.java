package ua.kpi.nc.domain.model.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import ua.kpi.nc.domain.model.SocialAuth;
import ua.kpi.nc.domain.model.impl.real.SocialAuthImpl;
import ua.kpi.nc.domain.model.type.SocialEnum;
import ua.kpi.nc.service.SocialAuthService;

/**
 * Created by Chalienko on 14.04.2016.
 */
@Configurable
public class SocialAuthProxy implements SocialAuth {

    private Long id;

    private SocialAuthImpl socialAuth;

    @Autowired
    private SocialAuthService socialAuthService;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public SocialEnum getAuthType() {
        if (socialAuth == null) {
            socialAuth = downloadSocialAuth();
        }
        return socialAuth.getAuthType();
    }

    @Override
    public void setAuthType(SocialEnum authType) {
        if (socialAuth == null) {
            socialAuth = downloadSocialAuth();
        }
        socialAuth.setAuthType(authType);
    }

    @Override
    public String getAuthKey() {
        if (socialAuth == null) {
            socialAuth = downloadSocialAuth();
        }
        return socialAuth.getAuthKey();
    }

    @Override
    public void setAuthKey(String authKey) {
        if (socialAuth == null) {
            socialAuth = downloadSocialAuth();
        }
        socialAuth.setAuthKey(authKey);
    }

    private SocialAuthImpl downloadSocialAuth() {
        return (SocialAuthImpl) socialAuthService.getAuthById(id);
    }

}
