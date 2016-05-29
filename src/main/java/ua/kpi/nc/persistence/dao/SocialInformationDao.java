package ua.kpi.nc.persistence.dao;

import java.sql.Timestamp;
import java.util.Set;

import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.SocialNetwork;
import ua.kpi.nc.persistence.model.User;

/**
 * Created by Chalienko on 15.04.2016.
 */
public interface SocialInformationDao {

	SocialInformation getById(Long id);

	SocialInformation getByUserEmailSocialType(String email, Long idSocialType);

	Set<SocialInformation> getByUserId(Long id);

	Long insertSocialInformation(SocialInformation socialInformation, User user, SocialNetwork socialNetwork);

	int updateSocialInformation(SocialInformation socialInformation);

	int deleteSocialInformation(SocialInformation socialInformation);

	boolean isExist(String email, Long idSocialNetwork);
}
