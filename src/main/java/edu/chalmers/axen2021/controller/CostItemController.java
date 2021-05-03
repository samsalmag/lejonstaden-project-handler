package edu.chalmers.axen2021.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the applications modalWindowItem.fxml.
 * Handles all event triggered in the modalWindowItem (cost item).
 * @author Sam Salek
 */
@FXMLController
public class CostItemController implements Initializable {

    /**
     * The name label in the .fxml file.
     */
    @FXML Label nameLabel;

    /**
     * Name of the cost item
     */
    private String name;

    public CostItemController(String name) {
        this.name = name;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(name);
    }
}
