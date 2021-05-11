package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

@FXMLController
public class ConfirmationController {

  private RootController rootController = RootController.getInstance();

    @FXML Button confirmButton;

    @FXML Label confirmationBoxLabel;

    private String nameObjectToRemove;

    private ApartmentItem item;

    private EventHandler<ActionEvent> removeCostItemHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
          rootController.removeCostItem(nameObjectToRemove);
          rootController.closeConfirmationView();
        }
    };

    private EventHandler<ActionEvent> removeLagenhetsTypeHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
            rootController.removeApartmentItem(item);
            rootController.closeConfirmationView();
        }
    };

    private EventHandler<ActionEvent> removeProjectHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
            rootController.removeProject(nameObjectToRemove);
            rootController.closeConfirmationView();
        }
    };

    public void setItemToRemove(String nameObjectToRemove, ItemType type) {
        this.nameObjectToRemove = nameObjectToRemove;
        changeConfirmButtonText(type);
    }

    public void setItemToRemove(ApartmentItem item, ItemType type) {
        this.item = item;
        changeConfirmButtonText(type);
    }

    public void setEventHandler(ItemType type){
        if(type == ItemType.COST_ITEM){
            confirmButton.setOnAction(removeCostItemHandler);
        }
        else if (type == ItemType.APARTMENT_ITEM){
            confirmButton.setOnAction(removeLagenhetsTypeHandler);
        }
        else if (type == ItemType.PROJECT){
            confirmButton.setOnAction(removeProjectHandler);
        }
    }

    private void changeConfirmButtonText(ItemType type){

        if(type == ItemType.COST_ITEM){
            confirmationBoxLabel.setText("Vill du ta bort " + nameObjectToRemove + "?");
        }
        else if (type == ItemType.APARTMENT_ITEM){
            confirmationBoxLabel.setText("Vill du ta bort denna lägenhetstypen?");
        }
        else if (type == ItemType.PROJECT){
            confirmationBoxLabel.setText("Vill du ta bort " + nameObjectToRemove + "?");
        }

    }

    @FXML
    private void closeConfirmationView(){
        rootController.closeConfirmationView();
    }
}
