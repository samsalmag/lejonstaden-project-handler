package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.model.managers.SaveManager;
import edu.chalmers.axen2021.model.CostItem;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller class for the applications modalWindow.fxml.
 * Handles all event triggered in the modalWindow.
 * @author Oscar Arvidson
 * @author Erik Wetter
 * @author Malte Åkvist
 * @author Sam Salek
 */
@FXMLController
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
        SaveManager.getInstance().saveProject(ProjectManager.getInstance().getActiveProject());

        projectManager.getActiveProject().updateAllVariables();
        rootController.updateInputView();
        rootController.getModalAnchorPane().toBack();
        clearTilePane();
    }

    /**
     * Opens the addNewCostView based on addNewCostView.fxml.
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
        CostItem newCostItem = projectManager.getActiveProject().addCostItem(name);
        addModalItem(newCostItem);

        SaveManager.getInstance().saveProject(ProjectManager.getInstance().getActiveProject());
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
        ArrayList<String> activeCostItemNamesCopy = new ArrayList<>(projectManager.getActiveCostItemNames());
        for(String costItemName : activeCostItemNamesCopy) {

            // If cost item already exists in active project then just create a view.
            // If not then create a new cost item and add it to the active project before creating a view.
            CostItem costItem = getCostItemFromActive(costItemName);
            if(costItem != null) {
                addModalItem(costItem);
            } else {
                CostItem newCostItem = projectManager.getActiveProject().addCostItem(costItemName);
                addModalItem(newCostItem);
            }
        }
    }

    //ToDo add javaDoc.
    private CostItem getCostItemFromActive(String name) {
        for(CostItem costItem : projectManager.getActiveProject().getCostItemsMap().get(projectManager.getActiveCategory())) {
            if(costItem.getName().equals(name)) {
                return costItem;
            }
        }

        return null;
    }

    /**
     * Adds a new cost item view to the modal window.
     * Uses modalWindowItem.fxml to create the view.
     * @param newCostItem The new cost item to create a view for.
     */
    private void addModalItem(CostItem newCostItem) {
        FXMLLoader costItem = new FXMLLoader(getClass().getResource("/fxml/modalWindowItem.fxml"));
        CostItemController costItemController = new CostItemController(newCostItem);
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

    /**
     * Getter for modalWindowItemVBox.
     * @return modalWindowItemBox
     */
    public VBox getModalWindowItemVBox() {
        return modalWindowItemVBox;
    }
}
