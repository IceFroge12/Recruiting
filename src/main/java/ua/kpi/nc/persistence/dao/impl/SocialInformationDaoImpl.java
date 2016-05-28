package ua.kpi.nc.persistence.dao.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kpi.nc.persistence.dao.SocialInformationDao;
import ua.kpi.nc.persistence.model.SocialInformation;
import ua.kpi.nc.persistence.model.SocialNetwork;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.impl.proxy.UserProxy;
import ua.kpi.nc.persistence.model.impl.real.SocialInformationImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

/**
 * Created by Chalienko on 15.04.2016.
 */
public class SocialInformationDaoImpl extends JdbcDaoSupport implements SocialInformationDao {

    private static Logger log = LoggerFactory.getLogger(SocialInformationDaoImpl.class.getName());

    public SocialInformationDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    private ResultSetExtractor<SocialInformation> extractor = resultSet -> {
        long socialInformationId = resultSet.getLong("id");
        Timestamp writeTime = resultSet.getTimestamp("writetime");
        SocialInformation socialInformation;
        socialInformation = new SocialInformationImpl(socialInformationId,
                resultSet.getString("access_info"), new UserProxy(resultSet.getLong("id_user")),
                new SocialNetwork(resultSet.getLong("id_social_network"), resultSet.getString("title")), writeTime);
        return socialInformation;
    };

    private static final String SQL_GET_BY_ID = "SELECT si.id, si.id_social_network, si.id_user, si.access_info, sn.title, sn.writetime"
            + "FROM \"social_information \" si" + "\n INNER JOIN social_network sn ON sn.id = si.id_social_network "
            + "WHERE si.id = ?";

    private static final String SQL_GET_BY_USER_ID = "SELECT si.id, si.id_social_network, si.id_user, si.access_info, sn.title, sn.writetime"
            + "FROM \"social_information \" si" + "\n INNER JOIN social_network sn ON sn.id = si.id_social_network "
            + "WHERE si.id_user = ?";

    private static final String SQL_INSERT = "INSERT INTO social_information (access_info, id_user, id_social_network, writetime) VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE social_information set access_info = ? WHERE social_information.id = ?";

    private static final String SQL_DELETE = "DELETE FROM \"social_information\" WHERE \"social_information\".id = ?";

    private static final String SQL_GET_BY_USER_EMAIL_SOCIAL_TYPE = "SELECT si.id, si.id_social_network, si.id_user, si.access_info, sn.title, sn.writetime FROM public.user u JOIN public.social_information si ON u.id = si.id_user JOIN public.social_network sn ON si.id_social_network = sn.id WHERE u.email = ? AND si.id_social_network = ?;";

    @Override
    public SocialInformation getById(Long id) {
        log.trace("Looking for social information with id = ", id);
        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

    @Override
    public Set<SocialInformation> getByUserId(Long id) {
        log.trace("Looking for social informations with id_user = ", id);
        return this.getJdbcTemplate().queryForSet(SQL_GET_BY_USER_ID, extractor, id);
    }

    @Override
    public Long insertSocialInformation(SocialInformation socialInformation, User user, SocialNetwork socialNetwork, Timestamp writeTime) {
        log.trace("Inserting social information with id_user, id_social_network  = ", user.getId(),
                socialNetwork.getId());
        return this.getJdbcTemplate().insert(SQL_INSERT, socialInformation.getAccessInfo(), user.getId(),
                socialNetwork.getId(), writeTime);
    }

    @Override
    public int updateSocialInformation(SocialInformation socialInformation) {
        log.trace("Updating social_information with id = ", socialInformation.getId());
        return this.getJdbcTemplate().update(SQL_UPDATE, socialInformation.getAccessInfo(), socialInformation.getId());
    }

    @Override
    public int deleteSocialInformation(SocialInformation socialInformation) {
        log.trace("Deleting social information with id = ", socialInformation.getId());
        return this.getJdbcTemplate().update(SQL_DELETE, socialInformation.getId());
    }



//    @Override
//    public boolean isExist(String email, Long idSocialNetwork) {
//        log.trace("Search user social information by email = {}", email);
//        return this.getJdbcTemplate().queryWithParameters(SQL_GET_BY_USER_EMAIL_SOCIAL_TYPE, extractor, email, idSocialNetwork) != null;
//    }

    @Override
    public boolean isExist(String email, Long idSocialNetwork) {
        log.trace("Search user social information by email = {}", email);
        return getByUserEmailSocialType(email, idSocialNetwork) != null;
    }

    @Override
    public SocialInformation getByUserEmailSocialType(String email, Long idSocialType) {
        List<SocialInformation> list = this.getJdbcTemplate().queryForList(SQL_GET_BY_USER_EMAIL_SOCIAL_TYPE, extractor, email, idSocialType);
        if (list.size() == 0){
            return null;
        } else if (list.size() < 1){
            return list.iterator().next();
        } else {
            throw new  IllegalStateException("Data base error, more then one social information for one user for one social network");
        }
    }
}
