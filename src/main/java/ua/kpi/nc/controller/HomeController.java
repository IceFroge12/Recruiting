package ua.kpi.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.persistence.dao.DaoFactory;
import ua.kpi.nc.persistence.model.SocialNetwork;

/**
 * Created by dima on 12.04.16.
 */
@Controller
public class HomeController {

//
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public ModelAndView home() {
//        ModelAndView modelAndView = new ModelAndView("home");
//        DaoFactory daoFactory = DaoFactory.getDAOFactory(DaoFactoryType.PostgreSQL);
//        SocialNetworkDAO<SocialNetwork, Long> socialNetworkDAO = daoFactory.getSocialNetworkDAO();
//        SocialNetwork socialNetwork = socialNetworkDAO.getByTitle("FaceBook");
//        SocialNetworkDAO<SocialNetwork, Long> socialNetworkDAO1 = daoFactory.getSocialNetworkDAO();
//        SocialNetwork socialNetwork2 = socialNetworkDAO1.getByTitle("FaceBook");
//
//        return modelAndView;
//    }

}
