package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.SocialNetwork;

/**
 * Created by IO on 16.04.2016.
 */
public interface SocialNetworkDao {

    SocialNetwork getByID(Long id);

}
