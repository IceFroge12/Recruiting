package ua.kpi.nc.service;

import org.springframework.stereotype.Service;
import ua.kpi.nc.domain.model.User;

/**
 * Created by Chalienko on 13.04.2016.
 */
@Service
public interface UserService {
    User getUserByUsername(String username);

    User getUserByID(Long id);
}