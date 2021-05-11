package edu.chalmers.axen2021.controller.modals;

import edu.chalmers.axen2021.controller.FXMLController;
import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

/**
 * Controller class for the applications addNewCostView.fxml.
 * Handles all event triggered in the addNewCostView.
 * @author Sam Salek
 */
@FXMLController
public class AddNewCostController {

    /**
     * The parent controller.
     */
    private RootController rootController = RootController.getInstance();

    /**
     * The input text field.
     */
    @FXML
    private TextField costNameTextField;

    /**
     * Method for closing the addNewCostView.
     * Puts the addNewCostView to back in scene.
     * @param event that triggered the method.
     */
    @FXML
    private void closeAddNewCostView(Event event) {
        rootController.closeAddNewCostView();
    }

    /**
     * Method for addCost button clicked.
     * Adds a new cost item to the modal window.
     */
    @FXML
    private void addNewCost() {

        // Cost item name input can't be empty.
        if(costNameTextField.getText().equals("") || costNameTextField.getText().equals(null)) {
            return;
        }

        // Name can't be same as existing cost item in the same category
        for (String costItemName : ProjectManager.getInstance().getActiveCostItemNames()) {
            if (costItemName.toLowerCase().equals(costNameTextField.getText().toLowerCase())) {
                return;
            }
        }

        rootController.addCostItem(costNameTextField.getText());
        rootController.closeAddNewCostView();
    }

    public TextField getCostNameTextField() {
        return costNameTextField;
    }
}
