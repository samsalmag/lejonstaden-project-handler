package edu.chalmers.axen2021.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CostItemController implements Initializable {

    @FXML
    Label nameLabel;
    private String projectName;

    public CostItemController(String name) {
        projectName = name;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(projectName);
    }
}
