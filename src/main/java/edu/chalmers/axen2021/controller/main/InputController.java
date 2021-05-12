package edu.chalmers.axen2021.controller.main;

import edu.chalmers.axen2021.controller.FXMLController;
import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.controller.items.ApartmentItemController;
import edu.chalmers.axen2021.model.managers.SaveManager;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import edu.chalmers.axen2021.model.Category;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class for the applications inputView.fxml.
 * Handles all event triggered in the inputView.
 * @author Oscar Arvidson
 * @author Erik Wetter
 * @author Sam Salek
 * @author Malte Åkvist
 */
@FXMLController
public class InputController implements Initializable {

    //FXML fx:id for all the TextFields.
    @FXML private TextField tomtkostnaderKkr;
    @FXML private TextField tomtkostnaderKrBoa;
    @FXML private TextField tomtkostnaderKrBta;
    @FXML private TextField nedlagdaBygherreKkr;
    @FXML private TextField nedlagdaBygherreKrBoa;
    @FXML private TextField nedlagdaBygherreKrBta;
    @FXML private TextField anslutningarKkr;
    @FXML private TextField anslutningarKrBoa;
    @FXML private TextField anslutningarKrBta;
    @FXML private TextField byggherrekostnaderKkr;
    @FXML private TextField byggherrekostnaderKrBoa;
    @FXML private TextField byggherrekostnaderKrBta;
    @FXML private TextField entreprenadKkr;
    @FXML private TextField entreprenadKrBoa;
    @FXML private TextField entreprenadKrBta;
    @FXML private TextField oforutsettKkr;
    @FXML private TextField oforutsettKrBoa;
    @FXML private TextField oforutsettKrBta;
    @FXML private TextField finanisellaKostnaderKkr;
    @FXML private TextField finanisellaKostnaderKrBoa;
    @FXML private TextField finanisellaKostnaderKrBta;
    @FXML private TextField mervardeskattKkr;
    @FXML private TextField mervardeskattKrBoa;
    @FXML private TextField mervardeskattKrBta;
    @FXML private TextField investeringsstodKkr;
    @FXML private TextField investeringsstodKrBoa;
    @FXML private TextField investeringsstodKrBta;
    @FXML private TextField projektkostnadKkr;
    @FXML private TextField projektkostnadKrBoa;
    @FXML private TextField projektkostnadKrBta;

    @FXML private TextField hyresintakterMedStod;
    @FXML private TextField hyresintakterUtanStod;
    @FXML private TextField driftUnderhallMedStod;
    @FXML private TextField driftUnderhallUtanStod;
    @FXML private TextField tomtrattsavgaldMedStod;
    @FXML private TextField tomtrattsavgaldUtanStod;
    @FXML private TextField driftnettoMedStod;
    @FXML private TextField driftnettoUtanStod;
    @FXML private TextField yieldMedStod;
    @FXML private TextField yieldUtanStod;
    @FXML private TextField marknadsvardeMedStod;
    @FXML private TextField marknadsvardeUtanStod;

    @FXML private TextField investeringsstod;
    @FXML private TextField antagenPresumtionshyra;
    @FXML private TextField normHyraMedStod;
    @FXML private TextField totalLjusBta;
    @FXML private TextField totalBoa;
    @FXML private TextField oforutsett;

    @FXML private Label titleLabel;

    private DecimalFormat df;
    private DecimalFormat dfPercent;


    /**
     * Instance of the project manager.
     */
    private ProjectManager projectManager = ProjectManager.getInstance();

    /**
     * Parent controller
     */
    private RootController rootController = RootController.getInstance();

    /**
     * List of all input TextFields.
     */
    private ArrayList<TextField> inputFields = new ArrayList<TextField>();
    private ArrayList<TextField> inputFieldsPercent = new ArrayList<TextField>();

