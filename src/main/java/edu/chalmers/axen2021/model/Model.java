package edu.chalmers.axen2021.model;

import java.util.ArrayList;

public class Model {

    private static ArrayList<Project> projects = new ArrayList<>();

    // TODO - make classes below Singleton? and then use getInstance() instead of 'new ...'.
    private static ApartmentData apartmentData = new ApartmentData();
    private static ProjectCosts projectCosts = new ProjectCosts();
    private static SaboTable saboTable = new SaboTable();

    public static void addProject(Project project) {
        projects.add(project);
    }

    // ------------------ GETTERS ------------------ //

    public static ArrayList<Project> getProjects() {
        return projects;
    }

    public ApartmentData getApartmentData() {
        return apartmentData;
    }

    public ProjectCosts getProjectCosts() {
        return projectCosts;
    }

    public SaboTable getSaboTable() {
        return saboTable;
    }
}
