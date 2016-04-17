package ua.kpi.nc.service;

import ua.kpi.nc.domain.model.Role;

/**
 * Created by Chalienko on 13.04.2016.
 */

public interface RoleService {
    Role getRoleById(Long id);
    Role getRoleByTitle(String title);
}
