package ua.kpi.nc.domain.model.impl.real;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ua.kpi.nc.domain.model.SocialInformation;
import ua.kpi.nc.domain.model.SocialNetwork;
import ua.kpi.nc.domain.model.User;



/**
 * Created by Chalienko on 15.04.2016.
 */
public class SocialInformationImpl implements SocialInformation {
    private static final long serialVersionUID = -3471843543708949773L;
    private Long id;
    private String accessInfo;
    private User user;
    private SocialNetwork socialNetwork;

    public SocialInformationImpl(Long id,String accessInfo, User user, SocialNetwork socialNetwork) {
        this.id = id;
        this.accessInfo = accessInfo;
        this.user = user;
        this.socialNetwork = socialNetwork;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAccessInfo() {
        return accessInfo;
    }

    @Override
    public void setAccessInfo(String accessInfo) {
        this.accessInfo = accessInfo;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public SocialNetwork getSocialNetwork() {
        return socialNetwork;
    }

    @Override
    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    @Override
    public String toString() {
        return "SocialInformation: " +
                "accessInfo='" + accessInfo + '\'' +
                ", user=" + user +
                ", socialNetwork=" + socialNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SocialInformationImpl that = (SocialInformationImpl) o;

        return new EqualsBuilder()
                .append(accessInfo, that.accessInfo)
                .append(user, that.user)
                .append(socialNetwork, that.socialNetwork)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(accessInfo)
                .append(user)
                .append(socialNetwork)
                .toHashCode();
    }
}
