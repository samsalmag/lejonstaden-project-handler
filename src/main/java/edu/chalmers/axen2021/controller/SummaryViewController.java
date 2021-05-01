package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.managers.ProjectManager;
import edu.chalmers.axen2021.observers.IViewObservable;
import edu.chalmers.axen2021.observers.IViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller class for the applications summaryView.fxml.
 * Handles all event triggered in the SummaryView.
 * @author Erik Wetter
 * @author Oscar Arvidson
 */
public class SummaryViewController implements IViewObservable {

    /**
     * Instance of Project Manager.
     */
    private ProjectManager projectManager = ProjectManager.getInstance();

    /**
     * Instance of parent controller.
     */
    private RootController rootController = RootController.getInstance();

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
     * VBox in the SummaryView.
     */
    @FXML private VBox lagenhetsTypVbox;

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
        projektkostnadMedStod.setText("" + projectManager.getActiveProject().getProjektkostnadKkrMedStod());
        projektkostnadUtanStod.setText("" + projectManager.getActiveProject().getProjektkostnadKkr());
    }

    /**
     * Updates all TextFields related to Marknadsvarde.
     */
    private void updateMarknadsvarde() {
        marknadsvardeMedStod.setText("" + projectManager.getActiveProject().getMarknadsvardeMedStod());
        marknadsvardeUtanStod.setText("" + projectManager.getActiveProject().getMarknadsvardeUtanStod());
    }

    /**
     * Updates all TextFields related to Projektvinst.
     */
    private void updateProjektvinst() {
        projektvinstMedStod.setText("" + projectManager.getActiveProject().getProjektvinstMedStod());
        projektvinstUtanStod.setText("" + projectManager.getActiveProject().getProjektvinstUtanStod());
    }

    /**
     * Updates all TextFields related to ProjektvinstProcent.
     */
    private void updateProjektvinstProcent() {
        projektvinstProcentMedStod.setText("" + projectManager.getActiveProject().getProjektvinstProcentMedStod());
        projektvinstProcentUtanStod.setText("" + projectManager.getActiveProject().getProjektvinstProcentUtanStod());
    }

    /**
     * Method for addNewLagenhetstyp button clicked.
     * Adds a new LagenhetsDataSummaryItem to the VBox.
     * @param event of action.
     */
    @FXML
    private void addNewLagenhetstyp(ActionEvent event) throws IOException {
        Node LagenhetsDataSummaryItem = FXMLLoader.load(getClass().getResource("/fxml/lagenhetsDataSummaryItem.fxml"));
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
