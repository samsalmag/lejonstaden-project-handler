package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.CostItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * Controller class for the applications modalWindowItem.fxml.
 * Handles all event triggered in the modalWindowItem (cost item).
 * @author Sam Salek
 */
@FXMLController
public class CostItemController implements Initializable {

    /**
     * The name label in the .fxml file.
     */
    @FXML private Label nameLabel;

    @FXML private TextField valueTextField;

    @FXML private TextArea commentTextArea;

    /**
     * The cost item
     */
    private CostItem costItem;

    public CostItemController(CostItem costItem) {
        this.costItem = costItem;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(costItem.getName());

        DecimalFormat df = new DecimalFormat("###");
        valueTextField.setText(df.format(costItem.getValue()));

        commentTextArea.setText(costItem.getComment());
        initListeners();
    }

    private void initListeners() {
        valueTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {

            if(!newValue.matches("\\d*")){
                valueTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if(!valueTextField.getText().isEmpty()) {
                costItem.setValue(Double.parseDouble(valueTextField.getText()));
            }
        }));

        commentTextArea.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(!commentTextArea.getText().isEmpty()) {
                costItem.setComment(commentTextArea.getText());
            }
        }));
    }
}
