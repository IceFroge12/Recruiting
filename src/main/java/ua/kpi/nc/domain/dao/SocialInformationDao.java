package ua.kpi.nc.domain.dao;

import ua.kpi.nc.domain.model.SocialInformation;

/**
 * Created by Chalienko on 15.04.2016.
 */
public interface SocialInformationDao {
    SocialInformation getById(Long id);
}
