package edu.chalmers.axen2021.controller;


import edu.chalmers.axen2021.model.ProjectManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Controller class for the applications sideBarItem.fxml.
 * Handles all event triggered in the sideBarItem.
 * @author Oscar Arvidson
 * @author Erik Wetter
 */
public class SideBarItemController {

    @FXML Button button;

    /**
     * Method for when button is clicked
     * @param event of action.
     */
    @FXML
    private void onClick(ActionEvent event) throws Exception {
        ProjectManager.getInstance().setActiveProject(button.getText());

    }
}
