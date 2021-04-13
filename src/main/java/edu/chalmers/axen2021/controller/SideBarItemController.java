package edu.chalmers.axen2021.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class SideBarItemController {

    /**
     * Node of the sideBarItem.fxml
     */
    private Node sideBarItem;

    /**
     * Initialize sideBarItem.fxml as a node.
     */
    public SideBarItemController() {

        FXMLLoader fxmlSideBarItem = new FXMLLoader(getClass().getResource("/fxml/sideBarItem.fxml"));
        fxmlSideBarItem.setController(this);

        try {
            sideBarItem = fxmlSideBarItem.load();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for sideBarItem node.
     * @return A node of sideBarItem.fxml
     */
    public Node getSideBarItem() {
        return sideBarItem;
    }
}
