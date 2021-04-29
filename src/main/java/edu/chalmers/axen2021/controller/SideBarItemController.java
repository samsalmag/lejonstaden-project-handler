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
 * Handles all event triggered in the sideBarItem.
 * @author Oscar Arvidson
 * @author Erik Wetter
 * @author Sam Salek
 * @author Malte Åkvist
 */
public class SideBarItemController implements Initializable {

    /**
     * Parent Controller.
     */
    private RootController rootController = RootController.getInstance();

    /**
     * Button for opening an existing project.
     */
    @FXML Button button;

    /**
     * Name of the project bound to the button.
     */
    private String projectName;

    /**
     * Constructor that initialize the project name.
     * @param projectName name of the project.
     */
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
        rootController.updateSummaryView();
    }
}
