package edu.chalmers.axen2021.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import java.io.IOException;

/**
 * Controller class for the applications sideBar.fxml.
 * Handles all event triggered in the sideBar.
 * @author Oscar Arvidson
 * @author Erik Wetter
 * @author Sam Salek
 */
@FXMLController
public class SideBarController {

    /**
     * The parent controller
     */
    private RootController rootController = RootController.getInstance();

    /**
     * VBox in the sideBar.
     */
    @FXML private VBox projectItemVbox;

    /**
     * Opens the addNewProject view.
     */
    @FXML
    private void openAddNewProjectView() {
        rootController.getAddNewProjectAnchorPane().toFront();
    }

    /**
     * Adds a new SideBarItem (new Project) to the SideBar with the given name.
     * @param projectName Name of project
     */
    public void addNewSideBarItem(String projectName) {
        FXMLLoader sideBarItem = new FXMLLoader(getClass().getResource("/fxml/sideBarItem.fxml"));
        SideBarItemController sideBarItemController = new SideBarItemController(projectName);
        sideBarItem.setController(sideBarItemController);
        Node sideBarItemNode = null;

        try {
            sideBarItemNode = sideBarItem.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        projectItemVbox.getChildren().add(sideBarItemNode);
    }
}
