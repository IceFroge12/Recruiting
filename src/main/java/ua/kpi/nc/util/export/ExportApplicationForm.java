package ua.kpi.nc.util.export;

import ua.kpi.nc.persistence.model.User;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Korzh
 */
public interface ExportApplicationForm {

    void export(User user, HttpServletResponse response );
}
