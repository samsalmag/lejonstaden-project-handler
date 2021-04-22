package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.observers.IViewObserver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


/**
 * Controller singleton class for the applications root.fxml.
 * Initialize starting page and all its nodes.
 * @author Oscar Arvidson
 * @author Erik Wetter
 */
public class RootController implements IViewObserver {

    /**
     * Instance of RootController class.
     */
    private static RootController instance = null;

    //Because of singleton pattern.
    private RootController() {}

    /**
     * Getter for instance of this class.
     * @return instance of this class.
     */
    public static RootController getInstance() {
        if(instance == null) {
            instance = new RootController();
        }
        return instance;
    }

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
    /**
     * Modal AnchorPane in root.fxml
     */
    @FXML private AnchorPane modalAnchorPane;

    /**
     * Initialize method that starts up the first scene and all its children.
     */
    public void initialize() {

        Node header = null;
        Node sideBar = null;
        Node modalWindow = null;

        try {
            header = FXMLLoader.load(getClass().getResource("/fxml/header.fxml"));
            sideBar = FXMLLoader.load(getClass().getResource("/fxml/sideBar.fxml"));
            modalWindow = FXMLLoader.load(getClass().getResource("/fxml/modalWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        headerAnchorPane.getChildren().setAll(header);
        setAnchors(headerAnchorPane, header);
        sideBarAnchorPane.getChildren().setAll(sideBar);
        setAnchors(sideBarAnchorPane, sideBar);
        modalAnchorPane.getChildren().setAll(modalWindow);
        setAnchors(modalAnchorPane, modalWindow);


    }

    /**
     * Method for anchoring child to its parent.
     * @param anchorPane Parent.
     * @param node Child.
     */
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


    /**
     * Getter for the modalAnchorPane.
     * @return
     */
    public AnchorPane getModalAnchorPane() {
        return modalAnchorPane;
    }
}
