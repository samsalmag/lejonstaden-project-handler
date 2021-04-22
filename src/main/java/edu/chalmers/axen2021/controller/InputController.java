package edu.chalmers.axen2021.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller class for the applications inputView.fxml.
 * Handles all event triggered in the inputView.
 * @author Oscar Arvidson
 * @author Erik Wetter
 */
public class InputController {

    /**
     * Parent controller
     */
    private RootController rootController = RootController.getInstance();

    /**
     * Vbox in the inputView containing lagenhetsDataSummaryItems.
     */
    @FXML private VBox lagenhetsTypVbox;

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
    }

}
