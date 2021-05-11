package edu.chalmers.axen2021.model.managers;

import edu.chalmers.axen2021.model.Category;
import edu.chalmers.axen2021.model.projectdata.Project;

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
     * Should not contain duplicates.
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
    private HashMap<Category, ArrayList<String>> costItemNamesMap = new HashMap<>();

    // Private constructor because Singleton class. Use getInstance() instead.
    private ProjectManager(){
        projects = new ArrayList<>();
        initCostItemNamesMap();
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
     * Initializes the map by creating an ArrayList for each category.
     */
    private void initCostItemNamesMap() {
        for (Category category : Category.values()) {
            costItemNamesMap.put(category, new ArrayList<>());
        }
    }

    /**
     * Loads the projects existing in the save directory into the application.
     */
    public void loadProjects() {
        ArrayList<Project> readProjects = SaveManager.getInstance().readProjects();

        // If no projects were read then don't continue.
        if(readProjects.size() == 0) {
            return;
        }

        for (Project project : readProjects) {
            addProject(project);      // Add project to 'projects' list during load.
        }

        // TODO - Remove line below...maybe? Depends on if a project should be 'chosen' on startup.
        setActiveProject(getProjects().get(0).getName());     // Sets first loaded project as the active project.
    }

    /**
     * Used to add a project to the 'projects' list.
     * @param project The project to be added.
     */
    public void addProject(Project project) {
        if(projects.contains(project)) {
            throw new IllegalArgumentException("This project already exists in the 'projects' list!");
        }

        projects.add(project);
    }

    /**
     * Removes a project from the application.
     * @param projectName The name of the project to remove.
     * @return Returns True if remove was successful, False if not.
     */
    public boolean removeProject(String projectName) {
        for(Project p: projects){
            if(p.getName().equals(projectName)){
                projects.remove(p);
                return SaveManager.getInstance().removeProjectFile(p);
            }
        }
        throw new IllegalArgumentException("This project does not exist in 'projects' list!");
    }

    /**
     * Getter method used to get project from project name
     * @param projectName the project that should be returned from projectName
     */
    public Project getProject(String projectName) {
        if(projectName == null) {
            return null;
        }

        for (Project p : projects) {
            if(p.getName().equals(projectName)) {
                return p;
            }
        }

        // Throws exception if it did not return in the if-statement above.
        throw new IllegalArgumentException("Could not set active project from project button name (button name is not a project)");
    }

    /**
     * Setter method used to set activeProject variable
     * @param project the project that should be active
     */
    public void setActiveProject(String project) {
        if(project == null) {
            activeProject = null;
            return;
        }

        for (Project p : projects) {
            if(p.getName().equals(project)) {
                activeProject = p;
                System.out.println("Set project \"" + p.getName() + "\" as the active project!");
                return;
            }
        }

        // Throws exception if it did not return in the if-statement above.
        throw new IllegalArgumentException("Could not set active project from project button name (button name is not a project)");
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
    public HashMap<Category, ArrayList<String>> getCostItemNamesMap() { return costItemNamesMap; }

    /**
     * Getter method to get current category's cost item names.
     * @return ArrayList of the names of the cost items.
     */
    public ArrayList<String> getActiveCostItemNames() {
        return costItemNamesMap.get(activeCategory);
    }

    // GETTERS FOR COST ITEM NAMES LISTS
    public ArrayList<String> getTomtkostnader() {
        return costItemNamesMap.get(Category.TOMTKOSTNADER);
    }

    public ArrayList<String> getNedlagdaByggherre() {
        return costItemNamesMap.get(Category.NEDLAGDABYGGHERRE);
    }

    public ArrayList<String> getAnslutningar() {
        return costItemNamesMap.get(Category.ANSLUTNINGAR);
    }

    public ArrayList<String> getByggherrekostnader() {
        return costItemNamesMap.get(Category.BYGGHERREKOSTNADER);
    }

    public ArrayList<String> getEntrepenad() {
        return costItemNamesMap.get(Category.ENTREPENAD);
    }

    public ArrayList<String> getFinansiellaKostnader() {
        return costItemNamesMap.get(Category.FINANSIELLAKOSTNADER);
    }

    public ArrayList<String> getHyresintäkter() {
        return costItemNamesMap.get(Category.HYRESINTÄKTER);
    }

    public ArrayList<String> getDriftOchUnderhåll() {
        return costItemNamesMap.get(Category.DRIFTOCHUNDERHÅLL);
    }
}
