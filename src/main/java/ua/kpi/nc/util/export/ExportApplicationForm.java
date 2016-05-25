package ua.kpi.nc.util.export;

import com.itextpdf.text.DocumentException;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author Korzh
        */
public interface ExportApplicationForm {

    void export(ApplicationForm applicationForm, HttpServletResponse response ) throws IOException, DocumentException;
}
