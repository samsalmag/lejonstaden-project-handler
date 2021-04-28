package edu.chalmers.axen2021.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class used to manage projects such as how many cost categories there should be.
 * Implements the 'Serializable' interface to allow serialization (saving) of this classes data.
 * @author Sam Salek
 * @author Malte Åkvist
 */
public class ProjectManager implements Serializable {

    /**
     * Used when deserializing, acts as this class' ID.
     */
    private static final long serialVersionUID = 10L;

    /**
     * The instance of this class.
     */
    private transient static ProjectManager instance = null;

    /**
     * A list of all projects during runtime.
     * Is updated when a new Project is created, and also when program startup.
     */
    private transient ArrayList<Project> projects = new ArrayList<>();

    private transient Project activeProject;
    private transient String activeCategory;

    private HashMap<String, ArrayList<String>> categoryListMap = new HashMap<>();

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

    // Private constructor because Singleton class. Use getInstance() instead.
    private ProjectManager(){

        // Put all categories names in categoryListMap with the corresponding arraylist
        categoryListMap.put("Tomtkostnader", tomtKostnader);
        categoryListMap.put("Nedlagda bygherre", nedlagdaByggherre);
        categoryListMap.put("Anslutningar", anslutningar);
        categoryListMap.put("Byggherrekostnader", byggherrekostnader);
        categoryListMap.put("Entrepenad", entrepenad);
        categoryListMap.put("Oförutsett", oförutsett);
        categoryListMap.put("Finansiella kostnader", finansiellakostnader);
        categoryListMap.put("Investeringsstöd", investeringsstöd);
        categoryListMap.put("Hyresintäkter", hyresintäkter);
        categoryListMap.put("Drift & Underhåll", driftOchUnderhåll);
        categoryListMap.put("Tomträttsavgäld", tomträttsAvgäld);
        categoryListMap.put("Driftnetto", driftNetto);
        categoryListMap.put("Yield", yield);
    }

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

    /**
     * Used by the 'Serializable' interface when deserializing.
     * Makes sure the Singleton pattern is kept.
     */
    protected Object readResolve() {
        return getInstance();
    }

    /**
     * Setter method used to set activeProject variable
     * @param project the project that should be active
     */
    public void setActiveProject(String project) {
        for (Project p : projects) {
            if(p.getName().equals(project)) {
                activeProject = p;
                System.out.println("Set project \"" + p.getName() + "\" as the active project!");
                return;
            }
        }

        try {
            throw new Exception("Could not set active project from project button name (button name is not a project)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for the 'projects' list.
     * @return The 'projects' list.
     */
    public ArrayList<Project> getProjects() { return projects; }

    /**
     * Getter method used to get active project
     * @return current active project
     */
    public Project getActiveProject() { return activeProject; }

    /**
     * Get method for the active category
     * @return current active category
     */
    public String getActiveCategory() { return activeCategory; }

    /**
     * Set method for activeCategory
     * @param category category to set
     */
    public void setActiveCategory(String category) {
        activeCategory = category;
    }

    /**
     * Getter method to get the categoryListMap used for getting the category array from a category
     * @return the categoryListMap
     */
    public HashMap<String, ArrayList<String>> getCategoryListMap() { return categoryListMap; }

    /**
     * Getter method to get current categoryList that is visible on screen
     * @return arraylist of the cost categories
     */
    public ArrayList<String> getActiveCategoryList() {
        return categoryListMap.get(getActiveCategory());
    }

    /**
     * Getter for the 'tomtKostnader' list.
     * @return The 'tomtKostnader' list.
     */
    public ArrayList<String> getTomtKostnader() { return tomtKostnader; }

    /**
     * Getter for the 'nedlagdaByggherre' list.
     * @return The 'nedlagdaByggherre' list.
     */
    public ArrayList<String> getNedlagdaByggherre() { return nedlagdaByggherre; }

    /**
     * Getter for the 'anslutningar' list.
     * @return The 'anslutningar' list.
     */
    public ArrayList<String> getAnslutningar() { return anslutningar; }

    /**
     * Getter for the 'byggherrekostnader' list.
     * @return The 'byggherrekostnader' list.
     */
    public ArrayList<String> getByggherrekostnader() { return byggherrekostnader; }

    /**
     * Getter for the 'entrepenad' list.
     * @return The 'entrepenad' list.
     */
    public ArrayList<String> getEntrepenad() {
        return entrepenad;
    }

    /**
     * Getter for the 'oförutsett' list.
     * @return The 'oförutsett' list.
     */
    public ArrayList<String> getOförutsett() {
        return oförutsett;
    }

    /**
     * Getter for the 'finansiellakostnader' list.
     * @return The 'finansiellakostnader' list.
     */
    public ArrayList<String> getFinansiellakostnader() {
        return finansiellakostnader;
    }

    /**
     * Getter for the 'mervärdeskatt' list.
     * @return The 'mervärdeskatt' list.
     */
    public ArrayList<String> getMervärdeskatt() {
        return mervärdeskatt;
    }

    /**
     * Getter for the 'investeringsstöd' list.
     * @return The 'investeringsstöd' list.
     */
    public ArrayList<String> getInvesteringsstöd() {
        return investeringsstöd;
    }

    /**
     * Getter for the 'hyresintäkter' list.
     * @return The 'hyresintäkter' list.
     */
    public ArrayList<String> getHyresintäkter() {
        return hyresintäkter;
    }

    /**
     * Getter for the 'driftOchUnderhåll' list.
     * @return The 'driftOchUnderhåll' list.
     */
    public ArrayList<String> getDriftOchUnderhåll() {
        return driftOchUnderhåll;
    }

    /**
     * Getter for the 'tomträttsAvgäld' list.
     * @return The 'tomträttsAvgäld' list.
     */
    public ArrayList<String> getTomträttsAvgäld() {
        return tomträttsAvgäld;
    }

    /**
     * Getter for the 'driftNetto' list.
     * @return The 'driftNetto' list.
     */
    public ArrayList<String> getDriftNetto() {
        return driftNetto;
    }

    /**
     * Getter for the 'yield' list.
     * @return The 'yield' list.
     */
    public ArrayList<String> getYield() {
        return yield;
    }
}
