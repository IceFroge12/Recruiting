package ua.kpi.nc.persistence.dao.impl;

import org.springframework.stereotype.Component;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ua.kpi.nc.persistence.dao.DataSourceFactory;
import ua.kpi.nc.persistence.dao.RoleDao;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.proxy.UserProxy;
import ua.kpi.nc.persistence.model.impl.real.RoleImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */

public class RoleDaoImpl extends JdbcDaoSupport implements RoleDao {

    private static Logger log = Logger.getLogger(RoleDaoImpl.class.getName());

    public RoleDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public Role getByID(Long id) {
        if (log.isTraceEnabled()){
            log.trace("Looking for role with id = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters("SELECT role.id, role.role FROM public.role where role.id = ?;", new RoleExtractor(), id);
    }

    @Override
    public Role getByTitle(String title) {
        if (log.isTraceEnabled()){
            log.trace("Looking for role with title = " + title);
        }
        return this.getJdbcTemplate().queryWithParameters("SELECT role.id, role.role FROM public.role where role.role = ?;", new RoleExtractor(), title);
    }

    private static final class RoleExtractor implements ResultSetExtractor<Role>{
        @Override
        public Role extractData(ResultSet resultSet) throws SQLException {
            Role role = new RoleImpl();
            role.setId(resultSet.getLong("id"));
            role.setRoleName(resultSet.getString("role"));
            return role;
        }
    }
//
//    private static Logger log = Logger.getLogger(RoleDaoImpl.class.getName());
//
//    public RoleDaoImpl() {
//
//    }
//
//    @Override
//    public Role getByID(Long id) {
//        String sql = "SELECT r.id, r.role, ur.id_user\n" +
//                "FROM \"role\" r\n" +
//                "  INNER JOIN user_role ur ON id = id_role\n" +
//                "WHERE r.id = " + id;
//        log.trace("Looking for role with id = " + id);
//        return getByQuery(sql);
//    }
//
//    @Override
//    public Role getByTitle(String title) {
//        String sql = "SELECT r.id, r.role, ur.id_role\n" +
//                "FROM \"role\" r\n" +
//                "  INNER JOIN user_role ur ON id = id_role\n" +
//                "WHERE r.role = " + "'" + title + "'";
//        log.trace("Looking for role with title = " + title);
//        return getByQuery(sql);
//    }
//
//    private Role getByQuery(String sql) {
//        Role role = new RoleImpl();
//        Set<User> users = new HashSet<>();
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql);
//             ResultSet resultSet = statement.executeQuery()) {
//            log.trace("Open connection");
//            log.trace("Create prepared statement");
//            log.trace("Get result set");
//            if (resultSet.next()) {
//                log.trace("Create user to return");
//                role.setId(resultSet.getLong("id"));
//                role.setRoleName(resultSet.getString("role"));
//                users.add(new UserProxy(resultSet.getLong("id_user")));
//            }
//            while (resultSet.next()) {
//                users.add(new UserProxy(resultSet.getLong("id_user")));
//            }
//        } catch (SQLException e) {
//            log.error("Cannot read user", e);
//            return null;
//        }
//        if (null == role) {
//            log.debug("Role not found");
//        } else {
//            log.trace("Role " + role + " found");
//        }
//        log.trace("Returning role");
//        return role;
//    }
}
