package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.Category;
import edu.chalmers.axen2021.model.ProjectManager;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller class for the applications inputView.fxml.
 * Handles all event triggered in the inputView.
 * @author Oscar Arvidson
 * @author Erik Wetter
 */
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

    @FXML private Label titleLabel;

    /**
     * Instance of model.
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

    /**
     * Vbox in the inputView containing lagenhetsDataSummaryItems.
     */
    @FXML private VBox lagenhetsTypVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initInputFieldsList();
        initTextFieldProperties();
    }

    /**
     * Init method for input TextFields properties.
     * Adds focus lost property.
     * Adds only allowing digits property.
     */
    private void initTextFieldProperties(){

        for(TextField textField: inputFields){

            //Adds property to TextField allowing users to only input numbers.
            textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                if(!newValue.matches("\\d*")){
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });

            //Adds focus lost property to textFields.
            textField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
                if(!newValue){
                    //ToDo add methods to run when focus lost.
                    updateAllTextFields();
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
        ProjectManager.getInstance().setActiveCategory(category);
        rootController.getModalController().populateTilePane();
        rootController.getModalAnchorPane().toFront();
    }

    /**
     * Method for adding a new lagenhetsDataSummaryItem.
     * Adds lagenhetsDataSummaryItem to Vbox in inputView.
     * @param event that triggered the method.
     * @throws IOException if unrecognized wrong file name.
     */
    @FXML
    private void addNewLagenhetstyp(ActionEvent event) throws IOException {
        Node lagenhetsDataItem = FXMLLoader.load(getClass().getResource("/fxml/lagenhetsDataSummaryItem.fxml"));
        lagenhetsTypVbox.getChildren().add(lagenhetsDataItem);
        updateAllTextFields();
    }

    /**
     * Updates all TextFields.
     */
    public void updateAllTextFields(){
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
     * Update all TextFields related to Tomtkostnader.
     */
    private void updateTomtkostnader(){
        tomtkostnaderKkr.setText("" + projectManager.getActiveProject().getTomtkostnaderKkr());
        tomtkostnaderKkr.setText("" + projectManager.getActiveProject().getTomtkostnaderKkr());
        tomtkostnaderKrBoa.setText("" + projectManager.getActiveProject().getTomtkostnaderKrBoa());
        tomtkostnaderKrBta.setText("" + projectManager.getActiveProject().getTomtkostnaderKrBta());
    }

    /**
     * Update all TextFields related to NedlagdaByggherre.
     */
    private void updateNedlagadaByggherre(){
        nedlagdaBygherreKkr.setText("" + projectManager.getActiveProject().getNedlagdaByggherreKkr());
        nedlagdaBygherreKrBoa.setText("" + projectManager.getActiveProject().getNedlagdaByggherreKrBoa());
        nedlagdaBygherreKrBta.setText("" + projectManager.getActiveProject().getNedlagdaByggherreKrBta());
    }

    /**
     * Update all TextFields related to Ansultningar.
     */
    private void updateAnslutningar(){
        anslutningarKkr.setText("" + projectManager.getActiveProject().getAnslutningarKkr());
        anslutningarKrBoa.setText("" + projectManager.getActiveProject().getAnslutningarKrBoa());
        anslutningarKrBta.setText("" + projectManager.getActiveProject().getAnslutningarKrBta());
    }

    /**
     * Update all TextFields related to Byggherrekostnader.
     */
    private void updateByggherrekostnader(){
        byggherrekostnaderKkr.setText("" + projectManager.getActiveProject().getByggherrekostnaderKkr());
        byggherrekostnaderKrBoa.setText("" + projectManager.getActiveProject().getByggherrekostnaderKrBoa());
        byggherrekostnaderKrBta.setText("" + projectManager.getActiveProject().getByggherrekostnaderKrBta());
    }

    /**
     * Update all TextFields related to Entreprenad.
     */
    private void updateEntreprenad(){
        entreprenadKkr.setText("" + projectManager.getActiveProject().getEntreprenadKkr());
        entreprenadKrBoa.setText("" + projectManager.getActiveProject().getEntreprenadKrBoa());
        entreprenadKrBta.setText("" + projectManager.getActiveProject().getEntreprenadKrBta());
    }

    /**
     * Update all TextFields related to Oforutsett.
     */
    private void updateOforutsett(){
        oforutsettKkr.setText("" + projectManager.getActiveProject().getOforutsettKkr());
        oforutsettKrBoa.setText("" + projectManager.getActiveProject().getOforutsettKrBoa());
        oforutsettKrBta.setText("" + projectManager.getActiveProject().getOforutsettKrBta());
    }

    /**
     * Update all TextFields related to FinansiellaKostnader.
     */
    private void updateFinansiellaKostnader(){
        finanisellaKostnaderKkr.setText("" + projectManager.getActiveProject().getFinansiellaKostnaderKkr());
        finanisellaKostnaderKrBoa.setText("" + projectManager.getActiveProject().getFinansiellaKostnaderKrBoa());
        finanisellaKostnaderKrBta.setText("" + projectManager.getActiveProject().getFinansiellaKostnaderKrBta());
    }

    /**
     * Update all TextFields related to Mervardeskatt.
     */
    private void updateMervardeskatt(){
        mervardeskattKkr.setText("" + projectManager.getActiveProject().getMervardeskattKkr());
        mervardeskattKrBoa.setText("" + projectManager.getActiveProject().getMervardeskattKrBoa());
        mervardeskattKrBta.setText("" + projectManager.getActiveProject().getMervardeskattKrBta());
    }

    /**
     * Update all TextFields related to Investeringsstod.
     */
    private void updateInvesteringsstod(){
        investeringsstodKkr.setText("" + projectManager.getActiveProject().getInvesteringsstodKkr());
        investeringsstodKrBoa.setText("" + projectManager.getActiveProject().getInvesteringsstodKrBoa());
        investeringsstodKrBta.setText("" + projectManager.getActiveProject().getInvesteringsstodKrBta());
    }

    /**
     * Update all TextFields related to Projektkostnad.
     */
    private void updateProjektkostnad(){
        projektkostnadKkr.setText("" + projectManager.getActiveProject().getProjektkostnadKkr());
        projektkostnadKrBoa.setText("" + projectManager.getActiveProject().getProjektkostnadKrBoa());
        projektkostnadKrBta.setText("" + projectManager.getActiveProject().getProjektkostnadKrBta());
    }

    /**
     * Update all TextFields related to Fastighetsvarde and Resultat.
     */
    private void updateFastighetsvardeOchResultat(){
        hyresintakterMedStod.setText("" + projectManager.getActiveProject().getHyresintakterMedStod());
        hyresintakterUtanStod.setText("" + projectManager.getActiveProject().getHyresintakterUtanStod());
        driftUnderhallMedStod.setText("" + projectManager.getActiveProject().getDriftUnderhallMedStod());
        driftUnderhallUtanStod.setText("" + projectManager.getActiveProject().getDriftUnderhallUtanStod());
        tomtrattsavgaldMedStod.setText("" + projectManager.getActiveProject().getTomtrattsavgaldMedStod());
        tomtrattsavgaldUtanStod.setText("" + projectManager.getActiveProject().getTomtrattsavgaldUtanStod());
        driftnettoMedStod.setText("" + projectManager.getActiveProject().getDriftnettoMedStod());
        driftnettoUtanStod.setText("" + projectManager.getActiveProject().getDriftnettoUtanStod());
        yieldMedStod.setText("" + projectManager.getActiveProject().getYieldMedStod());
        yieldUtanStod.setText("" + projectManager.getActiveProject().getYieldUtanStod());
        marknadsvardeMedStod.setText("" + projectManager.getActiveProject().getMarknadsvardeMedStod());
        marknadsvardeUtanStod.setText("" + projectManager.getActiveProject().getMarknadsvardeUtanStod());
    }
}
