package edu.chalmers.axen2021.controller.modals;

import edu.chalmers.axen2021.controller.FXMLController;
import edu.chalmers.axen2021.controller.items.ItemType;
import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller class for confirmationView.fxml.
 * Handles all events triggered by the .fxml file.
 * @author Oscar Arvidson
 * @author Erik Wetter
 * @author Sam Salek
 */
@FXMLController
public class ConfirmationController {

    /**
     * Parent controller.
     */
  private final RootController rootController = RootController.getInstance();

    /**
     * Confirm button in the view.
     */
    @FXML Button confirmButton;

    /**
     * Confirmation Label in the view.
     */
    @FXML Label confirmationBoxLabel;

    /**
     * Name of the object to be removed. Used when it's a cost item or project.
     */
    private String nameObjectToRemove;

    /**
     * The apartment item to be removed. Only used when an apartment item is being removed.
     */
    private ApartmentItem apartmentItem;

    /**
     * EventHandler for when a cost item should be removed.
     */
    private final EventHandler removeCostItemHandler = event -> {
          rootController.removeCostItem(nameObjectToRemove);
          rootController.closeConfirmationView();
    };

    /**
     * EventHandler for when a apartment item should be removed.
     */
    private final EventHandler removeApartmentItemHandler = event -> {
            rootController.removeApartmentItem(apartmentItem);
            rootController.closeConfirmationView();
    };

    /**
     * EventHandler for when a project should be removed.
     */
    private final EventHandler removeProjectHandler = event -> {
            rootController.removeProject(nameObjectToRemove);
            rootController.closeConfirmationView();
    };

    /**
     * Sets the item to be removed. Use this for removal of projects and cost items.
     * @param nameObjectToRemove Name of the cost item / project.
     * @param type Type of the item to be removed (cost item / project).
     */
    public void setItemToRemove(String nameObjectToRemove, ItemType type) {
        this.nameObjectToRemove = nameObjectToRemove;
        changeConfirmButtonText(type);
        setEventHandler(type);
    }

    /**
     * Sets the apartment item to be removed. Use this for removal of ApartmentItems.
     * @param apartmentItem The apartmentItem.
     */
    public void setItemToRemove(ApartmentItem apartmentItem) {
        this.apartmentItem = apartmentItem;
        changeConfirmButtonText(ItemType.APARTMENT_ITEM);
        setEventHandler(ItemType.APARTMENT_ITEM);
    }

    /**
     * Sets which EventHandler to be used based on the ItemType of the item being removed.
     * @param type Type of the item being removed.
     */
    private void setEventHandler(ItemType type){
        // Decide action for confirmButton based on the ItemType.
        if(type == ItemType.COST_ITEM){
            confirmButton.setOnAction(removeCostItemHandler);
        }
        else if (type == ItemType.APARTMENT_ITEM){
            confirmButton.setOnAction(removeApartmentItemHandler);
        }
        else if (type == ItemType.PROJECT){
            confirmButton.setOnAction(removeProjectHandler);
        }
    }

    /**
     * Changes the confirmation label text based on which ItemType is being removed.
     * @param type Type of the item being removed.
     */
    private void changeConfirmButtonText(ItemType type){
        // Decide confirmation text based on ItemType.
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

    /**
     * Closes the confirmation view.
     */
    @FXML
    private void closeConfirmationView(){
        rootController.closeConfirmationView();
    }
}
