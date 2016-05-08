package ua.kpi.nc.util.export;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.model.*;
import ua.kpi.nc.persistence.model.enums.FormQuestionTypeEnum;
import ua.kpi.nc.service.ApplicationFormService;
import ua.kpi.nc.service.FormAnswerService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

/**
 * @author Korzh
 */
public class ExportApplicationFormImp implements ExportApplicationForm {
    private final static int FONT_SIZE_BIG = 20;

    private UserService  userService = ServiceFactory.getUserService();
    private FormAnswerService formAnswerService = ServiceFactory.getFormAnswerService();
    public ExportApplicationFormImp() {

    }

    //private ApplicationFormService applicationFormService = ServiceFactory.getApplicationFormService();
    private static Logger log = LoggerFactory.getLogger(ExportApplicationFormImp.class.getName());
    @Override
    public void export(ApplicationForm applicationForm, HttpServletResponse response) throws Exception {

        User user  = userService.getUserByID(applicationForm.getUser().getId());

        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, FONT_SIZE_BIG, Font.BOLD);
        //Font for Checkboxes
        URL urlFont = Thread.currentThread().getContextClassLoader().getResource("fonts/wingding.ttf");
        BaseFont base = BaseFont.createFont(urlFont.getPath(), BaseFont.IDENTITY_H, false);
        Font font = new Font(base, 16f, Font.BOLD);
        char checked='\u00FE';
        char unchecked='\u00A8';

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        String appFormId = "# "+ applicationForm.getId();
        document.add(new Paragraph(appFormId, font1));
        document.add(new Paragraph(user.getLastName() + " " + user.getFirstName() + " " + user.getSecondName(), font1));
        PdfPTable table = new PdfPTable(3);
        insertBaseStudentData(table,user);
        /** Inserting Image in document **/
        URL url = Thread.currentThread().getContextClassLoader().getResource("photos/"+ applicationForm.getId() + ".jpg");
        insertImage(table,url);
        /** Image inserted**/
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(100);
        document.add(table);

        for (FormQuestion formQuestion : applicationForm.getQuestions()) {
            Paragraph paragraph = new Paragraph();
            if (formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.SELECT.getTitle())
                    || formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.RADIO.getTitle())) {
                for (FormAnswerVariant formAnswerVariant : formQuestion.getFormAnswerVariants()) {
                    for (FormAnswer answer : formAnswerService.getByApplicationFormAndQuestion(applicationForm, formQuestion)) {
                        if (String.valueOf(formAnswerVariant.getAnswer()).equals(String.valueOf(answer.getFormAnswerVariant().getAnswer()))) {
                            Phrase phrase = new Phrase(formQuestion.getTitle());
                            phrase.add(": " + answer.getFormAnswerVariant().getAnswer());
                            paragraph.add(phrase);
                        }
                    }
                }
            }
            if (formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.INPUT.getTitle())
                    || formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.TEXTAREA.getTitle())){
                for (FormAnswer answer : formAnswerService.getByApplicationFormAndQuestion(applicationForm, formQuestion)) {
                    Phrase phrase = new Phrase(formQuestion.getTitle());
                    phrase.add(": " + answer.getAnswer());
                    paragraph.add(phrase);
                }
            }
            if (formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.CHECKBOX.getTitle())) {
                Phrase phrase = new Phrase(formQuestion.getTitle() + ": ");
                paragraph.add(phrase);
                for (FormAnswerVariant formAnswerVariant : formQuestion.getFormAnswerVariants()) {
                    boolean flag = false;
                    for (FormAnswer answer : formAnswerService.getByApplicationFormAndQuestion(applicationForm, formQuestion)) {
                        if (String.valueOf(formAnswerVariant.getAnswer()).equals(String.valueOf(answer.getFormAnswerVariant().getAnswer()))) {
                        flag = true;
                        }
                    }
                   if (flag){
                       Phrase phraseCheck = new Phrase(String.valueOf(checked),font);
                       paragraph.add(phraseCheck);
                   }else{
                       Phrase phraseUnCheck = new Phrase(String.valueOf(unchecked),font);
                       paragraph.add(phraseUnCheck);
                   }
                    Phrase variant = new Phrase(formAnswerVariant.getAnswer());
                    paragraph.add(variant);
                }
            }
//            if (formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.INPUT.getTitle())
//                    || formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.TEXTAREA.getTitle())){
//
//            }

//            for (FormAnswerVariant variant : formQuestion.getFormAnswerVariants()){
//                if(formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.SELECT.getTitle()))
//                paragraph.add(new Phrase(variant.getAnswer()));
//            }
            document.add(paragraph);
        }
        document.close();
        writer.close();
    }

    private void insertBaseStudentData(PdfPTable baseTable, User user) {
        /** Left table**/
        PdfPTable innerTable1 = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Phrase("Surname:"));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        innerTable1.addCell(cell);
        cell.setPhrase(new Phrase("First Name:"));
        innerTable1.addCell(cell);
        cell.setPhrase(new Phrase("Second Name:"));
        innerTable1.addCell(cell);
        cell.setPhrase(new Phrase("Email:"));
        innerTable1.addCell(cell);
        PdfPCell cellTableLeft = new PdfPCell(innerTable1);
        cellTableLeft.setBorder(PdfPCell.NO_BORDER);
        baseTable.addCell(cellTableLeft);
        /** Right table**/
        PdfPTable innerTable2 = new PdfPTable(1);
        cell.setPhrase(new Phrase( user.getLastName()));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        innerTable2.addCell(cell);
        cell.setPhrase(new Phrase(user.getFirstName()));
        innerTable2.addCell(cell);
        cell.setPhrase(new Phrase(user.getSecondName()));
        innerTable2.addCell(cell);
        cell.setPhrase(new Phrase(user.getEmail()));
        innerTable2.addCell(cell);
        PdfPCell cellTableRight = new PdfPCell(innerTable2);
        cellTableRight.setBorder(PdfPCell.NO_BORDER);
        baseTable.addCell(cellTableRight);
    }

    private void insertImage(PdfPTable table, URL url) throws Exception {
        try {
            PdfPCell cellImg ;
            cellImg = new PdfPCell(getImage(url));
            cellImg.setBorder(PdfPCell.NO_BORDER);
            cellImg.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellImg);
        } catch (IOException | BadElementException e) {
            log.error("Error while inserting Student Image in Application Form");
            e.printStackTrace();
            throw new Exception();
        }

    }
    private Image getImage(URL url) throws IOException, BadElementException {
            Image image;
            image = Image.getInstance(url);
            image.setAlignment(Image.ALIGN_RIGHT|Image.TEXTWRAP);
            image.scaleAbsolute(105, 140);
            return image;
    }
}
