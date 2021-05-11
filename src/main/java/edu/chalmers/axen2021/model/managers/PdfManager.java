package edu.chalmers.axen2021.model.managers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.scenario.effect.ImageData;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import edu.chalmers.axen2021.model.projectdata.Project;
import edu.chalmers.axen2021.view.AXEN2021;
import javafx.stage.FileChooser;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * This class handles the making of a PDF file containing information about the chosen project.
 * @author Sam Salek
 */
public class PdfManager {

    /**
     * The instance of this class.
     */
    private static PdfManager instance = null;

    /**
     * The document for pdf
     */
    private Document document = null;

    /**
     * The project to create pdf from
     */
    private Project project = null;

    /**
     * The file to save AKA path to the PDF.
     */
    private static File FILE;

    // Fonts
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    private static BaseFont base;

    {
        try {
            base = BaseFont.createFont(String.valueOf(getClass().getResource("/fonts/ArialCE.ttf")), BaseFont.WINANSI, true);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static Font arialSmall = new Font(base, 12, Font.NORMAL);
    private static Font arialNormal = new Font(base, 12, Font.NORMAL);
    private static Font arialNormalBold = new Font(base, 12, Font.BOLD);
    private static Font arialBigBold = new Font(base, 16, Font.BOLD);
    private static Font arialSmallBold = new Font(base, 12, Font.BOLD);

    // Singleton. Use getInstance().
    private PdfManager(){}

    /**
     * Returns the instance of this Singleton class.
     * @return Instance.
     */
    public static PdfManager getInstance() {
        if(instance == null) {
            instance = new PdfManager();
        }

        return instance;
    }

    /**
     * Starts the process of making a PDF.
     * @param project The project to create pdf from
     * @return Returns True if creation of the PDF was successful, False if not.
     */
    public boolean makePdf(Project project) {
        if(setSavePath(project.getName())) {
            this.project = project;
            createPdf();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets save path for the PDF file to be created. Returns False if a path wasn't set, and True if one was.
     * @param initialFileName The initial name of the PDF file when setting the path.
     * @return Returns False if a path wasn't set, and True if one was.
     */
    private boolean setSavePath(String initialFileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)","*.pdf"));
        fileChooser.setInitialFileName(initialFileName + ".pdf");

        File file = fileChooser.showSaveDialog(AXEN2021.getMainStage());
        //File file = new File(System.getProperty("user.home") + File.separatorChar + ".axen2021" + File.separatorChar + initialFileName + ".pdf");
        if(file != null) {
            System.out.println("PDF path set!");
            FILE = file;
            return true;
        } else {
            System.out.println("PDF path not set! Aborting!");
            return false;
        }
    }

    /**
     * Creates a PDF based on the information in the "add..." methods below.
     */
    private void createPdf() {
        // Don't continue if file path has not been set.
        if(FILE == null) {
            throw new NullPointerException("File path for PDF has not been set!");
        }

        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();

            // Add all content and information to the PDF
            addMetaData(document);
            addTitlePage(document);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private void addMetaData(Document document) {
        document.addTitle(project.getName());
        document.addSubject("Project");
        document.addKeywords("Lejonstaden AB, PDF");
        document.addAuthor("Lejonstaden AB");
        document.addCreator("Lejonstaden AB");
    }

    private void addTitlePage(Document document) throws DocumentException {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String datum = localDate.getMonthValue() + "/" + localDate.getDayOfMonth() + "/" + localDate.getYear();

        Image image = null;
        try {
            image = Image.getInstance(getClass().getResource("/images/lejonstadenLogga.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.scalePercent(36);
        image.setAlignment(Element.ALIGN_LEFT | Image.TEXTWRAP);
        image.setAbsolutePosition(30, PageSize.A4.getHeight() - image.getScaledHeight());

        document.add(image);

        Paragraph header = new Paragraph(new Chunk(datum, arialSmall));
        header.setAlignment(Paragraph.ALIGN_RIGHT);

        Chunk chunk = new Chunk(project.getName(), arialBigBold);
        Paragraph title = new Paragraph(chunk);
        addEmptyLine(title, 1);
        title.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(header);
        document.add(title);
        createGrundForutsattningar();
        createLagenhetsdata();
        createProjektKostnader();
        document.newPage();
        createFastighetsvardeOchResultat();
        document.newPage();
    }

    private void createProjektKostnader() throws DocumentException {
        Paragraph projektkostnader = new Paragraph("Projektkostnader:", arialNormalBold);
        addEmptyLine(projektkostnader, 1);

        PdfPTable table = new PdfPTable(4);
        table.getDefaultCell().setPadding(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{20,20,20,20});

        table.addCell(createCell("", arialSmall));
        table.addCell(createCell("kkr", arialSmall));
        table.addCell(createCell("kr/BOA", arialSmall));
        table.addCell(createCell("kr/BTA", arialSmall));

        table.addCell(createCell("Tomtkostnader", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getTomtkostnaderKkr())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getTomtkostnaderKrBoa())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getTomtkostnaderKrBta())), arialSmall));

        table.addCell(createCell("Nedlagda byggherre", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getNedlagdaByggherreKkr())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getNedlagdaByggherreKrBoa())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getNedlagdaByggherreKrBta())), arialSmall));

