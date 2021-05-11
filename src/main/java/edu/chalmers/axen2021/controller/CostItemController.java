package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.projectdata.CostItem;
import edu.chalmers.axen2021.utils.StringUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the applications modalWindowItem.fxml.
 * Handles all event triggered in the modalWindowItem (cost item).
 * @author Sam Salek
 * @author Oscar Arvidson
 */
@FXMLController
public class CostItemController implements Initializable {

    private RootController rootController = RootController.getInstance();

    /**
     * The name label in the .fxml file.
     */
    @FXML private Label nameLabel;

    /**
     * TextField representing the value of the cost.
     */
    @FXML private TextField valueTextField;

    /**
     * TextArea representing the comment for the cost.
     */
    @FXML private TextArea commentTextArea;

    /**
     * A CheckBox representing if moms is enabled for the cost.
     */
    @FXML private CheckBox momsCheckBox;

    /**
     * The cost item for this controller intance.
     */
    private CostItem costItem;

    public CostItemController(CostItem costItem) {
        this.costItem = costItem;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInitialValues();
        initValueTextField();
        initMomsCheckBox();
        initCommentTextArea();
    }

    /**
     * Sets the initial values for the .fxml nodes.
     */
    private void setInitialValues() {
        nameLabel.setText(costItem.getName() + ":");
        valueTextField.setText(StringUtils.removeTrailingZeros(costItem.getValue()));
        momsCheckBox.setSelected(costItem.getMoms());
        commentTextArea.setText(costItem.getComment());
    }

    /**
     * Initializes the valueTextField by adding listeners.
     */
    private void initValueTextField() {
        // Adds property to TextField allowing users to only input numbers and ".".
        valueTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*" + "[.]?" + "[0-9]*")){
                valueTextField.setText(oldValue);
            }
        });

        // Adds focus lost property to textFields.
        valueTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue) {
                //Make sure that the textField has a readable value.
                if(valueTextField.getText().equals("") || valueTextField.getText().equals(".")) {
                    valueTextField.setText("0.0");
                }

                // Remove unnecessary zeroes
                valueTextField.setText(StringUtils.removeTrailingZeros(Double.parseDouble(valueTextField.getText())));
                costItem.setValue(Double.parseDouble(valueTextField.getText()));
            }
        });
    }

    /**
     * Initializes the momsCheckBox by adding listeners.
     */
    private void initMomsCheckBox() {
        // Adds changeListener to the momsCheckBox to update the cost item's value in model.
        momsCheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            costItem.setMoms(momsCheckBox.isSelected());
        });

    }

    /**
     * Initializes the commentTextArea by adding listeners.
     */
    private void initCommentTextArea() {
        // Make sure a comment field is not null.
        commentTextArea.focusedProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(!newValue){
                if(commentTextArea.getText() == null ) {
                    commentTextArea.setText("");
                }
                costItem.setComment(commentTextArea.getText());
            }
        }));
    }

    /**
     * Removes cost item.
     */
    @FXML
    private void openRemoveConfirmation(){
        rootController.openConfirmationView(costItem.getName(), ItemType.COST_ITEM);
    }
}
