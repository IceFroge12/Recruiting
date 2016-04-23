package ua.kpi.nc.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.RoleDao;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.impl.real.RoleImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Chalienko on 13.04.2016.
 */

public class RoleDaoImpl extends JdbcDaoSupport implements RoleDao {

    private static Logger log = LoggerFactory.getLogger(RoleDaoImpl.class.getName());

    public RoleDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public Role getByID(Long id) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for role with id = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters("SELECT role.id, role.role FROM public.role where role.id = ?;", new RoleExtractor(), id);
    }

    @Override
    public Role getByTitle(String title) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for role with title = " + title);
        }
        return this.getJdbcTemplate().queryWithParameters("SELECT role.id, role.role FROM public.role where role.role = ?;", new RoleExtractor(), title);
    }

    private static final class RoleExtractor implements ResultSetExtractor<Role> {
        @Override
        public Role extractData(ResultSet resultSet) throws SQLException {
            Role role = new RoleImpl();
            role.setId(resultSet.getLong("id"));
            role.setRoleName(resultSet.getString("role"));
            return role;
        }
    }
}
