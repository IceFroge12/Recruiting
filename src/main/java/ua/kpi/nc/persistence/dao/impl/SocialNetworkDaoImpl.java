package ua.kpi.nc.persistence.dao.impl;
import org.apache.log4j.Logger;
import ua.kpi.nc.persistence.dao.SocialNetworkDao;
import ua.kpi.nc.persistence.model.SocialNetwork;

/**
 * Created by IO on 16.04.2016.
 */
public class SocialNetworkDaoImpl extends JdbcDaoSupport implements SocialNetworkDao {

    private static Logger log = Logger.getLogger(SocialNetworkDaoImpl.class.getName());

    public SocialNetworkDaoImpl() {

    }

    @Override
    public SocialNetwork getByID(Long id) {
//        String sql = "SELECT * FROM \"social_network\" WHERE \"social.network\".id = " + id;
//        log.trace("Looking for social network with id = " + id);
//        SocialNetwork socialNetwork = null;
//        try(Connection connection = dataSource.getConnection()){
//            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet resultSet = statement.executeQuery();
//            log.trace("Open connection");
//            log.trace("Prepared statement creating");
//            log.trace("Get result set");
//            if (resultSet.next()){
//                log.trace("Create social network to return");
//            }
//        }
        return null;
    }
}
