package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.SocialNetwork;
import ua.kpi.nc.persistence.model.User;

import java.sql.Timestamp;
import java.util.Set;

/**
 * @author Chalienko 15.04.2016.
 */
public interface SocialInformationService {
    SocialInformation getById(Long id);

    Set<SocialInformation> getByUserId(Long id);

    Long insertSocialInformation(SocialInformation socialInformation, User user, SocialNetwork socialNetwork, Timestamp writeTime);

    int updateSocialInformation(SocialInformation socialInformation);

    int deleteSocialInformation(SocialInformation socialInformation);

    boolean isExist(String email, Long idSocialNetwork);

    SocialInformation getByUserEmailSocialNetworkType(String email, Long socialNetworkId);
}
