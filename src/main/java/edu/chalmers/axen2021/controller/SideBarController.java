package edu.chalmers.axen2021.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class SideBarController {

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

    //-----------------------------------Getters--------------------------------//

    /**
     * Getter for sideBar node.
     * @return A node of sideBar.fxml
     */
    public Node getSideBar() {
        return sideBar;
    }
}
