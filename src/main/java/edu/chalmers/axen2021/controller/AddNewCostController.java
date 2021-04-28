package edu.chalmers.axen2021.controller;

import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

public class AddNewCostController {

    private RootController rootController = RootController.getInstance();

    @FXML
    private TextField costNameTextField;

    /**
     * Method for closing the modalWindow.
     * Puts the modalWindowAnchorPane to back in scene.
     * @param event that triggered the method.
     */
    @FXML
    private void closeAddNewCostView(Event event) {
        rootController.getAddNewProjectAnchorPane().toBack();
        costNameTextField.clear();
    }

    /**
     * Method for adding a new cost item to the modal window.
     */
    @FXML
    private void addNewCost() {
        rootController.getModalController().addNewModalWindowItem(costNameTextField.getText() + ":");

        rootController.getAddNewCostAnchorPane().toBack();
        costNameTextField.clear();
    }
}
