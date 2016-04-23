package ua.kpi.nc.service.impl;

import org.springframework.stereotype.Repository;
import ua.kpi.nc.persistence.dao.SocialInformationDao;
import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.service.SocialInformationService;

/**
 * Created by IO on 16.04.2016.
 */

public class SocialInformationServiceImpl implements SocialInformationService {

    private SocialInformationDao socialInformationDao;

    public SocialInformationServiceImpl(SocialInformationDao socialInformationDao) {
        this.socialInformationDao = socialInformationDao;
    }

    @Override
    public SocialInformation getById(Long id) {
        return null;
    }
}
