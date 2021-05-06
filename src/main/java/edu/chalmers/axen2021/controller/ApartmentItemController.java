package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.ApartmentItem;
import edu.chalmers.axen2021.utils.StringUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the applications lagenhetsDataItem.
 * @author Oscar Arvidson
 * @author Sam Salek
 */
@FXMLController
public class ApartmentItemController implements Initializable {

    @FXML private MenuButton apartmentTypeMenuButton;

    @FXML private TextField BOATextField;

    @FXML private TextField amountTextField;

    /**
     * The type of apartment for the object instance.
     */
    private ApartmentItem apartmentItem;

    /**
     * Constructor for ApartmentTypeController.
     * @param apartmentItem type of apartment.
     */
    public ApartmentItemController(ApartmentItem apartmentItem) {
        this.apartmentItem = apartmentItem;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(apartmentItem.getApartmentType() != null) {
            apartmentTypeMenuButton.setText(apartmentItem.getApartmentType());
        }
        BOATextField.setText(String.valueOf(apartmentItem.getBOA()));
        amountTextField.setText(String.valueOf(apartmentItem.getAmount()));

        initTextFieldProperties();
    }


    /**
     * Init method for input TextFields properties.
     * Adds focus lost property.
     * Adds only allowing doubles property.
     */
    private void initTextFieldProperties(){

        for (MenuItem menuItem : apartmentTypeMenuButton.getItems()) {
            menuItem.setOnAction(actionEvent -> {
                apartmentTypeMenuButton.setText(menuItem.getText());
                apartmentItem.setApartmentType(menuItem.getText());
            });
        }

        //Adds property to TextField allowing users to only input numbers and ".".
        BOATextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*" + "[.]?" + "[0-9]*")){
                BOATextField.setText(oldValue);
            }
        });

        //Adds focus lost property to textFields.
        BOATextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue){
                //Make sure that the textField has a readable value.
                if(BOATextField.getText().equals("") || BOATextField.getText().equals(".")){
                    BOATextField.setText("0.0");
                }
                // Remove unnecessary zeroes
                BOATextField.setText(StringUtils.removeTrailingZeros(Double.parseDouble(BOATextField.getText())));

                apartmentItem.setBOA(Double.parseDouble(BOATextField.getText()));
            }
        });

        //Adds property to TextField allowing users to only input numbers and.
        amountTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")){
                amountTextField.setText(oldValue);
            }
        });

        //Adds focus lost property to textFields.
        amountTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue){
                //Make sure that the textField has a readable value.
                if(amountTextField.getText().equals("")){
                    amountTextField.setText("0");
                }

                // Remove unnecessary zeroes
                amountTextField.setText(StringUtils.removeTrailingZeros(Double.parseDouble(amountTextField.getText())));
                apartmentItem.setAmount(Integer.parseInt(amountTextField.getText()));

            }
        });
    }
}
