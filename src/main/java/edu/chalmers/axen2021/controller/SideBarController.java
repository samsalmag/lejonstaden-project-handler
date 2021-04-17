package edu.chalmers.axen2021.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

import java.io.IOException;

/**
 * Controller class for the applications sideBar.fxml.
 * Handles all event triggered in the sideBar.
 * @author Oscar Arvidson
 * @author Erik Wetter
 */
public class SideBarController {


    /**
     * TilePane in the sideBar.
     */
    @FXML private TilePane projectTilePane;

    /**
     * Method for on addProject button clicked.
     * Adds sideBarItem to the tilePane for the new project.
     * @param event of action.
     */
    @FXML
    private void addNewProject(ActionEvent event) throws IOException {
        //ToDo add functionality: Fill in input for new project and show it in the centerStage view.
        Node sideBarItem = FXMLLoader.load(getClass().getResource("/fxml/sideBarItem.fxml"));
        projectTilePane.getChildren().add(sideBarItem);
    }

}
