package ua.kpi.nc.persistence.model.impl.proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.SocialNetwork;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.real.SocialInformationImpl;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.SocialInformationService;

/**
 * Created by Chalienko on 15.04.2016.
 */
public class SocialInformationProxy implements SocialInformation {


    private static final long serialVersionUID = 8463245677164450837L;
    private Long id;

    private SocialInformationImpl socialInformation;

    private SocialInformationService socialInformationService;

    public SocialInformationProxy(Long id) {
        this.id = id;
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
        checkSocialInformationForExist();
        return socialInformation.getAccessInfo();
    }

    @Override
    public void setAccessInfo(String accessInfo) {
        checkSocialInformationForExist();
        socialInformation.setAccessInfo(accessInfo);
    }

    @Override
    public User getUser() {
        checkSocialInformationForExist();
        return socialInformation.getUser();
    }

    @Override
    public void setUser(User user) {
        checkSocialInformationForExist();
        socialInformation.setUser(user);
    }

    @Override
    public SocialNetwork getSocialNetwork() {
        checkSocialInformationForExist();
        return socialInformation.getSocialNetwork();
    }

    @Override
    public void setSocialNetwork(SocialNetwork socialNetwork) {
        checkSocialInformationForExist();
        socialInformation.setSocialNetwork(socialNetwork);
    }

    @Override
    public Long getIdUserInSocialNetwork() {
        checkSocialInformationForExist();
        return socialInformation.getIdUserInSocialNetwork();
    }

    @Override
    public void setIdUserInSocialNetwork(Long idUserInSocialNetwork) {
        checkSocialInformationForExist();
        socialInformation.setIdUserInSocialNetwork(idUserInSocialNetwork);
    }

    private void checkSocialInformationForExist(){
        if (socialInformation == null) {
            socialInformationService = ServiceFactory.getSocialInformationService();
            socialInformation = downloadSocialInformation();
        }
    }

    private SocialInformationImpl downloadSocialInformation() {
        return (SocialInformationImpl) socialInformationService.getById(id);
    }
}
