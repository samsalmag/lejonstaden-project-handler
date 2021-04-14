package edu.chalmers.axen2021.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Controller class for the applications header.fxml.
 * Handles all event triggered in the header.
 * Initialize a header Node.
 * @author Oscar Arvidson
 */
public class HeaderController{

    /**
     * Logo image
     */
    @FXML private ImageView logoImageView;

    /**
     * Node of the header.fxml
     */
    private Node header;

    /**
     * Initialize header.fxml as a node.
     */
    public HeaderController(){
        FXMLLoader fxmlHeader = new FXMLLoader(getClass().getResource("/fxml/header.fxml"));
        fxmlHeader.setController(this);

        try {
            header = fxmlHeader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-----------------------------------Getters--------------------------------//

    /**
     * Getter for header node.
     * @return A node of header.fxml
     */
    public Node getHeader() {
        return header;
    }
}
