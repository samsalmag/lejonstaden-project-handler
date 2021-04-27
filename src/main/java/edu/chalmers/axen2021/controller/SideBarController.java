package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller class for the applications sideBar.fxml.
 * Handles all event triggered in the sideBar.
 * @author Oscar Arvidson
 * @author Erik Wetter
 */
public class SideBarController implements Initializable {

    private RootController rootController = RootController.getInstance();

    /**
     * VBox in the sideBar.
     */
    @FXML private VBox projectItemVbox;

    @FXML
    private void openAddNewProjectView() {
        rootController.getAddNewProjectAnchorPane().toFront();
    }

    /**
     * Method for on addProject button clicked.
     * Adds sideBarItem to the VBox for the new project.
     * @param event of action.
     */
    protected void addNewProject() throws IOException {
        //TODO - add functionality: Fill in input for new project and show it in the centerStage view.
        Node sideBarItem = FXMLLoader.load(getClass().getResource("/fxml/sideBarItem.fxml"));
        projectItemVbox.getChildren().add(sideBarItem);



        // TODO - replace 'String.valueOf(new Date().getTime())' with the name given to the project when it was created.
        //SaveManager.getInstance().saveProject(new Project(String.valueOf(new Date().getTime())));
        SaveManager.getInstance().saveProject(new Project("Button"));
        SaveManager.getInstance().saveProjectManager();
    }

    // TODO - temporary position for this method (?). Should preferably be in RootController (must find way to reach this class from there..)
    private void loadProjects() {
        for (Project project : SaveManager.getInstance().readProjects()) {
            Node sideBarItem = null;
            try {
                sideBarItem = FXMLLoader.load(getClass().getResource("/fxml/sideBarItem.fxml"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            projectItemVbox.getChildren().add(sideBarItem);

            Model.getInstance().addProject(project); // Add project to 'projects' list during load.
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProjects();
    }
}
