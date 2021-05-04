package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.ApartmentType;
import edu.chalmers.axen2021.utils.StringUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@FXMLController
public class ApartmentTypeController implements Initializable {

    @FXML private MenuButton apartmentTypeMenuButton;

    @FXML private TextField BOATextField;

    @FXML private TextField amountTextField;

    private ApartmentType apartmentType;

    private ArrayList<TextField> inputFields = new ArrayList<TextField>();

    public ApartmentTypeController(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BOATextField.setText(String.valueOf(apartmentType.getBOA()));
        amountTextField.setText(String.valueOf(apartmentType.getAmount()));

        initTextFieldProperties();
    }


    /**
     * Init method for input TextFields properties.
     * Adds focus lost property.
     * Adds only allowing doubles property.
     */
    private void initTextFieldProperties(){

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
                // ToDo remove comment when method implemented.
                // BOATextField.setText(StringUtils.removeTrailingZeros(BOATextField.getText()));
                apartmentType.setBOA(Double.parseDouble(BOATextField.getText()));

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
                // ToDo remove comment when method implemented.
                // amountTextField.setText(StringUtils.removeTrailingZeros(amountTextField.getText()));
                apartmentType.setAmount(Integer.parseInt(amountTextField.getText()));

            }
        });
    }
}

