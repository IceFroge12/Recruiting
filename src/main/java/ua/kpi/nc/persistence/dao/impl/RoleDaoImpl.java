package ua.kpi.nc.persistence.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.RoleDao;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.proxy.UserProxy;
import ua.kpi.nc.persistence.model.impl.real.RoleImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

/**
 * Created by Chalienko on 13.04.2016.
 */

public class RoleDaoImpl extends JdbcDaoSupport implements RoleDao {

	private static Logger log = LoggerFactory.getLogger(RoleDaoImpl.class.getName());

	private static final String SQL_GET_BY_ID = "SELECT r.id, r.role" + "FROM \"role\" r\n" + "WHERE r.id = ?";

	private static final String SQL_GET_BY_TITLE = "SELECT r.id, r.role" + "FROM \"role\" r\n" + "WHERE r.role = ?";
	
	private static final String SQL_GET_ALL = "SELECT r.id, r.role" + "FROM \"role\" r";

	private static final String SQL_INSERT = "INSERT INTO role (role) VALUES (?)";

	private static final String SQL_UPDATE = "UPDATE role set role = ? WHERE role.id = ?";

	private static final String SQL_DELETE = "DELETE FROM \"role\" WHERE \"role\".id = ?";

	public RoleDaoImpl(DataSource dataSource) {
		this.setJdbcTemplate(new JdbcTemplate(dataSource));
	}

	@Override
	public Role getByID(Long id) {
		if (log.isTraceEnabled()) {
			log.trace("Looking for role with id = " + id);
		}
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, new RoleExtractor(), id);
	}

	@Override
	public Role getByTitle(String title) {
		if (log.isTraceEnabled()) {
			log.trace("Looking for role with title = " + title);
		}
		return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_TITLE, new RoleExtractor(), title);
	}

	private Set<User> getUsers(Long roleId) {
		return this.getJdbcTemplate().queryWithParameters("SELECT u.id\n" + "FROM \"user\" u\n"
				+ "INNER JOIN user_role ur on ur.id_user = u.id WHERE ur.id_role = ?;", resultSet -> {
					Set<User> set = new HashSet<>();
					do {
						set.add(new UserProxy(resultSet.getLong("id")));
					} while (resultSet.next());
					return set;
				}, roleId);
	}

	private final class RoleExtractor implements ResultSetExtractor<Role> {

		@Override
		public Role extractData(ResultSet resultSet) throws SQLException {
			Role role = new RoleImpl();
			long roleId = resultSet.getLong("id");
			role.setId(roleId);
			role.setRoleName(resultSet.getString("role"));
			role.setUsers(getUsers(roleId));
			return role;
		}

	}

	@Override
	public Long insertRole(Role role) {
		if (log.isTraceEnabled()) {
			log.trace("Inserting role with name = " + role.getRoleName());
		}
		return this.getJdbcTemplate().insert(SQL_INSERT, role.getRoleName());
	}

	@Override
	public int updateRole(Role role) {
		if (log.isTraceEnabled()) {
			log.trace("Updating role with id = " + role.getId());
		}
		return this.getJdbcTemplate().update(SQL_UPDATE, role.getRoleName(), role.getId());
	}

	@Override
	public int deleteRole(Role role) {
		if (log.isTraceEnabled()) {
			log.trace("Deleting role with id = " + role.getId());
		}
		return this.getJdbcTemplate().update(SQL_DELETE, role.getId());
	}

	@Override
	public Set<Role> getAll() {
		if (log.isTraceEnabled()) {
			log.trace("Getting all roles");
		}
		return this.getJdbcTemplate().queryForSet(SQL_GET_ALL, new RoleExtractor());
	}

}
