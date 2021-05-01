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
    private transient static ArrayList<Project> projects;

    /**
     * The active project.
     */
    private transient Project activeProject;

    /**
     * The active category.
     */
    private transient Category activeCategory;

    /**
     * A map containing all category lists
     */
    private HashMap<Category, ArrayList<String>> categoryMap = new HashMap<>();

    // Private constructor because Singleton class. Use getInstance() instead.
    private ProjectManager(){
        projects = new ArrayList<>();
        initCategoryMap();
    }

    /**
     * This class acts as a Singleton.
     * Returns the instance of the class.
     * @return Instance of class.
     */
    public static ProjectManager getInstance() {
        if(instance == null) {

            if(!SaveManager.getInstance().projectManagerExists()) {
                instance = new ProjectManager();
            } else {
                instance = SaveManager.getInstance().readProjectManager();
                projects = new ArrayList<>();
            }
        }
        return instance;
    }

    /**
     * Initializes the category map by creating an ArrayList for each category.
     */
    private void initCategoryMap() {
        for (Category category : Category.values()) {
            categoryMap.put(category, new ArrayList<>());
        }
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

        // Throws exception if it did not return in the if-statement above.
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
    public Category getActiveCategory() { return activeCategory; }

    /**
     * Set method for activeCategory
     * @param category category to set
     */
    public void setActiveCategory(Category category) {
        activeCategory = category;
    }

    /**
     * Getter method for the categoryMap, used for getting an category ArrayList.
     * @return The categoryMap
     */
    public HashMap<Category, ArrayList<String>> getCategoryMap() { return categoryMap; }

    /**
     * Getter method to get current categoryList that is visible on screen
     * @return arraylist of the cost categories
     */
    public ArrayList<String> getActiveCategoryList() {
        return categoryMap.get(activeCategory);
    }

    // GETTERS FOR CATEGORY LISTS
    public ArrayList<String> getTomtKostnader() {
        return categoryMap.get(Category.TOMTKOSTNADER);
    }

    public ArrayList<String> getNedlagdaByggherre() {
        return categoryMap.get(Category.NEDLAGDABYGGHERRE);
    }

    public ArrayList<String> getAnslutningar() {
        return categoryMap.get(Category.ANSLUTNINGAR);
    }

    public ArrayList<String> getByggherrekostnader() {
        return categoryMap.get(Category.BYGGHERREKOSTNADER);
    }

    public ArrayList<String> getEntrepenad() {
        return categoryMap.get(Category.ENTREPENAD);
    }

    public ArrayList<String> getOförutsett() {
        return categoryMap.get(Category.OFÖRUTSETT);
    }

    public ArrayList<String> getFinansiellakostnader() {
        return categoryMap.get(Category.FINANSIELLAKOSTNADER);
    }

    public ArrayList<String> getMervärdeskatt() {
        return categoryMap.get(Category.MERVÄRDESKATT);
    }

    public ArrayList<String> getInvesteringsstöd() {
        return categoryMap.get(Category.INVESTERINGSSTÖD);
    }

    public ArrayList<String> getHyresintäkter() {
        return categoryMap.get(Category.HYRESINTÄKTER);
    }

    public ArrayList<String> getDriftOchUnderhåll() {
        return categoryMap.get(Category.DRIFTOCHUNDERHÅLL);
    }

    public ArrayList<String> getTomträttsAvgäld() {
        return categoryMap.get(Category.TOMTRÄTTSAVGÄLD);
    }

    public ArrayList<String> getDriftNetto() {
        return categoryMap.get(Category.DRIFTNETTO);
    }

    public ArrayList<String> getYield() {
        return categoryMap.get(Category.YIELD);
    }
}
