package ua.kpi.nc.controller.staff;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by uakruk on 5/2/16.
 * Staff main page controller
 */
@Controller
@RequestMapping("/staff")
public class StaffMainController {

    // TODO make ajax js for Staff
    // TODO move it to a seperate package
    @ModelAttribute("showAdminMenu")
    public boolean getShhowAdminMenu() {
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN"))
                return true;
        }
        return false;
    }

    /**
     * main page
     * @return main page
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView staffPage() {
        ModelAndView response = new ModelAndView("staff_main");
        return response;
    }


}
