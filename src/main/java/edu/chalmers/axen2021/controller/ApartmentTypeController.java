package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.ApartmentType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class ApartmentTypeController implements Initializable {

    @FXML private MenuButton apartmentTypeMenuButton;

    @FXML private TextField BOATextField;

    @FXML private TextField amountTextField;

    private ApartmentType apartmentType;

    public ApartmentTypeController(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(apartmentType.getApartmentType() != null) {
            apartmentTypeMenuButton.setText(apartmentType.getApartmentType());
        }
        BOATextField.setText(String.valueOf(apartmentType.getBOA()));
        amountTextField.setText(String.valueOf(apartmentType.getAmount()));

        initListeners();
    }

    private void initListeners() {

        for (MenuItem menuItem : apartmentTypeMenuButton.getItems()) {
            menuItem.setOnAction(actionEvent -> {
                apartmentTypeMenuButton.setText(menuItem.getText());
                apartmentType.setApartmentType(menuItem.getText());
            });
        }

        apartmentTypeMenuButton.setOnAction(actionEvent -> {
            System.out.println("HEJ");
        });

        BOATextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {

            if(!newValue.matches("\\d*")){
                BOATextField.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if(!BOATextField.getText().isEmpty()) {
                apartmentType.setBOA(Double.parseDouble(BOATextField.getText()));
            }
        }));

        amountTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {

            if(!newValue.matches("\\d*")){
                amountTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if(!amountTextField.getText().isEmpty()) {
                apartmentType.setAmount(Integer.parseInt(amountTextField.getText()));
            }
        }));
    }
}
