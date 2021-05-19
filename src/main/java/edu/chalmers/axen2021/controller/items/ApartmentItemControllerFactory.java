package edu.chalmers.axen2021.controller.items;

import edu.chalmers.axen2021.model.projectdata.ApartmentItem;

import java.util.ArrayList;

/**
 * Factory class for ApartmentItemController.
 * Used to create instances of ApartmentItemController.
 * Also contains a list of all instances of the controller class.
 *
 * @author Sam Salek
 */
public class ApartmentItemControllerFactory {

    /**
     * List of all instances of the controller class.
     */
    private static ArrayList<ApartmentItemController> instances = new ArrayList<>();

    // Doesn't need to be instantiated or a have reference. Use methods directly instead.
    private ApartmentItemControllerFactory(){}

    /**
     * Creates a new instance of ApartmentItemController.
     * @param apartmentItem The model ApartmentItem class for this specific controller instance.
     * @return The newly created instance of ApartmentItemController.
     */
    public static ApartmentItemController create(ApartmentItem apartmentItem) {
        ApartmentItemController aic = new ApartmentItemController(apartmentItem);
        instances.add(aic);
        return aic;
    }

    /**
     * Removes all instances of the active controllers.
     * Mostly used when switching between views/projects.
     * Called to to avoid references to controllers whose views got removed at view/project switch.
     */
    public static void clearInstances() {
        instances.clear();
    }

    /**
     * Gets the instances of the active controllers.
     * @return All instances.
     */
    public static ArrayList<ApartmentItemController> getInstances() {
        return instances;
    }
}
