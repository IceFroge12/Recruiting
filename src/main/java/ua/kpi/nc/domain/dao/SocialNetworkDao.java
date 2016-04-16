package ua.kpi.nc.domain.dao;

import ua.kpi.nc.domain.model.SocialNetwork;

/**
 * Created by IO on 16.04.2016.
 */
public interface SocialNetworkDao {

    SocialNetwork getByID(Long id);

}
