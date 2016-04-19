package ua.kpi.nc.domain.dao.impl.PostgreSQLImpl;

import ua.kpi.nc.config.SecurityConfig;
import ua.kpi.nc.domain.dao.GenericDaoOnePK;

import java.io.Serializable;

/**
 * Created by IO on 19.04.2016.
 */
public interface SocialNetworkDAO<T, PK extends Serializable>  extends GenericDaoOnePK<T, PK > {
    T getByTitle(String title);
}
