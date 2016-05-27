package ua.kpi.nc.controller.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.SocialInformationService;
import ua.kpi.nc.service.UserService;

/**
 * Created by IO on 27.05.2016.
 */
public class SocialNetworkAuthenticationManager implements AuthenticationManager {

    private UserAuthService userService;
    private SocialInformationService socialInformationService;

    private SocialNetworkAuthenticationManager() {
        userService = UserAuthService.getInstance();
        socialInformationService = ServiceFactory.getSocialInformationService();
    }

    private static class SocialNetworkAuthenticationManagerHolder {
        private static final SocialNetworkAuthenticationManager HOLDER = new SocialNetworkAuthenticationManager();
    }

    public static SocialNetworkAuthenticationManager getInstance() {
        return SocialNetworkAuthenticationManagerHolder.HOLDER;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = ((User) authentication.getDetails());
        if (socialInformationService.isExist(user.getEmail(), user.getSocialInformations().iterator().next().getSocialNetwork().getId())){
            return null;
        }else {
            throw new UsernameNotFoundException("not found");
        }
    }
}
