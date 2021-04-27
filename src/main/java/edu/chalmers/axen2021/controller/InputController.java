package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    /**
     * Instance of model.
     */
    private Model model = Model.getInstance();

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
                    updateAllLabels();
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
     * Method for opening the modalWindow.
     * Puts the modalWindowAnchorPane to front in scene.
     * @param event that triggered the method.
     */
    @FXML
    private void categoryButtonClicked(ActionEvent event) {
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
        Node lagenhetsDataItem = FXMLLoader.load(getClass().getResource("/fxml/LagenhetsDataSummaryItem.fxml"));
        lagenhetsTypVbox.getChildren().add(lagenhetsDataItem);
        updateAllLabels();
    }

    public void updateAllLabels(){
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

    private void updateTomtkostnader(){
        tomtkostnaderKkr.setText("" + model.getProjects().get(0).getTomtkostnaderKkr());
        tomtkostnaderKrBoa.setText("" + model.getProjects().get(0).getTomtkostnaderKrBoa());
        tomtkostnaderKrBta.setText("" + model.getProjects().get(0).getTomtkostnaderKrBta());
    }

    private void updateNedlagadaByggherre(){
        nedlagdaBygherreKkr.setText("" + model.getProjects().get(0).getNedlagdaByggherreKkr());
        nedlagdaBygherreKrBoa.setText("" + model.getProjects().get(0).getNedlagdaByggherreKrBoa());
        nedlagdaBygherreKrBta.setText("" + model.getProjects().get(0).getNedlagdaByggherreKrBta());
    }

    private void updateAnslutningar(){
        anslutningarKkr.setText("" + model.getProjects().get(0).getAnslutningarKkr());
        anslutningarKrBoa.setText("" + model.getProjects().get(0).getAnslutningarKrBoa());
        anslutningarKrBta.setText("" + model.getProjects().get(0).getAnslutningarKrBta());
    }

    private void updateByggherrekostnader(){
        byggherrekostnaderKkr.setText("" + model.getProjects().get(0).getByggherrekostnaderKkr());
        byggherrekostnaderKrBoa.setText("" + model.getProjects().get(0).getByggherrekostnaderKrBoa());
        byggherrekostnaderKrBta.setText("" + model.getProjects().get(0).getByggherrekostnaderKrBta());
    }

    private void updateEntreprenad(){
        entreprenadKkr.setText("" + model.getProjects().get(0).getEntreprenadKkr());
        entreprenadKrBoa.setText("" + model.getProjects().get(0).getEntreprenadKrBoa());
        entreprenadKrBta.setText("" + model.getProjects().get(0).getEntreprenadKrBta());
    }

    private void updateOforutsett(){
        oforutsettKkr.setText("" + model.getProjects().get(0).getOforutsettKkr());
        oforutsettKrBoa.setText("" + model.getProjects().get(0).getOforutsettKrBoa());
        oforutsettKrBta.setText("" + model.getProjects().get(0).getOforutsettKrBta());
    }

    private void updateFinansiellaKostnader(){
        finanisellaKostnaderKkr.setText("" + model.getProjects().get(0).getFinansiellaKostnaderKkr());
        finanisellaKostnaderKrBoa.setText("" + model.getProjects().get(0).getFinansiellaKostnaderKrBoa());
        finanisellaKostnaderKrBta.setText("" + model.getProjects().get(0).getFinansiellaKostnaderKrBta());
    }

    private void updateMervardeskatt(){
        mervardeskattKkr.setText("" + model.getProjects().get(0).getMervardeskattKkr());
        mervardeskattKrBoa.setText("" + model.getProjects().get(0).getMervardeskattKrBoa());
        mervardeskattKrBta.setText("" + model.getProjects().get(0).getMervardeskattKrBta());
    }

    private void updateInvesteringsstod(){
        investeringsstodKkr.setText("" + model.getProjects().get(0).getInvesteringsstodKkr());
        investeringsstodKrBoa.setText("" + model.getProjects().get(0).getInvesteringsstodKrBoa());
        investeringsstodKrBta.setText("" + model.getProjects().get(0).getInvesteringsstodKrBta());
    }

    private void updateProjektkostnad(){
        projektkostnadKkr.setText("" + model.getProjects().get(0).getProjektkostnadKkr());
        projektkostnadKrBoa.setText("" + model.getProjects().get(0).getProjektkostnadKrBoa());
        projektkostnadKrBta.setText("" + model.getProjects().get(0).getProjektkostnadKrBta());
    }

    private void updateFastighetsvardeOchResultat(){
        hyresintakterMedStod.setText("" + model.getProjects().get(0).getHyresintakterMedStod());
        hyresintakterUtanStod.setText("" + model.getProjects().get(0).getHyresintakterUtanStod());
        driftUnderhallMedStod.setText("" + model.getProjects().get(0).getDriftUnderhallMedStod());
        driftUnderhallUtanStod.setText("" + model.getProjects().get(0).getDriftUnderhallUtanStod());
        tomtrattsavgaldMedStod.setText("" + model.getProjects().get(0).getTomtrattsavgaldMedStod());
        tomtrattsavgaldUtanStod.setText("" + model.getProjects().get(0).getTomtrattsavgaldUtanStod());
        driftnettoMedStod.setText("" + model.getProjects().get(0).getDriftnettoMedStod());
        driftnettoUtanStod.setText("" + model.getProjects().get(0).getDriftnettoUtanStod());
        yieldMedStod.setText("" + model.getProjects().get(0).getYieldMedStod());
        yieldUtanStod.setText("" + model.getProjects().get(0).getYieldUtanStod());
        marknadsvardeMedStod.setText("" + model.getProjects().get(0).getMarknadsvardeMedStod());
        marknadsvardeUtanStod.setText("" + model.getProjects().get(0).getMarknadsvardeUtanStod());
    }

}
