package edu.chalmers.axen2021.model.managers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import edu.chalmers.axen2021.model.projectdata.Project;
import edu.chalmers.axen2021.utils.StringUtils;
import edu.chalmers.axen2021.view.AXEN2021;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

/**
 * This class handles the making of a PDF file containing information about the chosen project.
 * @author Sam Salek
 * @author Malte Åkvist
 */
public class PdfManager {

    /**
     * The instance of this class.
     */
    private static PdfManager instance = null;

    /**
     * The document for the PDF.
     */
    private Document document = null;

    /**
     * The project to create the PDF from.
     */
    private Project project = null;

    /**
     * The file to save AKA path to the PDF.
     */
    private static File file;

    /**
     * Used to format the decimals in totalBOAPercent.
     */
    private final DecimalFormat dfPercent;

    // Fonts
    private static BaseFont arialBase;
    {
        try {
            arialBase = BaseFont.createFont(String.valueOf(getClass().getResource("/fonts/ArialCE.ttf")), BaseFont.WINANSI, true);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
    private static final Font arialSmall = new Font(arialBase, 11, Font.NORMAL);
    private static final Font arialSmallBold = new Font(arialBase, 11, Font.BOLD);
    private static final Font arialNormal = new Font(arialBase, 13, Font.NORMAL);
    private static final Font arialNormalBold = new Font(arialBase, 13, Font.BOLD);
    private static final Font arialBigBold = new Font(arialBase, 16, Font.BOLD);

    // Singleton. Use getInstance().
    private PdfManager(){
        // Init DecimalFormatter
        dfPercent = new DecimalFormat("#.###");
        dfPercent.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));
    }

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
            System.out.println("PDF of project \"" + project.getName() + "\" created at set directory!");
            return true;
        } else {
            System.out.println("PDF path not set! Aborting PDF creation!");
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
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File file = fileChooser.showSaveDialog(AXEN2021.getMainStage());
        //File file = new File(System.getProperty("user.home") + File.separatorChar + ".axen2021" + File.separatorChar + initialFileName + ".pdf");
        if(file != null) {
            PdfManager.file = file;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a PDF based on the needed content.
     */
    private void createPdf() {
        // Don't continue if file path has not been set.
        if(file == null) {
            throw new NullPointerException("File path for PDF has not been set!");
        }

        try {
            // Init the document
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // Add all information and content to the PDF
            addMetaData();
            addContent();

            // Close document after all content has ben added.
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds metadata to the PDF.
     */
    private void addMetaData() {
        document.addTitle(project.getName());
        document.addSubject("PDF export of project " + project.getName());
        document.addKeywords("Lejonstaden AB, PDF, " + project.getName());
        document.addAuthor("Lejonstaden AB");
        document.addCreator("Lejonstaden AB");
    }

    /**
     * Adds all content to the PDF.
     * @throws DocumentException
     */
    private void addContent() throws DocumentException {
        addLogo();
        addDate();
        addTitle();
        addGrundforutsattningar();
        addLagenhetsdata();
        addProjektkostnader();
        addFastighetsvardeOchResultat();
    }

    /**
     * Adds the logo to the PDF.
     */
    private void addLogo() {
        Image image;
        try {
            image = Image.getInstance(getClass().getResource("/images/fullLogoHD.png"));
            image.scalePercent(5);
            image.setAlignment(Element.ALIGN_LEFT | Image.TEXTWRAP);
            image.setAbsolutePosition(40, PageSize.A4.getHeight() - (image.getScaledHeight() + 10));
            document.add(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the date to the PDF.
     */
    private void addDate() {
        try {
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String datum = localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + localDate.getYear();
            Paragraph header = new Paragraph(new Chunk(datum, arialSmall));
            header.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(header);
            addEmptyLineToDocument(15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the title (project name) to the PDF.
     */
    private void addTitle() {
        try {
            Chunk chunk = new Chunk(project.getName(), arialBigBold);
            Paragraph title = new Paragraph(chunk);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            addEmptyLineToDocument(15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds all relevant data from "Grundforutsattningar" to the PDF.
     * @throws DocumentException
     */
    private void addGrundforutsattningar() throws DocumentException {
        Paragraph grundforutsattningar = new Paragraph();
        grundforutsattningar.add(new Paragraph("Grundförutsättningar:", arialNormalBold));
        addEmptyLineToParagraph(grundforutsattningar, 4);

        PdfPTable table = new PdfPTable(6);
        table.getDefaultCell().setPadding(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{17,25,28,20,22,20});

        table.addCell(createCell("Normhyra", arialSmall));
        table.addCell(createCell("Investeringsstöd", arialSmall));
        table.addCell(createCell("Antagen presumtions hyra", arialSmall));
        table.addCell(createCell("Oförutsett (%)", arialSmall));
        table.addCell(createCell("Total BOA", arialSmall));
        table.addCell(createCell("Total ljus BTA", arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(project.getNormhyraMedStod())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(String.valueOf(Math.round(project.getInvesteringsstod())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(String.valueOf(Math.round(project.getAntagenPresumtionshyra())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(dfPercent.format(project.getOforutsettPercent()), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(String.valueOf(Math.round(project.getTotalBoa())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(String.valueOf(Math.round(project.getTotalLjusBta())), arialSmall, Element.ALIGN_RIGHT));

        grundforutsattningar.add(table);
        document.add(grundforutsattningar);
        addEmptyLineToDocument(10);
    }

    /**
     * Adds all relevant data from "Lagenhetsdata" to the PDF.
     * @throws DocumentException
     */
    private void addLagenhetsdata() throws DocumentException {
        Paragraph lagenhetsdata = new Paragraph();
        lagenhetsdata.add(new Paragraph("Lägenhetsdata:", arialNormalBold));
        addEmptyLineToParagraph(lagenhetsdata, 4);

        PdfPTable table = new PdfPTable(10);
        table.getDefaultCell().setPadding(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{16,15,15,22,18,22,18,20,19,18});
        table.setHeaderRows(1);

        table.addCell(createCell("Typ", arialSmall));
        table.addCell(createCell("BOA", arialSmall));
        table.addCell(createCell("Antal", arialSmall));
        table.addCell(createCell("Hyra/mån låg", arialSmall));
        table.addCell(createCell("Kr/kvm låg", arialSmall));
        table.addCell(createCell("Hyra/mån hög", arialSmall));
        table.addCell(createCell("Kr/kvm hög", arialSmall));
        table.addCell(createCell("Total BOA", arialSmall));
        table.addCell(createCell("Total BOA (%)", arialSmall));
        table.addCell(createCell("Bidrag", arialSmall));

        // Loop through all apartment items and add a row with data for each.
        for(ApartmentItem apartmentItem : project.getApartmentItems()) {
            createLagenhetsCell(table, apartmentItem);
        }

        table.addCell(createCell("Totalt", arialSmall, Element.ALIGN_LEFT,2));
        table.addCell(createCell("", arialSmall, Element.ALIGN_RIGHT, 2));
        table.addCell(createCell(String.valueOf(Math.round(project.getNumOfApt())), arialSmall, Element.ALIGN_RIGHT, 2));
        table.addCell(createCell("", arialSmall, Element.ALIGN_RIGHT, 2));
        table.addCell(createCell(String.valueOf(Math.round(project.getTotalKrPerKvmLow())), arialSmall, Element.ALIGN_RIGHT, 2));
        table.addCell(createCell("", arialSmall, Element.ALIGN_RIGHT, 2));
        table.addCell(createCell(String.valueOf(Math.round(project.getTotalKrPerKvmHigh())), arialSmall, Element.ALIGN_RIGHT, 2));
        table.addCell(createCell(String.valueOf(Math.round(project.getTotalBoa())), arialSmall, Element.ALIGN_RIGHT, 2));
        table.addCell(createCell("100", arialSmall, Element.ALIGN_RIGHT, 2));
        table.addCell(createCell(String.valueOf(Math.round(project.getTotalBidrag())), arialSmall, Element.ALIGN_RIGHT, 2));

        lagenhetsdata.add(table);
        document.add(lagenhetsdata);
        addEmptyLineToDocument(10);
    }

    /**
     * Adds all relevant data from "Projektkostnader" to the PDF.
     * @throws DocumentException
     */
    private void addProjektkostnader() throws DocumentException {
        Paragraph projektkostnader = new Paragraph();
        projektkostnader.setKeepTogether(true);
        projektkostnader.add(new Paragraph("Projektkostnader:", arialNormalBold));
        addEmptyLineToParagraph(projektkostnader, 4);

        PdfPTable table = new PdfPTable(4);
        table.getDefaultCell().setPadding(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{20,20,20,20});

        table.addCell(createCell("", arialSmall));
        table.addCell(createCell("kkr", arialSmall));
        table.addCell(createCell("kr/BOA", arialSmall));
        table.addCell(createCell("kr/BTA", arialSmall));

        table.addCell(createCell("Tomtkostnader", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getTomtkostnaderKkr())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getTomtkostnaderKrBoa())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getTomtkostnaderKrBta())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Nedlagda byggherre", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getNedlagdaByggherreKkr())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getNedlagdaByggherreKrBoa())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getNedlagdaByggherreKrBta())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Anslutningar", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getAnslutningarKkr())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getAnslutningarKrBoa())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getAnslutningarKrBta())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Byggherrekostnader", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getByggherrekostnaderKkr())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getByggherrekostnaderKrBoa())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getByggherrekostnaderKrBta())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Finansiella kostnader", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getFinansiellaKostnaderKkr())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getFinansiellaKostnaderKrBoa())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getFinansiellaKostnaderKrBta())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Entrepenad", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getEntreprenadKkr())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getEntreprenadKrBoa())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getEntreprenadKrBta())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Oförutsett", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getOforutsettKkr())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getOforutsettKrBoa())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getOforutsettKrBta())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Mervärdeskatt", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getMervardeskattKkr())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getMervardeskattKrBoa())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getMervardeskattKrBta())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Investeringsstöd", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getInvesteringsstodKkr())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getInvesteringsstodKrBoa())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getInvesteringsstodKrBta())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Projektkostnad", arialSmall, Element.ALIGN_LEFT,2));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getProjektkostnadKkrMedStod())), arialSmall, Element.ALIGN_RIGHT, 2));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getProjektkostnadKrBoa())), arialSmall, Element.ALIGN_RIGHT, 2));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getProjektkostnadKrBta())), arialSmall, Element.ALIGN_RIGHT, 2));

        projektkostnader.add(table);
        document.add(projektkostnader);
        addEmptyLineToDocument(10);
    }

    /**
     * Adds all relevant data from "Fastighetsvarde Och Resultat" to the PDF.
     * @throws DocumentException
     */
    private void addFastighetsvardeOchResultat() throws DocumentException {
        Paragraph fastighetsvardeOchResultat = new Paragraph();
        fastighetsvardeOchResultat.setKeepTogether(true);
        fastighetsvardeOchResultat.add(new Paragraph("Fastighetsvärde och resultat:", arialNormalBold));
        addEmptyLineToParagraph(fastighetsvardeOchResultat, 4);

        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setPadding(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{20,20,20});

        table.addCell(createCell("", arialSmall));
        table.addCell(createCell("Med stöd", arialSmall));
        table.addCell(createCell("Utan stöd", arialSmall));

        table.addCell(createCell("Hyresintäkter", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getHyresintakterMedStod())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getHyresintakterUtanStod())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Drift & Underhåll", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getDriftUnderhallMedStod())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getDriftUnderhallUtanStod())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Tomträttsavgäld", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getTomtrattsavgaldMedStod())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getTomtrattsavgaldUtanStod())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Driftnetto", arialSmall));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getDriftnettoMedStod())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getDriftnettoUtanStod())), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Yield (%)", arialSmall));
        table.addCell(createCell(dfPercent.format(project.getYieldMedStod()), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(dfPercent.format(project.getYieldUtanStod()), arialSmall, Element.ALIGN_RIGHT));

        table.addCell(createCell("Marknadsvärde", arialSmall, Element.ALIGN_LEFT,2));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getMarknadsvardeMedStod())), arialSmall, Element.ALIGN_RIGHT, 2));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getMarknadsvardeUtanStod())), arialSmall, Element.ALIGN_RIGHT, 2));

        table.addCell(createCell("Projektvinst", arialSmallBold));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getProjektvinstMedStod())), arialSmallBold, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(project.getProjektvinstUtanStod())), arialSmallBold, Element.ALIGN_RIGHT));
        table.addCell(createCell("Projektvinst (%)", arialSmallBold));
        table.addCell(createCell(dfPercent.format(project.getProjektvinstProcentMedStod()), arialSmallBold, Element.ALIGN_RIGHT));
        table.addCell(createCell(dfPercent.format(project.getProjektvinstProcentUtanStod()), arialSmallBold, Element.ALIGN_RIGHT));

        fastighetsvardeOchResultat.add(table);
        document.add(fastighetsvardeOchResultat);
        addEmptyLineToDocument(10);
    }

    /**
     * Adds all data from an ApartmentItem to a given table as a new row.
     * @param table The table to add the data to.
     * @param apartmentItem The ApartmentItem to get the data from
     */
    private void createLagenhetsCell(PdfPTable table, ApartmentItem apartmentItem) {
        table.addCell(createCell(apartmentItem.getApartmentType(), arialSmall));
        table.addCell(createCell(String.valueOf(Math.round(apartmentItem.getBOA())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(String.valueOf(Math.round(apartmentItem.getAmount())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(apartmentItem.getRentPerMonthLow())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(apartmentItem.getKrPerKvmLow())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(apartmentItem.getRentPerMonthHigh())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(apartmentItem.getKrPerKvmHigh())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(apartmentItem.getTotalBOA())), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(dfPercent.format(apartmentItem.getTotalBOAPercent()), arialSmall, Element.ALIGN_RIGHT));
        table.addCell(createCell(StringUtils.separateKkr((double)Math.round(apartmentItem.getBidrag())), arialSmall, Element.ALIGN_RIGHT));
    }

    /**
     * Creates a new table cell.
     * @param text The text in the cell.
     * @param font Font for the text.
     * @return The newly created cell.
     */
    private PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        return cell;
    }

    /**
     * Creates a new table cell.
     * @param text The text in the cell.
     * @param font Font for the text.
     * @param alignment Alignment of the text.
     * @return The newly created cell.
     */
    private PdfPCell createCell(String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }

    /**
     * Creates a new table cell with a top-border.
     * @param text The text in the cell.
     * @param font Font for the text.
     * @param alignment Alignment of the text.
     * @param borderTop Size of the top-border.
     * @return The newly created cell.
     */
    private PdfPCell createCell(String text, Font font, int alignment, int borderTop) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderWidthTop(borderTop);
        return cell;
    }

    /**
     * Adds an empty line to the main document.
     * @param lineSize Size of the empty line.
     * @throws DocumentException
     */
    private void addEmptyLineToDocument(int lineSize) throws DocumentException {
        Paragraph emptyLine = new Paragraph();
        emptyLine.add(new Paragraph(" ", new Font(arialBase, lineSize, Font.NORMAL)));
        document.add(emptyLine);
    }

    /**
     * Adds an empty line to a given paragraph.
     * @param paragrapgh The paragraph to add the empty line to.
     * @param lineSize Size of the empty line.
     */
    private void addEmptyLineToParagraph(Paragraph paragrapgh, int lineSize) {
        paragrapgh.add(new Paragraph(" ", new Font(arialBase, lineSize, Font.NORMAL)));
    }
}
