package ua.kpi.nc.util.export;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ua.kpi.nc.persistence.model.ApplicationForm;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author Korzh
 */
public class ExportapplicationformImpl implements ExportApplicationForm {
    static int FONT_SIZE_BIG = 20;
    //Temporary Hardcoding
    static String IMAGE_PATH = "C:\\Users\\Vova\\IdeaProjects\\Recruting13\\src\\main\\webapp\\frontend\\module\\student\\photoscope\\155\\DSC_0000.jpg";
    private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();

    @Override
    public void export(User user, HttpServletResponse response) {
        try {
            Document document = new Document();
            ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(user.getId());
            Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, FONT_SIZE_BIG, Font.BOLD);
            PdfWriter writer = null;
            writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            String appFormId = "# " + user.getId();
            document.add(new Paragraph(appFormId, font1));
            document.add(new Paragraph(user.getLastName() + " " + user.getFirstName() + " " + user.getSecondName(), font1));
            insertImage(document);
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertImage(Document document) {
        try {
            Image image;
            image = Image.getInstance(IMAGE_PATH);
            image.setAlignment(Image.ALIGN_RIGHT);
            image.scaleAbsolute(150, 200);
            document.add(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
