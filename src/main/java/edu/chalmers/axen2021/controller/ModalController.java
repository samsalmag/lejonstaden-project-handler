package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.Project;
import edu.chalmers.axen2021.model.ProjectManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

import java.io.IOException;

/**
 * Controller class for the applications modalWindow.fxml.
 * Handles all event triggered in the modalWindow.
 * @author Oscar Arvidson
 * @author Erik Wetter
 */
public class ModalController {
    private ProjectManager projectManager = ProjectManager.getInstance();

    /**
     * Parent controller
     */
    private RootController rootController = RootController.getInstance();

    /**
     * TilePane in the modalWindow containing modalWindowItems.
     */
    @FXML private TilePane modalWindowItemTilePane;

    /**
     * Method for closing the modalWindow.
     * Puts the modalWindowAnchorPane to back in scene.
     * @param event that triggered the method.
     */
    @FXML
    private void closeModalWindow(Event event) {
        rootController.getModalAnchorPane().toBack();
        clearTilePane();
    }

    /**
     * Method for adding a new modalWindowItem.
     * Adds modalWindowItem to the TilePane in modalWindow.
     * @param event that triggered the method.
     * @throws IOException if unrecognized wrong file name.
     */
    @FXML
    private void addNewModalWindowItem(Event event) throws IOException {
        Node modalWindowItem = FXMLLoader.load(getClass().getResource("/fxml/modalWindowItem.fxml"));
        modalWindowItemTilePane.getChildren().add(modalWindowItem);

        ProjectManager.getInstance().getActiveProject().addCostItem();
    }

    private void clearTilePane() {
        modalWindowItemTilePane.getChildren().clear();
    }

    public void populateTilePane() {
        // For every cost in a category add a cost to the specified category
        for(int i = 0; i < projectManager.getActiveCategoryList().size(); i++) {
            Node modalWindowItem = null;
            try {
                modalWindowItem = FXMLLoader.load(getClass().getResource("/fxml/modalWindowItem.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            modalWindowItemTilePane.getChildren().add(modalWindowItem);
        }
    }
}
