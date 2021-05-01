package edu.chalmers.axen2021.model;

/**
 * The main class for access to the applications model.
 * @author Sam Salek
 */
public class Model {

    private static Model instance = null;

    private static ApartmentData apartmentData = new ApartmentData();
    private static ProjectCosts projectCosts = new ProjectCosts();
    private static SaboTable saboTable = new SaboTable();

    // Singleton class. Use getInstance() to get access.
    private Model(){}

    /**
     * This class acts as a Singleton.
     * Returns the instance of the class.
     * @return Instance of class.
     */
    public static Model getInstance() {
        if(instance == null) {
            instance = new Model();
        }
        return instance;
    }

    // ------------------ GETTERS ------------------ //

    /**
     * Getter for the 'ApartmentData' class.
     * @return The 'ApartmentData' class.
     */
    public ApartmentData getApartmentData() {
        return apartmentData;
    }

    /**
     * Getter for the 'ProjectCosts' class.
     * @return The 'ProjectCosts' class.
     */
    public ProjectCosts getProjectCosts() {
        return projectCosts;
    }

    /**
     * Getter for the 'SaboTable' class.
     * @return The 'SaboTable' class.
     */
    public SaboTable getSaboTable() {
        return saboTable;
    }
}
