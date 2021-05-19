package edu.chalmers.axen2021.controller.modals;

import edu.chalmers.axen2021.controller.FXMLController;
import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the applications changeCostItemNameView.fxml.
 * Handles all event triggered in the changeCostItemNameView.
 *
 * @author Sam Salek
 */
@FXMLController
public class ChangeCostItemNameController implements Initializable {

    /**
     * The parent controller.
     */
    private final RootController rootController = RootController.getInstance();

    /**
     * Main root node for this .fxml.
     */
    @FXML
    private VBox mainVBox;

    /**
     * The input text field.
     */
    @FXML private TextField costNameTextField;

    /**
     * The current name of the cost item that's getting its name changed.
     */
    private String currentCostItemName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initMainVBox();
        initTextField();
    }

    /**
     * Method for closing the changeCostItemNameView.
     * Puts the changeCostItemNameView to back in scene.
     */
    @FXML
    private void closeChangeCostItemNameView() {
        rootController.closeChangeCostItemNameView();
    }

    /**
     * Method for changeCostItemName button clicked.
     * Changes the name of the cost item.
     */
    @FXML
    private void changeCostItemName() {

        // Cost item name input can't be empty.
        if(costNameTextField.getText().equals("") || costNameTextField.getText() == null) {
            return;
        }

        // Name can't be same as existing cost item in the same category
        for (String costItemName : ProjectManager.getInstance().getActiveCostItemNames()) {
            if (costItemName.toLowerCase().equals(costNameTextField.getText().toLowerCase())) {
                return;
            }
        }

        rootController.renameCostItem(currentCostItemName, costNameTextField.getText());
        rootController.closeChangeCostItemNameView();
    }

    /**
     * Initializes the main root node (the VBox)
     */
    private void initMainVBox() {
        mainVBox.setOnKeyPressed(keyEvent -> {
            // If 'Escape' is pressed then close view
            if(keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                closeChangeCostItemNameView();
            }
        });
    }

    /**
     * Initializes the TextField for cost item name input.
     */
    private void initTextField() {
        // On key pressed event
        costNameTextField.setOnKeyPressed(keyEvent -> {
            // If 'Enter' is pressed then add new cost item.
            if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                changeCostItemName();
            }
        });
    }

    /**
     * Getter for the TextField for cost item name input.
     * @return The TextField.
     */
    public TextField getCostNameTextField() {
        return costNameTextField;
    }

    /**
     * Sets the current name of the cost item to change name of.
     * @param currentCostItemName Current name of the cost item.
     */
    public void setCurrentCostItemName(String currentCostItemName) {
        this.currentCostItemName = currentCostItemName;
    }
}
