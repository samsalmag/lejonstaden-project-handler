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
 * Initialize a sideBar Node.
 * @author Oscar Arvidson
 */
public class SideBarController {


    /**
     * TilePane in the sideBar.
     */
    @FXML private TilePane projectTilePane;

    /**
     * Node of the sideBar.fxml
     */
    private Node sideBar;

    /**
     * Initialize sideBar.fxml as a node.
     */
    public SideBarController(){
        FXMLLoader fxmlSideBar = new FXMLLoader(getClass().getResource("/fxml/sideBar.fxml"));
        fxmlSideBar.setController(this);

        try {
            sideBar = fxmlSideBar.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for on addProject button clicked.
     * Adds sideBarItem to the tilePane for the new project.
     * @param event of action.
     */
    @FXML
    private void addNewProject(ActionEvent event){
        //ToDo add functionality: Fill in input for new project and show it in the centerStage view.
        SideBarItemController sideBarItemController = new SideBarItemController();
        projectTilePane.getChildren().add(sideBarItemController.getSideBarItem());
    }


    //-----------------------------------Getters--------------------------------//

    /**
     * Getter for sideBar node.
     * @return A node of sideBar.fxml
     */
    public Node getSideBar() {
        return sideBar;
    }
}
