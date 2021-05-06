package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.model.ApartmentItem;
import edu.chalmers.axen2021.model.Project;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

@FXMLController
public class confirmationController {

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

    public void setItemToRemove(String nameObjectToRemove, EventHandlerObjects type) {
        this.nameObjectToRemove = nameObjectToRemove;
        changeConfirmButtonText(type);
    }

    public void setItemToRemove(ApartmentItem item, EventHandlerObjects type) {
        this.item = item;
        changeConfirmButtonText(type);
    }

    public void setEventHandler(EventHandlerObjects type){
        if(type == EventHandlerObjects.COST_ITEM){
            confirmButton.setOnAction(removeCostItemHandler);
        }
        else if (type == EventHandlerObjects.APARTMENT_TYPE){
            confirmButton.setOnAction(removeLagenhetsTypeHandler);
        }
        else if (type == EventHandlerObjects.PROJECT){
            confirmButton.setOnAction(removeProjectHandler);
        }
    }

    private void changeConfirmButtonText(EventHandlerObjects type){

        if(type == EventHandlerObjects.COST_ITEM){
            confirmationBoxLabel.setText("Vill du ta bort " + nameObjectToRemove + "?");
        }
        else if (type == EventHandlerObjects.APARTMENT_TYPE){
            confirmationBoxLabel.setText("Vill du ta bort denna lägenhetstypen?");
        }
        else if (type == EventHandlerObjects.PROJECT){
            confirmationBoxLabel.setText("Vill du ta bort " + nameObjectToRemove + "?");
        }

    }

    @FXML
    private void closeConfirmationView(){
        rootController.closeConfirmationView();
    }
}
