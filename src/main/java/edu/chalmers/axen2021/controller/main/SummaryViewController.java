package edu.chalmers.axen2021.controller.main;

import edu.chalmers.axen2021.controller.FXMLController;
import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.controller.items.ApartmentItemController;
import edu.chalmers.axen2021.controller.items.ApartmentItemControllerFactory;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.model.managers.SaveManager;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import edu.chalmers.axen2021.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class for the applications summaryView.fxml.
 * Handles all event triggered in the SummaryView.
 * @author Erik Wetter
 * @author Oscar Arvidson
 * @author Sam Salek
 * @author Malte Åkvist
 */
@FXMLController
public class SummaryViewController implements Initializable {

    /**
     * Instance of Project Manager.
     */
    private final ProjectManager projectManager = ProjectManager.getInstance();

    /**
     * Instance of parent controller.
     */
    private final RootController rootController = RootController.getInstance();

    //Variables connected to text fields in summaryView.fxml
    @FXML private TextField projektkostnadMedStod;
    @FXML private TextField projektkostnadUtanStod;
    @FXML private TextField marknadsvardeMedStod;
    @FXML private TextField marknadsvardeUtanStod;
    @FXML private TextField projektvinstMedStod;
    @FXML private TextField projektvinstUtanStod;
    @FXML private TextField projektvinstProcentMedStod;
    @FXML private TextField projektvinstProcentUtanStod;

    @FXML private Label titleLabel;

    /**
     * Decimal formatter removing decimals.
     */
    private DecimalFormat df;

    /**
     * Decimal formatter for percent labels
     */
    private DecimalFormat dfPercent;

    /**
     * VBox in the SummaryView.
     */
    @FXML private VBox lagenhetsTypVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        df = new DecimalFormat("#");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));
        dfPercent = new DecimalFormat("#.###");
        dfPercent.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));
    }

    /**
     * Updates title on the page.
     */
    public void updateTitle(){
        titleLabel.setText("Sammanfattning: " + projectManager.getActiveProject().getName());
    }

    /**
     * Switches view to InputView.
     */
    @FXML private void switchToInputView(){
        rootController.updateInputView();
    }

    /**
     * Updates all TextFields.
     */
    public void updateTextFields() {
        updateProjektkostnad();
        updateMarknadsvarde();
        updateProjektvinst();
        updateProjektvinstProcent();
    }

    /**
     * Updates all TextFields related to Projektkostnad.
     */
    private void updateProjektkostnad() {
        projektkostnadMedStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getProjektkostnadKkrMedStod()));
        projektkostnadUtanStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getProjektkostnadKkr()));
    }

    /**
     * Updates all TextFields related to Marknadsvarde.
     */
    private void updateMarknadsvarde() {
        marknadsvardeMedStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getMarknadsvardeMedStod()));
        marknadsvardeUtanStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getMarknadsvardeUtanStod()));
    }

    /**
     * Updates all TextFields related to Projektvinst.
     */
    private void updateProjektvinst() {
        projektvinstMedStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getProjektvinstMedStod()));
        projektvinstUtanStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getProjektvinstUtanStod()));
    }

    /**
     * Updates all TextFields related to ProjektvinstProcent.
     */
    private void updateProjektvinstProcent() {
        projektvinstProcentMedStod.setText(dfPercent.format(projectManager.getActiveProject().getProjektvinstProcentMedStod()) + "%");
        projektvinstProcentUtanStod.setText(dfPercent.format(projectManager.getActiveProject().getProjektvinstProcentUtanStod()) + "%");
    }

    /**
     * Method for addNewLagenhetstyp button clicked.
     * Adds a new LagenhetsDataSummaryItem to the VBox.
     * @param event of action.
     */
    @FXML
    private void addNewApartmentItem(ActionEvent event) {
        ApartmentItem newApartmentItem = projectManager.getActiveProject().addApartmentItem();
        createNewApartmentItemView(newApartmentItem);
        SaveManager.getInstance().saveProject(projectManager.getActiveProject());
    }

    /**
     * Populates apartmentItems for the active project.
     */
    public void populateApartmentItems() {
        for (ApartmentItem apartmentItem : projectManager.getActiveProject().getApartmentItems()) {
            createNewApartmentItemView(apartmentItem);
        }
    }

    /**
     * Remove all apartmentItems from view.
     */
    public void clearApartmentItems() {
        ApartmentItemControllerFactory.clearInstances();
        lagenhetsTypVbox.getChildren().clear();
    }

    /**
     * Creates new lagenhetsDataItem and adds it to the current view.
     * @param newApartmentItem
     */
    private void createNewApartmentItemView(ApartmentItem newApartmentItem) {
        FXMLLoader apartmentTypeFXML = new FXMLLoader(getClass().getResource("/fxml/lagenhetsDataItem.fxml"));
        ApartmentItemController apartmentItemController = ApartmentItemControllerFactory.create(newApartmentItem);
        apartmentTypeFXML.setController(apartmentItemController);
        Node apartmentTypeNode = null;

        try {
            apartmentTypeNode = apartmentTypeFXML.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lagenhetsTypVbox.getChildren().add(0, apartmentTypeNode);
    }
}
