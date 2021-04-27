package edu.chalmers.axen2021.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddNewProjectController {

    private RootController rootController = RootController.getInstance();

    @FXML private TextField projektnamnTextField;

    /**
     * Method for closing the modalWindow.
     * Puts the modalWindowAnchorPane to back in scene.
     * @param event that triggered the method.
     */
    @FXML
    private void closeAddNewProjectView(Event event) {
        rootController.getAddNewProjectAnchorPane().toBack();
        projektnamnTextField.clear();
    }

    @FXML
    private void addNewProject() throws IOException {
        rootController.getSideBarController().addNewProject();
        rootController.getAddNewProjectAnchorPane().toBack();
        projektnamnTextField.clear();
    }

}