    /**
     * Vbox in the inputView containing lagenhetsDataSummaryItems.
     */
    @FXML private VBox lagenhetsTypVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        df = new DecimalFormat("#");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));

        dfPercent = new DecimalFormat("#.###");
        dfPercent.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));

        initInputFieldsList();
        initTextFieldProperties();
    }

    /**
     * Init method for input TextFields properties.
     * Adds focus lost property.
     * Adds only allowing doubles property.
     */
    private void initTextFieldProperties(){

        for(TextField textField: inputFields){

            //Adds property to TextField allowing users to only input numbers and ",".
            textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                if(!newValue.matches("[0-9]*" + "[,]?" + "[0-9]*")){
                    textField.setText(oldValue);
                }
            });

            //Adds focus lost property to textFields.
            textField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
                if(!newValue){
                    //Make sure that the textField has a readable value.
                    if(textField.getText().equals("") || textField.getText().equals(",")){
                        textField.setText("0");
                    }
                    setInputFields();
                    rootController.updateAllLabels();
                }
            });
        }
        for(TextField textField: inputFieldsPercent){

            //Adds property to TextField allowing users to only input numbers and ",".
            textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                if(!newValue.matches("((100)|[0-9]{0,2}([,][0-9]{0,3})?)[%]?")){
                    textField.setText(oldValue);
                }
            });

            //Adds focus lost property to textFields.
            textField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
                if(!newValue){
                    //Make sure that the textField has a readable value.
                    if(textField.getText().equals("") || textField.getText().equals(",")){
                        textField.setText("0");
                    }
                    setInputFields();
                    rootController.updateAllLabels();
                }
            });
        }
    }

    /**
     * Initialize list of all input TextFields.
     */
    private void initInputFieldsList(){
        inputFields.add(investeringsstod);
        inputFields.add(antagenPresumtionshyra);
        inputFields.add(normHyraMedStod);
        inputFields.add(totalLjusBta);
        inputFieldsPercent.add(yieldMedStod);
        inputFieldsPercent.add(yieldUtanStod);
        inputFieldsPercent.add(oforutsett);
    }

    /**
     * Updates title label of the page to the name of the current project.
     */
    public void updateTitle(){
        titleLabel.setText("Detaljer: " + projectManager.getActiveProject().getName());
    }

    /**
     * Change view to summaryView.
     */
    @FXML private void switchToSummaryView(){
        rootController.updateSummaryView();
    }

    /**
     * Method for opening the modalWindow.
     * Puts the modalWindowAnchorPane to front in scene.
     * @param event that triggered the method.
     */
    @FXML
    private void categoryButtonClicked(ActionEvent event) {
        Category category = Category.fromString(((Button)event.getSource()).getText());
        rootController.openModalWindow(category);
    }

    /**
     * Method for adding a new lagenhetsDataSummaryItem.
     * Adds lagenhetsDataSummaryItem to Vbox in inputView.
     * @param event that triggered the method.
     */
    @FXML
    private void addNewApartmentItem(ActionEvent event) {
        ApartmentItem newApartmentItem = projectManager.getActiveProject().addApartmentItem();
        createNewLagenhetstypView(newApartmentItem);
        updateAllTextFields();
        SaveManager.getInstance().saveProject(projectManager.getActiveProject());
    }

    /**
     * Populates the input view with apartmentItems views from the current project.
     */
    public void populateApartmentItems() {
        for (ApartmentItem apartmentItem : projectManager.getActiveProject().getApartmentItems()) {
            createNewLagenhetstypView(apartmentItem);
        }
    }

    /**
     * Remove all apartmentItems in the apartmentTypesView of the window.
     */
    public void clearApartmentItems() {
        lagenhetsTypVbox.getChildren().clear();
    }

    /**
     * Creates new apartment object and adds it to the view.
     * @param newApartmentItem The apartmentItem.
     */
    private void createNewLagenhetstypView(ApartmentItem newApartmentItem) {
        FXMLLoader apartmentTypeFXML = new FXMLLoader(getClass().getResource("/fxml/lagenhetsDataItem.fxml"));
        ApartmentItemController apartmentItemController = new ApartmentItemController(newApartmentItem);
        apartmentTypeFXML.setController(apartmentItemController);
        Node apartmentTypeNode = null;

        try {
            apartmentTypeNode = apartmentTypeFXML.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lagenhetsTypVbox.getChildren().add(0, apartmentTypeNode);
    }

    /**
     * Updates all TextFields.
     */
    public void updateAllTextFields(){
        updateGrundforutsattingar();
        updateTomtkostnader();
        updateNedlagadaByggherre();
        updateAnslutningar();
        updateByggherrekostnader();
        updateEntreprenad();
        updateOforutsett();
        updateFinansiellaKostnader();
        updateMervardeskatt();
        updateInvesteringsstod();
        updateProjektkostnad();
        updateFastighetsvardeOchResultat();
    }

    /**
     * Updates all TextFields related to Grundforutsattningar.
     */
    private void updateGrundforutsattingar(){
        normHyraMedStod.setText(df.format(projectManager.getActiveProject().getNormhyraMedStod()));
        investeringsstod.setText(df.format(projectManager.getActiveProject().getInvesteringsstod()));
        antagenPresumtionshyra.setText(df.format(projectManager.getActiveProject().getAntagenPresumtionshyra()));
        totalBoa.setText(df.format(projectManager.getActiveProject().getTotalBoa()));
        totalLjusBta.setText(df.format(projectManager.getActiveProject().getTotalLjusBta()));
        oforutsett.setText(dfPercent.format(projectManager.getActiveProject().getOforutsettPercent()) + "%");
    }

    /**
     * Update all TextFields related to Tomtkostnader.
     */
    private void updateTomtkostnader(){
        tomtkostnaderKkr.setText(df.format(projectManager.getActiveProject().getTomtkostnaderKkr()));
        tomtkostnaderKrBoa.setText(df.format(projectManager.getActiveProject().getTomtkostnaderKrBoa()));
        tomtkostnaderKrBta.setText(df.format(projectManager.getActiveProject().getTomtkostnaderKrBta()));
    }

    /**
     * Update all TextFields related to NedlagdaByggherre.
     */
    private void updateNedlagadaByggherre(){
        nedlagdaBygherreKkr.setText(df.format(projectManager.getActiveProject().getNedlagdaByggherreKkr()));
        nedlagdaBygherreKrBoa.setText(df.format(projectManager.getActiveProject().getNedlagdaByggherreKrBoa()));
        nedlagdaBygherreKrBta.setText(df.format(projectManager.getActiveProject().getNedlagdaByggherreKrBta()));
    }

    /**
     * Update all TextFields related to Ansultningar.
     */
    private void updateAnslutningar(){
        anslutningarKkr.setText(df.format(projectManager.getActiveProject().getAnslutningarKkr()));
        anslutningarKrBoa.setText(df.format(projectManager.getActiveProject().getAnslutningarKrBoa()));
        anslutningarKrBta.setText(df.format(projectManager.getActiveProject().getAnslutningarKrBta()));
    }

    /**
     * Update all TextFields related to Byggherrekostnader.
     */
    private void updateByggherrekostnader(){
        byggherrekostnaderKkr.setText(df.format(projectManager.getActiveProject().getByggherrekostnaderKkr()));
        byggherrekostnaderKrBoa.setText(df.format(projectManager.getActiveProject().getByggherrekostnaderKrBoa()));
        byggherrekostnaderKrBta.setText(df.format(projectManager.getActiveProject().getByggherrekostnaderKrBta()));
    }

    /**
     * Update all TextFields related to Entreprenad.
     */
    private void updateEntreprenad(){
        entreprenadKkr.setText(df.format(projectManager.getActiveProject().getEntreprenadKkr()));
        entreprenadKrBoa.setText(df.format(projectManager.getActiveProject().getEntreprenadKrBoa()));
        entreprenadKrBta.setText(df.format(projectManager.getActiveProject().getEntreprenadKrBta()));
    }

    /**
     * Update all TextFields related to Oforutsett.
     */
    private void updateOforutsett(){
        oforutsettKkr.setText(df.format(projectManager.getActiveProject().getOforutsettKkr()));
        oforutsettKrBoa.setText(df.format(projectManager.getActiveProject().getOforutsettKrBoa()));
        oforutsettKrBta.setText(df.format(projectManager.getActiveProject().getOforutsettKrBta()));
    }

    /**
     * Update all TextFields related to FinansiellaKostnader.
     */
    private void updateFinansiellaKostnader(){
        finanisellaKostnaderKkr.setText(df.format(projectManager.getActiveProject().getFinansiellaKostnaderKkr()));
        finanisellaKostnaderKrBoa.setText(df.format(projectManager.getActiveProject().getFinansiellaKostnaderKrBoa()));
        finanisellaKostnaderKrBta.setText(df.format(projectManager.getActiveProject().getFinansiellaKostnaderKrBta()));
    }

    /**
     * Update all TextFields related to Mervardeskatt.
     */
    private void updateMervardeskatt(){
        mervardeskattKkr.setText(df.format(projectManager.getActiveProject().getMervardeskattKkr()));
        mervardeskattKrBoa.setText(df.format(projectManager.getActiveProject().getMervardeskattKrBoa()));
        mervardeskattKrBta.setText(df.format(projectManager.getActiveProject().getMervardeskattKrBta()));
    }

    /**
     * Update all TextFields related to Investeringsstod.
     */
    private void updateInvesteringsstod(){
        investeringsstodKkr.setText(df.format(projectManager.getActiveProject().getInvesteringsstodKkr()));
        investeringsstodKrBoa.setText(df.format(projectManager.getActiveProject().getInvesteringsstodKrBoa()));
        investeringsstodKrBta.setText(df.format(projectManager.getActiveProject().getInvesteringsstodKrBta()));
    }

    /**
     * Update all TextFields related to Projektkostnad.
     */
    private void updateProjektkostnad(){
        projektkostnadKkr.setText(df.format(projectManager.getActiveProject().getProjektkostnadKkr()));
        projektkostnadKrBoa.setText(df.format(projectManager.getActiveProject().getProjektkostnadKrBoa()));
        projektkostnadKrBta.setText(df.format(projectManager.getActiveProject().getProjektkostnadKrBta()));
    }

    /**
     * Update all TextFields related to Fastighetsvarde and Resultat.
     */
    private void updateFastighetsvardeOchResultat(){
        hyresintakterMedStod.setText(df.format(projectManager.getActiveProject().getHyresintakterMedStod()));
        hyresintakterUtanStod.setText(df.format(projectManager.getActiveProject().getHyresintakterUtanStod()));
        driftUnderhallMedStod.setText(df.format(projectManager.getActiveProject().getDriftUnderhallMedStod()));
        driftUnderhallUtanStod.setText(df.format(projectManager.getActiveProject().getDriftUnderhallUtanStod()));
        tomtrattsavgaldMedStod.setText(df.format(projectManager.getActiveProject().getTomtrattsavgaldMedStod()));
        tomtrattsavgaldUtanStod.setText(df.format(projectManager.getActiveProject().getTomtrattsavgaldUtanStod()));
        driftnettoMedStod.setText(df.format(projectManager.getActiveProject().getDriftnettoMedStod()));
        driftnettoUtanStod.setText(df.format(projectManager.getActiveProject().getDriftnettoUtanStod()));
        yieldMedStod.setText(dfPercent.format(projectManager.getActiveProject().getYieldMedStod()) + "%");
        yieldUtanStod.setText(dfPercent.format(projectManager.getActiveProject().getYieldUtanStod()) + "%");
        marknadsvardeMedStod.setText(df.format(projectManager.getActiveProject().getMarknadsvardeMedStod()));
        marknadsvardeUtanStod.setText(df.format(projectManager.getActiveProject().getMarknadsvardeUtanStod()));
    }

    /**
     * Set all new values from the inputFields.
     */
    private void setInputFields(){
        projectManager.getActiveProject().setNormhyraMedStod(StringUtils.convertToDouble(normHyraMedStod.getText()));
        projectManager.getActiveProject().setInvesteringsstod(StringUtils.convertToDouble(investeringsstod.getText()));
        projectManager.getActiveProject().setAntagenPresumtionshyra(StringUtils.convertToDouble(antagenPresumtionshyra.getText()));
        projectManager.getActiveProject().setTotalLjusBta(StringUtils.convertToDouble(totalLjusBta.getText()));
        projectManager.getActiveProject().setYieldMedStod(StringUtils.convertToDouble(yieldMedStod.getText()));
        projectManager.getActiveProject().setYieldUtanStod(StringUtils.convertToDouble(yieldUtanStod.getText()));
        projectManager.getActiveProject().setOforutsettPercent(StringUtils.convertToDouble(oforutsett.getText()));
    }
}
