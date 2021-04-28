package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.ProjectManager;
import edu.chalmers.axen2021.model.SaveManager;
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
 * @author Malte Åkvist
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

    @FXML
    private void openAddNewCostView() {
        rootController.getAddNewCostAnchorPane().toFront();
    }

    /**
     * Method for adding a new modalWindowItem.
     * Adds modalWindowItem to the TilePane in modalWindow.
     * @throws IOException if unrecognized wrong file name.
     */
    public void addNewModalWindowItem(String name) {
        FXMLLoader costItem = new FXMLLoader(getClass().getResource("/fxml/modalWindowItem.fxml"));
        CostItemController costItemController = new CostItemController(name);
        costItem.setController(costItemController);
        Node costItemNode = null;

        try {
            costItemNode = costItem.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        modalWindowItemTilePane.getChildren().add(costItemNode);

        ProjectManager.getInstance().getActiveProject().addCostItem();
        SaveManager.getInstance().saveProjectManager();
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
