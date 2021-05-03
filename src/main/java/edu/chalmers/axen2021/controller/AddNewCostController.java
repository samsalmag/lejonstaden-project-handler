package edu.chalmers.axen2021.controller;

import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

/**
 * Controller class for the applications addNewCostView.fxml.
 * Handles all event triggered in the addNewCostView.
 * @author Sam Salek
 */
@FXMLController
public class AddNewCostController {

    /**
     * The parent controller.
     */
    private RootController rootController = RootController.getInstance();

    /**
     * The input text field.
     */
    @FXML
    private TextField costNameTextField;

    /**
     * Method for closing the addNewCostView.
     * Puts the addNewCostView to back in scene.
     * @param event that triggered the method.
     */
    @FXML
    private void closeAddNewCostView(Event event) {
        rootController.getAddNewCostAnchorPane().toBack();
        costNameTextField.clear();
    }

    /**
     * Method for addCost button clicked.
     * Adds a new cost item to the modal window.
     */
    @FXML
    private void addNewCost() {

        // Cost item name input can't be empty.
        if(costNameTextField.getText().equals("") || costNameTextField.getText().equals(null)) {
            return;
        }

        rootController.getModalController().addNewModalWindowItem(costNameTextField.getText() + ":");
        rootController.getAddNewCostAnchorPane().toBack();
        costNameTextField.clear();
    }
}
