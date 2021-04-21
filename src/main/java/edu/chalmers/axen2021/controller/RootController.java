package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.observers.IViewObserver;
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
public class RootController implements Initializable, IViewObserver {

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
        setAnchors(headerAnchorPane, header);
        sideBarAnchorPane.getChildren().setAll(sideBar);
        setAnchors(sideBarAnchorPane, sideBar);
    }


    private void setAnchors(AnchorPane anchorPane, Node node) {
        anchorPane.setTopAnchor(node, 0.0);
        anchorPane.setRightAnchor(node, 0.0);
        anchorPane.setLeftAnchor(node, 0.0);
        anchorPane.setBottomAnchor(node, 0.0);
    }

    /**
     * Method is called when a button that wants to change view/scene is clicked in order to change view
     * @param fxmlName the name of the fxml file that should be changed to
     */
    @Override
    public void update(String fxmlName) {

        Node center = null;
        try {
            center = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        centerStageAnchorPane.getChildren().setAll(center);
    }
}
