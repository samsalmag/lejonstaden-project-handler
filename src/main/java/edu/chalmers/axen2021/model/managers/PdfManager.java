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

    private static Font arialSmall = new Font(base, 11, Font.NORMAL);
    private static Font arialNormal = new Font(base, 12, Font.NORMAL);
    private static Font arialNormalBold = new Font(base, 12, Font.BOLD);
    private static Font arialBigBold = new Font(base, 16, Font.BOLD);

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
     * @param initialFileName The initial name that is displayed when choosing the save directory.
     * @return Returns True if creation of the PDF was successful, False if not.
     */
    public boolean makePdf(String initialFileName) {
        if(setSavePath(initialFileName)) {
            project = ProjectManager.getInstance().getActiveProject();
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

        //File file = fileChooser.showSaveDialog(AXEN2021.getMainStage());
        File file = new File(System.getProperty("user.home") + File.separatorChar + ".axen2021" + File.separatorChar + initialFileName + ".pdf");
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

        table.addCell("");
        /*PdfPCell emptyCell = new PdfPCell();
        emptyCell.setPadding(5);
        emptyCell.setBorderWidthTop(0);
        emptyCell.setBorderWidthLeft(0);
        table.addCell(emptyCell);*/
        table.addCell("kkr");
        table.addCell("kr/BOA");
        table.addCell("kr/BTA");
        table.addCell("Tomtkostnader");
        table.addCell(String.valueOf(Math.round(project.getTomtkostnaderKkr())));
        table.addCell(String.valueOf(Math.round(project.getTomtkostnaderKrBoa())));
        table.addCell(String.valueOf(Math.round(project.getTomtkostnaderKrBta())));

        table.addCell("Nedlagda byggherre");
        table.addCell(String.valueOf(Math.round(project.getNedlagdaByggherreKkr())));
        table.addCell(String.valueOf(Math.round(project.getNedlagdaByggherreKrBoa())));
        table.addCell(String.valueOf(Math.round(project.getNedlagdaByggherreKrBta())));

        table.addCell("Anslutningar");
        table.addCell(String.valueOf(Math.round(project.getAnslutningarKkr())));
        table.addCell(String.valueOf(Math.round(project.getAnslutningarKrBoa())));
        table.addCell(String.valueOf(Math.round(project.getAnslutningarKrBta())));

        table.addCell("Byggherrekostnader");
        table.addCell(String.valueOf(Math.round(project.getByggherrekostnaderKkr())));
        table.addCell(String.valueOf(Math.round(project.getByggherrekostnaderKrBoa())));
        table.addCell(String.valueOf(Math.round(project.getByggherrekostnaderKrBta())));

        table.addCell("Entrepenad");
        table.addCell(String.valueOf(Math.round(project.getEntreprenadKkr())));
        table.addCell(String.valueOf(Math.round(project.getEntreprenadKrBoa())));
        table.addCell(String.valueOf(Math.round(project.getEntreprenadKrBta())));

        table.addCell("Oförutsett");
        table.addCell(String.valueOf(Math.round(project.getOforutsettKkr())));
        table.addCell(String.valueOf(Math.round(project.getOforutsettKrBoa())));
        table.addCell(String.valueOf(Math.round(project.getOforutsettKrBta())));

        table.addCell("Finansiella kostnader");
        table.addCell(String.valueOf(Math.round(project.getFinansiellaKostnaderKkr())));
        table.addCell(String.valueOf(Math.round(project.getFinansiellaKostnaderKrBoa())));
        table.addCell(String.valueOf(Math.round(project.getFinansiellaKostnaderKrBta())));

        table.addCell("Mervärdeskatt");
        table.addCell(String.valueOf(Math.round(project.getMervardeskattKkr())));
        table.addCell(String.valueOf(Math.round(project.getMervardeskattKrBoa())));
        table.addCell(String.valueOf(Math.round(project.getMervardeskattKrBta())));

        table.addCell("Investeringsstöd");
        table.addCell(String.valueOf(Math.round(project.getInvesteringsstodKkr())));
        table.addCell(String.valueOf(Math.round(project.getInvesteringsstodKrBoa())));
        table.addCell(String.valueOf(Math.round(project.getInvesteringsstodKrBta())));

        //table.getDefaultCell().setBorderWidthTop(2);
        //table.getDefaultCell().setBorderWidthBottom(2);
        table.getDefaultCell().setBorderWidthTop(2);

        /*PdfPCell cell1 = new PdfPCell(new Paragraph("Projektkostnad"));
        cell1.setPadding(5);
        cell1.setBorderWidthLeft(2);
        cell1.setBorderWidthTop(2);
        cell1.setBorderWidthBottom(2);
        table.addCell(cell1);*/

        table.addCell("Projektkostnad");
        table.addCell(String.valueOf(Math.round(project.getProjektkostnadKkr())));
        table.addCell(String.valueOf(Math.round(project.getProjektkostnadKrBoa())));
        table.addCell(String.valueOf(Math.round(project.getProjektkostnadKrBta())));

        /*PdfPCell cell2 = new PdfPCell(new Paragraph(String.valueOf(Math.round(project.getProjektkostnadKrBta()))));
        cell2.setPadding(5);
        cell2.setBorderWidthTop(2);
        cell2.setBorderWidthRight(2);
        cell2.setBorderWidthBottom(2);
        table.addCell(cell2);*/

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


        table.addCell("");
        table.addCell("Med stöd");
        table.addCell("Utan stöd");

        table.addCell("Hyresintäkter");
        table.addCell(String.valueOf(Math.round(project.getHyresintakterMedStod())));
        table.addCell(String.valueOf(Math.round(project.getHyresintakterUtanStod())));

        table.addCell("Drift & Underhåll");
        table.addCell(String.valueOf(Math.round(project.getDriftUnderhallMedStod())));
        table.addCell(String.valueOf(Math.round(project.getDriftUnderhallUtanStod())));

        table.addCell("Tomträttsavgäld");
        table.addCell(String.valueOf(Math.round(project.getTomtrattsavgaldMedStod())));
        table.addCell(String.valueOf(Math.round(project.getTomtrattsavgaldUtanStod())));


        table.addCell("Driftnetto");
        table.addCell(String.valueOf(Math.round(project.getDriftnettoMedStod())));
        table.addCell(String.valueOf(Math.round(project.getDriftnettoUtanStod())));

        table.addCell("Yield");
        table.addCell(String.valueOf(Math.round(project.getYieldMedStod())));
        table.addCell(String.valueOf(Math.round(project.getYieldUtanStod())));

        table.getDefaultCell().setBorderWidthTop(2);
        table.addCell(new Paragraph("Marknadsvärde", arialNormal));
        table.addCell(new Paragraph(String.valueOf(Math.round(project.getMarknadsvardeMedStod())), arialNormal));
        table.addCell(new Paragraph(String.valueOf(Math.round(project.getMarknadsvardeUtanStod())), arialNormal) );

        table.getDefaultCell().setBorderWidthTop(0);
        table.addCell(new Paragraph("Projektvinst", arialNormalBold));
        table.addCell(new Paragraph(String.valueOf(Math.round(project.getProjektvinstMedStod())), arialNormalBold));
        table.addCell(new Paragraph(String.valueOf(Math.round(project.getProjektvinstUtanStod())), arialNormalBold));
        table.addCell("");
        table.addCell(new Paragraph(String.valueOf(Math.round(project.getProjektvinstProcentMedStod())), arialNormalBold));
        table.addCell(new Paragraph(String.valueOf(Math.round(project.getProjektvinstProcentUtanStod())), arialNormalBold));

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

        table.addCell("Typ");
        table.addCell("BOA");
        table.addCell("Antal");
        table.addCell("Hyra/mån låg");
        table.addCell("Kr/kvm");
        table.addCell("Hyra/mån hög");
        table.addCell("Kr/kvm");
        table.addCell("BOA");
        table.addCell("Procent");
        table.addCell("Bidrag");

        for(ApartmentItem apartmentItem : project.getApartmentItems()) {
            createLagenhetsCell(table, apartmentItem);
        }

        table.getDefaultCell().setBorderWidthTop(2);
        table.addCell("Totalt");
        table.addCell(String.valueOf(Math.round(project.getTotalBoa())));
        table.addCell(String.valueOf(Math.round(project.getNumOfApt())));
        table.addCell("TBD");
        table.addCell("TBD");
        table.addCell("TBD");
        table.addCell("TBD");
        table.addCell("TBD");
        table.addCell("TBD");
        table.addCell("TBD");

        document.add(lagenhetsdata);
        document.add(table);
        addEmptyLines(1);
    }

    private void createLagenhetsCell(PdfPTable table, ApartmentItem apartmentItem) {
        table.addCell(apartmentItem.getApartmentType());
        table.addCell(String.valueOf(Math.round(apartmentItem.getBOA())));
        table.addCell(String.valueOf(apartmentItem.getAmount()));
        table.addCell("TBD");
        table.addCell("TBD");
        table.addCell("TBD");
        table.addCell("TBD");
        table.addCell("TBD");
        table.addCell("TBD");
        table.addCell("TBD");
    }

    private void createGrundForutsattningar() throws DocumentException {
        Paragraph grundforutsattningar = new Paragraph("Grundförutsättningar:", arialNormalBold);
        addEmptyLine(grundforutsattningar, 1);

        PdfPTable table = new PdfPTable(6);
        table.getDefaultCell().setPadding(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{17,25,28,20,22,20});

        table.addCell("Normhyra");
        table.addCell("Investeringsstöd");
        table.addCell("Antagen presumtions hyra");
        table.addCell("Oförutsett %");
        table.addCell("Total BOA");
        table.addCell("Total ljus BTA");
        table.addCell(String.valueOf(Math.round(project.getNormhyraMedStod())));
        table.addCell(String.valueOf(Math.round(project.getInvesteringsstod())));
        table.addCell(String.valueOf(Math.round(project.getAntagenPresumtionshyra())));
        table.addCell(String.valueOf(Math.round(project.getOforutsettPercent())));
        table.addCell(String.valueOf(Math.round(project.getTotalBoa())));
        table.addCell(String.valueOf(Math.round(project.getTotalLjusBta())));

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
        return cell;
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
