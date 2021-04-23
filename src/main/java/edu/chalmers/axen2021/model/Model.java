package edu.chalmers.axen2021.model;

import java.util.ArrayList;

/**
 * The main class for access to the applications model.
 * @author Sam Salek
 */
public class Model {

    /**
     * A list of all projects during runtime.
     * Is updated when a new Project is created, and also when program startup.
     */
    private static ArrayList<Project> projects = new ArrayList<>();

    // TODO - maybe make classes below Singleton? and then use getInstance() instead of 'new ...'?.
    private static ApartmentData apartmentData = new ApartmentData();
    private static ProjectCosts projectCosts = new ProjectCosts();
    private static SaboTable saboTable = new SaboTable();

    /**
     * Used to add a project to the 'projects' list.
     * @param project The project to be added.
     */
    public static void addProject(Project project) {
        projects.add(project);
    }

    // ------------------ GETTERS ------------------ //
    /**
     * Getter for the 'projects' list.
     * @return The 'projects' list.
     */
    public static ArrayList<Project> getProjects() {
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
