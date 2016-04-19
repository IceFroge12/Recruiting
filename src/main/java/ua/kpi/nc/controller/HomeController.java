package ua.kpi.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.domain.dao.DaoFactory.DAOFactory;
import ua.kpi.nc.domain.dao.DaoFactory.DaoFactoryType;
import ua.kpi.nc.domain.dao.impl.PostgreSQLImpl.SocialNetworkDAO;
import ua.kpi.nc.domain.model.SocialNetwork;
import ua.kpi.nc.service.UserService;

/**
 * Created by dima on 12.04.16.
 */
@Controller
public class HomeController{


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DaoFactoryType.PostgreSQL);
        SocialNetworkDAO<SocialNetwork, Long> socialNetworkDAO = daoFactory.getSocialNetworkDAO();
        SocialNetwork socialNetwork = socialNetworkDAO.getByTitle("FaceBook");
        SocialNetworkDAO<SocialNetwork, Long> socialNetworkDAO1 = daoFactory.getSocialNetworkDAO();
        SocialNetwork socialNetwork2 = socialNetworkDAO1.getByTitle("FaceBook");

        return modelAndView;
    }

}
