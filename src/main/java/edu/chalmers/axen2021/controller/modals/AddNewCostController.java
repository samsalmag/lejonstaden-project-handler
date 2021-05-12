package edu.chalmers.axen2021.controller.modals;

import edu.chalmers.axen2021.controller.FXMLController;
import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the applications addNewCostView.fxml.
 * Handles all event triggered in the addNewCostView.
 * @author Sam Salek
 */
@FXMLController
public class AddNewCostController implements Initializable {

    /**
     * The parent controller.
     */
    private RootController rootController = RootController.getInstance();

    /**
     * The input text field.
     */
    @FXML
    private TextField costNameTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTextField();
    }

    /**
     * Method for closing the addNewCostView.
     * Puts the addNewCostView to back in scene.
     */
    @FXML
    private void closeAddNewCostView() {
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

    /**
     * Initializes the TextField for cost item name input.
     */
    private void initTextField() {

        // On key pressed event
        costNameTextField.setOnKeyPressed(keyEvent -> {
            // If 'Enter' is pressed then add new cost item.
            if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                addNewCost();
            }
            // If 'Escape' is pressed then close view
            else if(keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                closeAddNewCostView();
            }
        });
    }

    /**
     * Getter for the TextField for cost item name input.
     * @return
     */
    public TextField getCostNameTextField() {
        return costNameTextField;
    }
}
