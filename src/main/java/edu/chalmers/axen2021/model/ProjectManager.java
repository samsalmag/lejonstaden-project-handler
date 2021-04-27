package edu.chalmers.axen2021.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectManager implements Serializable {
    private Project activeProject;
    private String activeCategory;

    public void setActiveCategory(String category) {
        activeCategory = category;
    }

    public String getActiveCategory() { return activeCategory; }

    public Project getActiveProject() { return activeProject; }

    /**
     * A list of all projects during runtime.
     * Is updated when a new Project is created, and also when program startup.
     */
    private ArrayList<Project> projects = new ArrayList<>();

    /**
     * Getter for the 'projects' list.
     * @return The 'projects' list.
     */
    public ArrayList<Project> getProjects() { return projects; }

    public void setActiveProject(String project) {
        for (Project p : projects) {
            if(p.getName().equals(project)) {
                activeProject = p;
                return;
            }
        }

        try {
            throw new Exception("Could not set active project from project button name (button name is not a project)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> tomtKostnader = new ArrayList<>();
    private ArrayList<String> nedlagdaByggherre = new ArrayList<>();
    private ArrayList<String> anslutningar = new ArrayList<>();
    private ArrayList<String> byggherrekostnader = new ArrayList<>();
    private ArrayList<String> entrepenad = new ArrayList<>();
    private ArrayList<String> oförutsett = new ArrayList<>();
    private ArrayList<String> finansiellakostnader = new ArrayList<>();
    private ArrayList<String> mervärdeskatt = new ArrayList<>();
    private ArrayList<String> investeringsstöd = new ArrayList<>();
    private ArrayList<String> hyresintäkter = new ArrayList<>();
    private ArrayList<String> driftOchUnderhåll = new ArrayList<>();
    private ArrayList<String> tomträttsAvgäld = new ArrayList<>();
    private ArrayList<String> driftNetto = new ArrayList<>();
    private ArrayList<String> yield = new ArrayList<>();

    public ArrayList<String> getTomtKostnader() { return tomtKostnader; }

    public ArrayList<String> getNedlagdaByggherre() { return nedlagdaByggherre; }

    public ArrayList<String> getAnslutningar() { return anslutningar; }

    public ArrayList<String> getByggherrekostnader() { return byggherrekostnader; }

    public ArrayList<String> getEntrepenad() {
        return entrepenad;
    }

    public ArrayList<String> getOförutsett() {
        return oförutsett;
    }

    public ArrayList<String> getFinansiellakostnader() {
        return finansiellakostnader;
    }

    public ArrayList<String> getMervärdeskatt() {
        return mervärdeskatt;
    }

    public ArrayList<String> getInvesteringsstöd() {
        return investeringsstöd;
    }

    public ArrayList<String> getHyresintäkter() {
        return hyresintäkter;
    }

    public ArrayList<String> getDriftOchUnderhåll() {
        return driftOchUnderhåll;
    }

    public ArrayList<String> getTomträttsAvgäld() {
        return tomträttsAvgäld;
    }

    public ArrayList<String> getDriftNetto() {
        return driftNetto;
    }

    public ArrayList<String> getYield() {
        return yield;
    }

    /**
     * The instance of this class.
     */
    private static ProjectManager instance = null;

    // Private constructor because Singleton class. Use getInstance() instead.
    private ProjectManager(){ }

    /**
     * This class acts as a Singleton.
     * Returns the instance of the class.
     * @return Instance of class.
     */
    public static ProjectManager getInstance() {
        if(instance == null) {
            instance = new ProjectManager();
        }
        return instance;
    }
}
