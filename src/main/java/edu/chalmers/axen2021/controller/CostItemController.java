package edu.chalmers.axen2021.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    /**
     * The name label in the .fxml file.
     */
    @FXML Label nameLabel;

    /**
     * TextField representing the value of the cost.
     */
    @FXML TextField costValue;

    /**
     * Name of the cost item
     */
    private String name;

    public CostItemController(String name) {
        this.name = name;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(name);
        initTextFieldProperties();
    }


    /**
     * Init method for input TextFields properties.
     * Adds focus lost property.
     * Adds only allowing doubles property.
     */
    private void initTextFieldProperties(){

        //Adds property to TextField allowing users to only input numbers and ".".
        costValue.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*" + "[.]?" + "[0-9]*")){
                costValue.setText(oldValue);
            }
        });

        //Adds focus lost property to textFields.
        costValue.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue){
                //Make sure that the textField has a readable value.
                if(costValue.getText().equals("") || costValue.getText().equals(".")){
                        costValue.setText("0.0");
                }
                //ToDo add method for adding new values to correct variable.

                }
            });
    }

}
