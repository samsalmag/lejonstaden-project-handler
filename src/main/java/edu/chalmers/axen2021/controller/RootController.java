package edu.chalmers.axen2021.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

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

        //Initialize Controllers
        HeaderController headerController = new HeaderController();
        SummaryViewController summaryViewController = new SummaryViewController();
        SideBarController sideBarController = new SideBarController();

        //Adds node to headerAnchorPane
        headerAnchorPane.getChildren().setAll(headerController.getHeader());
        centerStageAnchorPane.getChildren().setAll(summaryViewController.getNode());
        sideBarAnchorPane.getChildren().setAll(sideBarController.getSideBar());

    }
}
