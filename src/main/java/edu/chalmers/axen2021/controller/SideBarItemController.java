package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.ProjectManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the applications sideBarItem.fxml.
 * Handles all event triggered in the sideBarItem (project item).
 * @author Oscar Arvidson
 * @author Erik Wetter
 * @author Sam Salek
 * @author Malte Åkvist
 */
public class SideBarItemController implements Initializable {

    /**
     * The button in the .fxml file
     */
    @FXML Button button;

    /**
     * The project name
     */
    private String projectName;

    public SideBarItemController(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button.setText(projectName);
    }

    /**
     * Method for when button is clicked
     * @param event of action.
     */
    @FXML
    private void onClick(ActionEvent event) {
        ProjectManager.getInstance().setActiveProject(button.getText());
    }
}
