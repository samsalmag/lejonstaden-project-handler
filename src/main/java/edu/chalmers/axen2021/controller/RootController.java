package edu.chalmers.axen2021.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML private AnchorPane headerAnchorPane;
    @FXML private AnchorPane sideBarAnchorPane;
    @FXML private AnchorPane centerStageAnchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Initialize Controllers
        HeaderController headerController = new HeaderController();

        headerAnchorPane.getChildren().setAll(headerController.getHeader());

    }
}
