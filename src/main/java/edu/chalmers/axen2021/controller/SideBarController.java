package edu.chalmers.axen2021.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

import java.io.IOException;

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

    @FXML
    private void addNewProject(ActionEvent event){
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
