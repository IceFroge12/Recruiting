package ua.kpi.nc.service;

import org.springframework.stereotype.Service;
import ua.kpi.nc.domain.model.Role;

/**
 * Created by Chalienko on 13.04.2016.
 */
@Service
public interface RoleService {
    Role getRoleById(Long id);
}
