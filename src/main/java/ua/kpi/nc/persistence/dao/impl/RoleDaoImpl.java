package ua.kpi.nc.persistence.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.RoleDao;
import ua.kpi.nc.persistence.model.ApplicationForm;
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

    private ResultSetExtractor<Role> extractor = resultSet -> {
        Role role = new RoleImpl();
        long roleId = resultSet.getLong("id");
        role.setId(roleId);
        role.setRoleName(resultSet.getString("role"));
        role.setUsers(getUsers(roleId));
        return role;
    };

    private static Logger log = LoggerFactory.getLogger(RoleDaoImpl.class.getName());

    private static final String SQL_GET_BY_ID = "SELECT id, role FROM role WHERE id = ?;";

    private static final String SQL_GET_BY_TITLE = "SELECT r.id, r.role\n FROM \"role\" r\n WHERE r.role = ?";

    private static final String SQL_GET_ALL = "SELECT r.id, r.role\n FROM \"role\" r";

    private static final String SQL_INSERT = "INSERT INTO role (role) VALUES (?)";

    private static final String SQL_UPDATE = "UPDATE role set role = ? WHERE role.id = ?";

    private static final String SQL_DELETE = "DELETE FROM \"role\" WHERE \"role\".id = ?";
    
	private static final String SQL_GET_POSSIBLE_INTERVIEWS = "SELECT r.id, r.role FROM \"role\" r "
			+ " INNER JOIN user_role ur ON ur.id_role = r.id " + " WHERE r.id IN (2,5) AND "
			+ "  ur.id_user = ? AND " + " r.id NOT IN "
			+ "(SELECT i.interviewer_role  FROM interview i WHERE i.id_application_form = ?)";

    public RoleDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public Role getByID(Long id) {
        log.trace("Looking for role with id = ", id);
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

    @Override
    public Role getByTitle(String title) {
        log.trace("Looking for role with title = ", title);
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_TITLE, extractor, title);
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

    @Override
    public Long insertRole(Role role) {
        log.trace("Inserting role with name = ", role.getRoleName());
        return this.getJdbcTemplate().insert(SQL_INSERT, role.getRoleName());
    }

    @Override
    public int updateRole(Role role) {
        log.trace("Updating role with id = ", role.getId());
        return this.getJdbcTemplate().update(SQL_UPDATE, role.getRoleName(), role.getId());
    }

    @Override
    public int deleteRole(Role role) {
        log.trace("Deleting role with id = ", role.getId());
        return this.getJdbcTemplate().update(SQL_DELETE, role.getId());
    }

    @Override
    public Set<Role> getAll() {
        log.trace("Getting all roles");
        return this.getJdbcTemplate().queryForSet(SQL_GET_ALL, extractor);
    }

	@Override
	public List<Role> getPossibleInterviewsRoles(ApplicationForm applicationForm, User interviewer) {
		log.trace("Looking for possible interviews roles for application form with id = {} and interviewer with id = {}", applicationForm.getId(), interviewer.getId());
		return this.getJdbcTemplate().queryForList(SQL_GET_POSSIBLE_INTERVIEWS, extractor, interviewer.getId(), applicationForm.getId());
	}
}
