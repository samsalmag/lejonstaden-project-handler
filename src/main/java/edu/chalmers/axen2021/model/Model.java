package edu.chalmers.axen2021.model;

import java.util.ArrayList;

/**
 * The main class for access to the applications model.
 * @author Sam Salek
 */
public class Model {

    private static Model instance = null;

    /**
     * A list of all projects during runtime.
     * Is updated when a new Project is created, and also when program startup.
     */
    private static ArrayList<Project> projects = new ArrayList<>();

    // TODO - maybe make classes below Singleton? and then use getInstance() instead of 'new ...'?.
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

    /**
     * Used to add a project to the 'projects' list.
     * @param project The project to be added.
     */
    public void addProject(Project project) {
        projects.add(project);
    }

    // ------------------ GETTERS ------------------ //
    /**
     * Getter for the 'projects' list.
     * @return The 'projects' list.
     */
    public ArrayList<Project> getProjects() {
        return projects;
    }

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
