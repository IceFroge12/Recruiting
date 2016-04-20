package ua.kpi.nc.persistence.dao.impl;

import org.apache.log4j.Logger;
import ua.kpi.nc.persistence.dao.UserDao;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.proxy.RoleProxy;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

    private static Logger log = Logger.getLogger(UserDaoImpl.class.getName());

    public UserDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public User getByID(Long id) {
        if (log.isTraceEnabled()){
            log.trace("Looking for user with id = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters("SELECT u.id, u.email, u.first_name,u.last_name, u.second_name, " +
                "u.password, ur.id_role\n" +
                "FROM \"user\" u\n" +
                "  INNER JOIN user_role ur ON id = id_user\n WHERE u.email = ?;", new UserExtractor(), id);
    }

    @Override
    public User getByUsername(String email) {
        if (log.isTraceEnabled()) {
            log.trace("Looking for user with email = " + email);
        }
        return this.getJdbcTemplate().queryWithParameters("SELECT u.id, u.email, u.first_name,u.last_name, u.second_name, " +
                "u.password, ur.id_role\n" +
                "FROM \"user\" u\n" +
                "  INNER JOIN user_role ur ON id = id_user\n WHERE u.email = ?;", new UserExtractor(), email);
    }

    @Override
    public boolean isExist(String email) {
        int cnt = this.getJdbcTemplate().update("select exists(SELECT email from \"user\" where email =?)", email);
        return cnt > 0;
    }

    @Override
    public int insertUser(User user) {
        if (user.getRoles() == null){
            return 0;
        }
        return 0;
    }

    @Override
    public int deleteUser(User user) {
        if (log.isTraceEnabled()) {
            log.trace("Delete user with id = " + user.getId());
        }
        return this.getJdbcTemplate().update("DELETE FROM \"user\" WHERE \"user\".id = ?;", user.getId());
    }

    @Override
    public int addRole(User user, Role role) {
        if ((user.getId() == null) &&(log.isDebugEnabled())) {
            log.warn("User: "+ user.getEmail() + " don`t have id");
            return 0;
        }
        return this.getJdbcTemplate().update("INSERT INTO \"user_role\"(id_user, id_role) VALUES (?,?)",
                user.getId(),role.getId(), role.getRoleName());
    }

    @Override
    public int deleteRole(User user, Role role) {
        if ((user.getId() == null) &&(log.isDebugEnabled())) {
            log.warn("User: "+ user.getEmail() + " don`t have id");
            return 0;
        }
        return this.getJdbcTemplate().update("DELETE FROM \"user_role\" WHERE id_user= ? AND " +
                "id_role = ?", user.getId(),role.getId());
    }

    private static final class UserExtractor implements ResultSetExtractor<User> {
        @Override
        public User extractData(ResultSet resultSet) throws SQLException {
            User user = new UserImpl();
            user.setId(resultSet.getLong("id"));
            user.setEmail(resultSet.getString("email"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setSecondName(resultSet.getString("second_name"));
            user.setPassword(resultSet.getString("password"));
            Set<Role> roles = new HashSet<>();
            roles.add(new RoleProxy(resultSet.getLong("id_role")));
            while (resultSet.next()) {
                roles.add(new RoleProxy(resultSet.getLong("id_role")));
            }
            user.setRoles(roles);
            return user;
        }
    }
}
