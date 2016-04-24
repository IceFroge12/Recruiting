package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.SocialNetworkDao;
import ua.kpi.nc.persistence.model.SocialNetwork;
import ua.kpi.nc.service.SocialNetworkService;

/**
 * @author IO 16.04.2016.
 */
public class SocialNetworkServiceImpl implements SocialNetworkService{
    private SocialNetworkDao socialNetworkDao;

    public SocialNetworkServiceImpl(SocialNetworkDao socialNetworkDao) {
        this.socialNetworkDao = socialNetworkDao;
    }

    @Override
    public SocialNetwork getByID(Long id) {
        return socialNetworkDao.getByID(id);
    }
}
