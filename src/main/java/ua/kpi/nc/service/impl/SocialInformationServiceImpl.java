package ua.kpi.nc.service.impl;

import org.springframework.stereotype.Repository;
import ua.kpi.nc.persistence.dao.SocialInformationDao;
import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.SocialNetwork;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.SocialInformationService;

import java.util.Set;

/**
 * @author IO  16.04.2016.
 */

public class SocialInformationServiceImpl implements SocialInformationService {

    private SocialInformationDao socialInformationDao;

    public SocialInformationServiceImpl(SocialInformationDao socialInformationDao) {
        this.socialInformationDao = socialInformationDao;
    }

    @Override
    public SocialInformation getById(Long id) {
        return socialInformationDao.getById(id);
    }

    @Override
    public Set<SocialInformation> getByUserId(Long id) {
        return socialInformationDao.getByUserId(id);
    }

    @Override
    public Long insertSocialInformation(SocialInformation socialInformation, User user, SocialNetwork socialNetwork) {
        return socialInformationDao.insertSocialInformation(socialInformation, user,socialNetwork);
    }

    @Override
    public int updateSocialInformation(SocialInformation socialInformation) {
        return socialInformationDao.updateSocialInformation(socialInformation);
    }

    @Override
    public int deleteSocialInformation(SocialInformation socialInformation) {
        return socialInformationDao.deleteSocialInformation(socialInformation);
    }
}
