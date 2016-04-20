package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.SocialInformation;

/**
 * Created by Chalienko on 15.04.2016.
 */
public interface SocialInformationDao {
    SocialInformation getById(Long id);
}
