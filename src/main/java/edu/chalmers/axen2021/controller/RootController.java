package edu.chalmers.axen2021.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the applications root.fxml.
 * Initialize starting page and all its nodes.
 * @author Oscar Arvidson
 * @author Erik Wetter
 */
public class RootController implements Initializable {

    /**
     * Header AnchorPane in root.fxml
     */
    @FXML private AnchorPane headerAnchorPane;
    /**
     * Sidebar AnchorPane in root.fxml
     */
    @FXML private AnchorPane sideBarAnchorPane;
    /**
     * CenterStage AnchorPane in root.fxml
     */
    @FXML private AnchorPane centerStageAnchorPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Node header = null;
        Node sideBar = null;

        try {
            header = FXMLLoader.load(getClass().getResource("/fxml/header.fxml"));
            sideBar = FXMLLoader.load(getClass().getResource("/fxml/sideBar.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        headerAnchorPane.getChildren().setAll(header);
        sideBarAnchorPane.getChildren().setAll(sideBar);
    }
}
