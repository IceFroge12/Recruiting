package ua.kpi.nc.persistence.dao;

import java.util.Set;

import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.SocialNetwork;
import ua.kpi.nc.persistence.model.User;

/**
 * Created by Chalienko on 15.04.2016.
 */
public interface SocialInformationDao {
	SocialInformation getById(Long id);

	Set<SocialInformation> getByUserId(Long id);

	Long insertSocialInformation(SocialInformation socialInformation, User user, SocialNetwork socialNetwork);

	int updateSocialInformation(SocialInformation socialInformation);

	int deleteSocialInformation(SocialInformation socialInformation);
}
