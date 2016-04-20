package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.Role;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface RoleDao {
    Role getByID(Long id);

    Role getByTitle(String title);
}
