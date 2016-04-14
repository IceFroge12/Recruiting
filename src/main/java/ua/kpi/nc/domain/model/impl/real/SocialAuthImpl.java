package ua.kpi.nc.domain.model.impl.real;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ua.kpi.nc.domain.model.SocialAuth;
import ua.kpi.nc.domain.model.type.SocialEnum;

/**
 * Created by Chalienko on 14.04.2016.
 */
public class SocialAuthImpl implements SocialAuth {

    private Long id;

    private SocialEnum authType;

    private String authKey;

    public SocialAuthImpl() {

    }

    public SocialAuthImpl(Long id, SocialEnum authType, String authKey) {
        this.id = id;
        this.authType = authType;
        this.authKey = authKey;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    public SocialEnum getAuthType() {
        return authType;
    }

    public void setAuthType(SocialEnum authType) {
        this.authType = authType;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SocialAuthImpl that = (SocialAuthImpl) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(authType, that.authType)
                .append(authKey, that.authKey)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(authType)
                .append(authKey)
                .toHashCode();
    }
}
