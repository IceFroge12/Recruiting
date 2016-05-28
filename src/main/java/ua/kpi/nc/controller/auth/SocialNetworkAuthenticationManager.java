package ua.kpi.nc.controller.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.SocialInformationService;
import ua.kpi.nc.service.UserService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by IO on 27.05.2016.
 */
public class SocialNetworkAuthenticationManager implements AuthenticationManager {

    private static Logger log = LoggerFactory.getLogger(SocialNetworkAuthenticationManager.class.getName());
    private final static String ACCESS_TOKEN_TITLE = "accessToken";
    private final static boolean IS_ACTIVE = true;
    private final static String NO_PASSWORD = "";
    private final static String NO_CONFIRM_TOKEN = "";

    private UserAuthService userAuthService;
    private UserService userService;
    private SocialInformationService socialInformationService;


    private SocialNetworkAuthenticationManager() {
        userAuthService = UserAuthService.getInstance();
        socialInformationService = ServiceFactory.getSocialInformationService();
        userService = ServiceFactory.getUserService();
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
        SocialInformation socialInformation = user.getSocialInformations().iterator().next();
        User existUser = userAuthService.loadUserByUsername(user.getEmail());
        if (null != existUser) {
            if (socialInformationService.isExist(user.getEmail(), user.getSocialInformations().iterator().next().getSocialNetwork().getId())) {
                throw new UsernameNotFoundException("");
            } else {
                return new UserAuthentication(existUser);
            }
        } else {
            return registerFaceBookUser(socialInformation);
        }
    }


    private void registerNewUser(SocialInformation socialInformation) {
//        Long socialNetworkId = getSocialNetworkId(socialInformation);
//        if (Objects.equals(socialNetworkId, SocialNetworkEnum.FaceBook.getId())) {
//        }
//        //user.getSocialInformations().add(socialInformation);
//        socialInformationService.insertSocialInformation(socialInformation, user, socialInformation.getSocialNetwork());
    }

    private UserAuthentication registerFaceBookUser(SocialInformation socialInformation) {
        Facebook facebook = new FacebookTemplate(getFaceBookAccessToken(socialInformation.getAccessInfo()));
        org.springframework.social.facebook.api.User profile = facebook.userOperations().getUserProfile();
        User user = createNewUser(profile);
        userService.insertUser(user, new ArrayList<>(Collections.singletonList(RoleEnum.getRole(RoleEnum.ROLE_STUDENT))));
        socialInformationService.insertSocialInformation(socialInformation, user, socialInformation.getSocialNetwork(), new Timestamp(System.currentTimeMillis()));
        return new UserAuthentication(user);
    }

    private Long getSocialNetworkId(SocialInformation socialInformation) {
        return socialInformation.getSocialNetwork().getId();
    }

    private String getFaceBookAccessToken(String info) {
        return ((JsonObject) new JsonParser().parse(info)).get(ACCESS_TOKEN_TITLE).getAsString();
    }

    private User createNewUser(org.springframework.social.facebook.api.User profile){
        User user = new UserImpl(profile.getEmail(),
                profile.getFirstName(),
                profile.getMiddleName(),
                profile.getLastName(),
                NO_PASSWORD,
                IS_ACTIVE,
                new Timestamp(System.currentTimeMillis()),
                NO_CONFIRM_TOKEN
        );
        user.setRoles(new HashSet<Role>(){{
            add(RoleEnum.getRole(RoleEnum.ROLE_STUDENT));
        }});
        return user;
    }
}
