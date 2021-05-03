package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.managers.ProjectManager;
import edu.chalmers.axen2021.managers.SaveManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller class for the applications modalWindow.fxml.
 * Handles all event triggered in the modalWindow.
 * @author Oscar Arvidson
 * @author Erik Wetter
 * @author Malte Åkvist
 * @author Sam Salek
 */
public class ModalController {

    /**
     * Parent controller.
     */
    private RootController rootController = RootController.getInstance();

    /**
     * Instance of the project manager.
     */
    private ProjectManager projectManager = ProjectManager.getInstance();

    /**
     * TilePane in the modalWindow containing modalWindowItems.
     */
    @FXML private VBox modalWindowItemVBox;

    /**
     * Label for the category name.
     */
    @FXML private Label categoryNameLabel;

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
     * Opens the addNewCostView based on addNewCostVoew.fxml.
     */
    @FXML
    private void openAddNewCostView() {
        rootController.getAddNewCostAnchorPane().toFront();
    }

    /**
     * Method for adding a new modalWindowItem.
     * Adds modalWindowItem to the TilePane in modalWindow.
     */
    public void addNewModalWindowItem(String name) {
        addModalItem(name);

        projectManager.getActiveProject().addCostItem(name);
        SaveManager.getInstance().saveProjectManager();
    }

    /**
     * Removes all cost items in the modal window.
     */
    private void clearTilePane() {
        modalWindowItemVBox.getChildren().clear();
    }

    /**
     * This method populates the active category modal window with cost items,
     * based on the number of saved cost items for that specific category.
     */
    public void populateTilePane() {
        // For every cost in the active category: add a cost item
        for(String costItemName : projectManager.getActiveCostItemNames()) {
            addModalItem(costItemName);
        }
    }

    /**
     * Adds a new cost item view to the modal window.
     * Uses modalWindowItem.fxml to create the view.
     * @param name
     */
    private void addModalItem(String name) {
        FXMLLoader costItem = new FXMLLoader(getClass().getResource("/fxml/modalWindowItem.fxml"));
        CostItemController costItemController = new CostItemController(name);
        costItem.setController(costItemController);
        Node costItemNode = null;

        try {
            costItemNode = costItem.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        modalWindowItemVBox.getChildren().add(costItemNode);
    }

    /**
     * Sets the category name label text.
     * @param text Input string.
     */
    public void setCategoryNameLabelText(String text) {
        categoryNameLabel.setText(text);
    }
}
