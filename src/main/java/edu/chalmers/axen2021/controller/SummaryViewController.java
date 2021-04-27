package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.Model;
import edu.chalmers.axen2021.observers.IViewObservable;
import edu.chalmers.axen2021.observers.IViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller class for the applications SummaryView.fxml.
 * Handles all event triggered in the SummaryView.
 * @author Erik Wetter
 * @author Oscar Arvidson
 */
public class SummaryViewController implements IViewObservable {

    Model model = Model.getInstance();

    //Variables connected to text fields in SummaryView.fxml
    @FXML private TextField projektkostnadMedStod;
    @FXML private TextField projektkostnadUtanStod;
    @FXML private TextField marknadsvardeMedStod;
    @FXML private TextField marknadsvardeUtanStod;
    @FXML private TextField projektvinstMedStod;
    @FXML private TextField projektvinstUtanStod;
    @FXML private TextField projektvinstProcentMedStod;
    @FXML private TextField projektvinstProcentUtanStod;

    /**
     * VBox in the SummaryView.
     */
    @FXML private VBox lagenhetsTypVbox;

    public void updateTextFields() {
        updateProjektkostnad();
        updateMarknadsvarde();
        updateProjektvinst();
        updateProjektvinstProcent();
    }
    private void updateProjektkostnad() {
        projektkostnadMedStod.setText("" + model.getProjects().get(0).getProjektkostnadKkrMedStod());
        projektkostnadUtanStod.setText("" + model.getProjects().get(0).getProjektkostnadKkr());
    }
    private void updateMarknadsvarde() {
        marknadsvardeMedStod.setText("" + model.getProjects().get(0).getMarknadsvardeMedStod());
        marknadsvardeUtanStod.setText("" + model.getProjects().get(0).getMarknadsvardeUtanStod());
    }
    private void updateProjektvinst() {
        projektvinstMedStod.setText("" + model.getProjects().get(0).getProjektvinstMedStod());
        projektvinstUtanStod.setText("" + model.getProjects().get(0).getProjektvinstUtanStod());
    }
    private void updateProjektvinstProcent() {
        projektvinstProcentMedStod.setText("" + model.getProjects().get(0).getProjektvinstProcentMedStod());
        projektvinstProcentUtanStod.setText("" + model.getProjects().get(0).getProjektvinstProcentUtanStod());
    }
    /**
     * Method for addNewLagenhetstyp button clicked.
     * Adds a new LagenhetsDataSummaryItem to the VBox.
     * @param event of action.
     */
    @FXML
    private void addNewLagenhetstyp(ActionEvent event) throws IOException {
        Node LagenhetsDataSummaryItem = FXMLLoader.load(getClass().getResource("/fxml/LagenhetsDataSummaryItem.fxml"));
        lagenhetsTypVbox.getChildren().add(LagenhetsDataSummaryItem);
    }

    /**
     * Notifies observers of the view that is clicked
     */
    @Override
    public void notifyObservers() {
        for (IViewObserver IViewObserver : IViewObservers) {
            //IViewObserver.update(fxmlName);
        }
    }

    /**
     * Adds an observer to the viewObserver list
     * @param iViewObserver a viewObserver
     */
    @Override
    public void addObserver(IViewObserver iViewObserver) {
        IViewObservers.add(iViewObserver);
    }
}
