package edu.chalmers.axen2021.controller.modals;

import edu.chalmers.axen2021.controller.items.CostItemController;
import edu.chalmers.axen2021.controller.FXMLController;
import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.model.projectdata.CostItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller class for the applications modalWindow.fxml.
 * Handles all event triggered in the modalWindow.
 * @author Oscar Arvidson
 * @author Erik Wetter
 * @author Malte Åkvist
 * @author Sam Salek
 */
@FXMLController
public class ModalController implements Initializable {

    /**
     * Parent controller.
     */
    private RootController rootController = RootController.getInstance();

    /**
     * Instance of the project manager.
     */
    private ProjectManager projectManager = ProjectManager.getInstance();

    /**
     * Main root node for this .fxml.
     */
    @FXML private AnchorPane mainAnchorPane;

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
     */
    @FXML
    private void closeModalWindow() {
        rootController.closeModalWindow();
    }

    /**
     * Opens the addNewCostView based on addNewCostView.fxml.
     */
    @FXML
    private void openAddNewCostView() {
        rootController.openAddNewCostView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initMainAnchorPane();
    }

    /**
     * Removes all cost items in the modal window.
     */
    public void clearCostItems() {
        modalWindowItemVBox.getChildren().clear();
    }

    /**
     * This method populates the active category modal window with cost items,
     * based on the number of saved cost items for that specific category.
     */
    public void populateCostItems() {
        // For every cost in the active category: add a cost item
        ArrayList<String> activeCostItemNamesCopy = new ArrayList<>(projectManager.getActiveCostItemNames());
        for(String costItemName : activeCostItemNamesCopy) {

            // If cost item already exists in active project then just create a view.
            // If not then create a new cost item and add it to the active project before creating a view.
            CostItem costItem = projectManager.getActiveProject().getActiveCostItem(costItemName);
            if(costItem != null) {
                addModalItem(costItem);
            } else {
                CostItem newCostItem = projectManager.getActiveProject().addCostItem(costItemName);
                addModalItem(newCostItem);
            }
        }
    }

    /**
     * Adds a new cost item view to the modal window.
     * Uses modalWindowItem.fxml to create the view.
     * @param newCostItem The new cost item to create a view for.
     */
    public void addModalItem(CostItem newCostItem) {
        FXMLLoader costItem = new FXMLLoader(getClass().getResource("/fxml/modalWindowItem.fxml"));
        CostItemController costItemController = new CostItemController(newCostItem);
        costItem.setController(costItemController);
        Node costItemNode = null;

        try {
            costItemNode = costItem.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        modalWindowItemVBox.getChildren().add(0, costItemNode);
    }

    /**
     * Initializes the main root node (the AnchorPane).
     */
    private void initMainAnchorPane() {
        mainAnchorPane.setOnKeyPressed(keyEvent -> {
            // If 'Escape' is pressed then close view
            if(keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                closeModalWindow();
            }
        });
    }

    /**
     * Sets the category name label text.
     * @param text Input string.
     */
    public void setCategoryNameLabelText(String text) {
        categoryNameLabel.setText(text + " (" + projectManager.getActiveCategory().getUnit() + ")");
    }

    /**
     * Getter for modalWindowItemVBox.
     * @return modalWindowItemBox
     */
    public VBox getModalWindowItemVBox() {
        return modalWindowItemVBox;
    }
}
