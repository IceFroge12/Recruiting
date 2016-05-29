package ua.kpi.nc.controller.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.SocialInformationService;
import ua.kpi.nc.service.UserService;

/**
 * Created by IO on 29.05.2016.
 */
public class UserAuthServiceSocial {

    private SocialInformationService socialInformationService;

    private UserAuthServiceSocial() {
        socialInformationService = ServiceFactory.getSocialInformationService();
    }

    private static class UserAuthServiceSocialHolder{
        private static final UserAuthServiceSocial HOLDER = new UserAuthServiceSocial();
    }

    public static UserAuthServiceSocial getInstance(){
        return UserAuthServiceSocialHolder.HOLDER;
    }


    public User loadUserBySocialIdNetworkId(Long userId, Long socialNetworkId) throws UsernameNotFoundException {
        SocialInformation socialInformation = socialInformationService.getByIdUserInSocialNetworkSocialType(userId, socialNetworkId);
        if (null == socialInformation){
            return null;
        }else {
            return socialInformation.getUser();
        }
    }
}