        table.addCell(createCell("Anslutningar", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getAnslutningarKkr())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getAnslutningarKrBoa())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getAnslutningarKrBta())), arialSmall));

        table.addCell(createCell("Byggherrekostnader", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getByggherrekostnaderKkr())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getByggherrekostnaderKrBoa())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getByggherrekostnaderKrBta())), arialSmall));

        table.addCell(createCell("Entrepenad", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getEntreprenadKkr())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getEntreprenadKrBoa())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getEntreprenadKrBta())), arialSmall));

        table.addCell(createCell("Oförutsett", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getOforutsettKkr())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getOforutsettKrBoa())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getOforutsettKrBta())), arialSmall));

        table.addCell(createCell("Finansiella kostnader", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getFinansiellaKostnaderKkr())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getFinansiellaKostnaderKrBoa())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getFinansiellaKostnaderKrBta())), arialSmall));

        table.addCell(createCell("Mervärdeskatt", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getMervardeskattKkr())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getMervardeskattKrBoa())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getMervardeskattKrBta())), arialSmall));

        table.addCell(createCell("Investeringsstöd", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getInvesteringsstodKkr())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getInvesteringsstodKrBoa())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getInvesteringsstodKrBta())), arialSmall));


        table.addCell(createCell("Projektkostnad", arialSmall, 2));
        table.addCell(createCell(String.valueOf(Math.round(project.getProjektkostnadKkr())), arialSmall, 2));
        table.addCell(createCell(String.valueOf(Math.round(project.getProjektkostnadKrBoa())), arialSmall, 2));
        table.addCell(createCell(String.valueOf(Math.round(project.getProjektkostnadKrBta())), arialSmall, 2));

        document.add(projektkostnader);
        document.add(table);
        addEmptyLines(1);
    }

    private void createFastighetsvardeOchResultat() throws DocumentException {
        Paragraph fastighetsvardeOchResultat = new Paragraph("Fastighetsvärde och resultat:", arialNormalBold);
        addEmptyLine(fastighetsvardeOchResultat, 1);

        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setPadding(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{20,20,20});

        table.addCell(createCell("", arialSmall));
        table.addCell(createCell("Med stöd", arialSmall));
        table.addCell(createCell("Utan stöd", arialSmall));

        table.addCell(createCell("Hyresintäkter", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getHyresintakterMedStod())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getHyresintakterUtanStod())), arialSmall));

        table.addCell(createCell("Drift & Underhåll", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getDriftUnderhallMedStod())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getDriftUnderhallUtanStod())), arialSmall));

        table.addCell(createCell("Tomträttsavgäld", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getTomtrattsavgaldMedStod())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getTomtrattsavgaldUtanStod())), arialSmall));

        table.addCell(createCell("Driftnetto", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getDriftnettoMedStod())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getDriftnettoUtanStod())), arialSmall));

        table.addCell(createCell("Yield", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getYieldMedStod())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getYieldUtanStod())), arialSmall));

        table.addCell(createCell("Marknadsvärde", arialSmall, 2));
        table.addCell(createCell(String.valueOf(Math.round(project.getMarknadsvardeMedStod())), arialSmall, 2));
        table.addCell(createCell(String.valueOf(Math.round(project.getMarknadsvardeUtanStod())), arialSmall, 2));

        table.addCell(createCell("Projektvinst", arialSmallBold));
        table.addCell(createCell(String.valueOf(Math.round(project.getProjektvinstMedStod())), arialSmallBold));
        table.addCell(createCell(String.valueOf(Math.round(project.getProjektvinstUtanStod())), arialSmallBold));
        table.addCell(createCell("", arialSmallBold));
        table.addCell(createCell(String.valueOf(Math.round(project.getProjektvinstProcentMedStod())), arialSmallBold));
        table.addCell(createCell(String.valueOf(Math.round(project.getProjektvinstProcentUtanStod())), arialSmallBold));

        document.add(fastighetsvardeOchResultat);
        document.add(table);
        addEmptyLines(1);
    }

    private void createLagenhetsdata() throws DocumentException {
        Paragraph lagenhetsdata = new Paragraph("Lägenhetsdata:", arialNormalBold);
        addEmptyLine(lagenhetsdata, 1);

        PdfPTable table = new PdfPTable(10);
        table.getDefaultCell().setPadding(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{22,15,15,22,18,22,18,15,19,18});

        table.addCell(createCell("Typ", arialSmall));
        table.addCell(createCell("BOA", arialSmall));
        table.addCell(createCell("Antal", arialSmall));
        table.addCell(createCell("Hyra/mån låg", arialSmall));
        table.addCell(createCell("Kr/kvm", arialSmall));
        table.addCell(createCell("Hyra/mån hög", arialSmall));
        table.addCell(createCell("Kr/kvm", arialSmall));
        table.addCell(createCell("BOA", arialSmall));
        table.addCell(createCell("Procent", arialSmall));
        table.addCell(createCell("Bidrag", arialSmall));

        for(ApartmentItem apartmentItem : project.getApartmentItems()) {
            createLagenhetsCell(table, apartmentItem);
        }

        table.addCell(createCell("Totalt", arialSmall, 2));
        table.addCell(createCell(String.valueOf(Math.round(project.getTotalBoa())), arialSmall, 2));
        table.addCell(createCell(String.valueOf(Math.round(project.getNumOfApt())), arialSmall, 2));
        table.addCell(createCell("TBD", arialSmall, 2));
        table.addCell(createCell("TBD", arialSmall, 2));
        table.addCell(createCell("TBD", arialSmall, 2));
        table.addCell(createCell("TBD", arialSmall, 2));
        table.addCell(createCell("TBD", arialSmall, 2));
        table.addCell(createCell("TBD", arialSmall, 2));
        table.addCell(createCell("TBD", arialSmall, 2));


        document.add(lagenhetsdata);
        document.add(table);
        addEmptyLines(1);
    }

    private void createLagenhetsCell(PdfPTable table, ApartmentItem apartmentItem) {
        table.addCell(createCell(apartmentItem.getApartmentType(), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(apartmentItem.getBOA())), arialSmall));
        table.addCell(createCell(String.valueOf(apartmentItem.getAmount()), arialSmall));
        table.addCell(createCell("TBD", arialSmall));
        table.addCell(createCell("TBD", arialSmall));
        table.addCell(createCell("TBD", arialSmall));
        table.addCell(createCell("TBD", arialSmall));
        table.addCell(createCell("TBD", arialSmall));
        table.addCell(createCell("TBD", arialSmall));
        table.addCell(createCell("TBD", arialSmall));
    }

    private void createGrundForutsattningar() throws DocumentException {
        Paragraph grundforutsattningar = new Paragraph("Grundförutsättningar:", arialNormalBold);
        addEmptyLine(grundforutsattningar, 1);

        PdfPTable table = new PdfPTable(6);
        table.getDefaultCell().setPadding(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{17,25,28,20,22,20});

        table.addCell(createCell("Normhyra", arialSmall));
        table.addCell(createCell("Investeringsstöd", arialSmall));
        table.addCell(createCell("Antagen presumtions hyra", arialSmall));
        table.addCell(createCell("Oförutsett %", arialSmall));
        table.addCell(createCell("Total BOA", arialSmall));
        table.addCell(createCell("Total ljus BTA", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getNormhyraMedStod())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getInvesteringsstod())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getAntagenPresumtionshyra())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getOforutsettPercent())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getTotalBoa())), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getTotalLjusBta())), arialSmall));

        document.add(grundforutsattningar);
        document.add(table);
        addEmptyLines(1);
    }

    private void addEmptyLines(int amountEmptyLines) throws DocumentException {
        Paragraph emptyLine = new Paragraph();
        addEmptyLine(emptyLine, amountEmptyLines);
        document.add(emptyLine);
    }

    private PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        return cell;
    }

    private PdfPCell createCell(String text, Font font, int borderTop) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setBorderWidthTop(borderTop);
        return cell;
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
