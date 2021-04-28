package edu.chalmers.axen2021.model;

import java.io.*;
import java.util.ArrayList;

/**
 * Saves projects within the application to files on the local computer.
 * @author Sam Salek
 * @author Malte Åkvist
 */
public class SaveManager {

    /**
     * The instance of this class.
     */
    private static SaveManager instance = null;

    /**
     * The file type used to create save files, and save projects.
     */
    private static String fileType = ".axen";

    // Private constructor because Singleton class. Use getInstance() instead.
    private SaveManager(){}

    /**
     * This class acts as a Singleton.
     * Returns the instance of the class.
     * @return Instance of class.
     */
    public static SaveManager getInstance() {
        if(instance == null) {
            instance = new SaveManager();
            instance.init();
        }
        return instance;
    }

    /**
     * Initializes this class by running necessary methods.
     */
    private void init() {
        verifyDirectory();
    }

    /**
     * The path directory for where project are saved.
     * @return The directory.
     */
    private String getSaveDirectory() {
        return getDirectory() + File.separatorChar + "projects";
    }

    /**
     * The path directory for where all data for the application is saved.
     * @return The directory.
     */
    private String getDirectory() {
        return System.getProperty("user.home") + File.separatorChar + ".axen2021";
    }

    /**
     * Checks if the save directory exists. One is created if not.
     */
    private void verifyDirectory() {
        File directory;
        try {
            directory = new File(getSaveDirectory());
            if (!directory.exists()) {
                directory.mkdirs();
            }
        } catch (Exception e) {
            System.out.println("SaveManager class creating directory: " + e);
        }
    }

    public boolean projectManagerExists() {
        File projectManager;
        try {
            projectManager = new File(getDirectory() + File.separatorChar + "ProjectManager" + fileType);
            if(!projectManager.exists()) {
                System.out.println("ProjectManager" + fileType + " is missing!");
                return false;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        System.out.println("Found ProjectManager" + fileType + "!");
        return true;
    }

    /**
     * Get the names of all projects that exists in save directory.
     * @return Project names.
     */
    public ArrayList<String> getProjectNames() {
        ArrayList<String> projects = new ArrayList<>();

        File projectDir = new File(getSaveDirectory());
        if (projectDir.isDirectory()) {
            File[] files = projectDir.listFiles();

            for (File currentFile : files) {
                if (!currentFile.isHidden() && currentFile.getName().endsWith(fileType)) {
                    String fileName = currentFile.getName().substring(0, currentFile.getName().lastIndexOf('.'));  // Remove extension .txt of the name
                    projects.add(fileName);
                }
            }
        }
        return projects;
    }

    /**
     * Saves the given project and its values to a .axen file.
     * @param project The project to save.
     */
    public void saveProject(Project project)  {
        String filename = getSaveDirectory() + File.separatorChar + project.getName() + fileType;
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            oos.writeObject(project);
            oos.close();
            fileOut.close();
            System.out.println("Serialized data is saved for project " + project.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the projectManager and its values to a .axen file.
     */
    public void saveProjectManager()  {
        String filename = getDirectory() + File.separatorChar + "ProjectManager" + fileType;
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            oos.writeObject(ProjectManager.getInstance());
            oos.close();
            fileOut.close();
            System.out.println("Serialized data is saved for project " + "ProjectManager");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads all projects in the save directory.
     * @return Returns a list of all read projects.
     */
    public ArrayList<Project> readProjects()  {
        ArrayList<Project> projects = new ArrayList<>();

        for (String projectName : getProjectNames()) {
            Project project = readProject(projectName);
            projects.add(project);
        }

        return projects;
    }

    /**
     * Reads a single project from the given project name.
     * @param projectName Project name.
     * @return Returns the read project.
     */
    public Project readProject(String projectName)  {
        String filename = getSaveDirectory() + File.separatorChar + projectName + fileType;
        Project project = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fileIn);
            project = (Project) ois.readObject();
            ois.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return project;
    }

    /**
     * Reads the ProjectManager file from directory.
     * @return Returns the read ProjectManager.
     */
    public ProjectManager readProjectManager()  {
        String filename = getDirectory() + File.separatorChar + "ProjectManager" + fileType;
        ProjectManager projectManager = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fileIn);
            projectManager = (ProjectManager) ois.readObject();
            ois.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectManager;
    }

    /**
     * Remove a project file by inputting the project name to be removed
     * @param projectName Name of the project to remove
     * @return Boolean if remove file was successful
     */
    public boolean removeProjectFile(String projectName) {
        File projectFile = new File(getSaveDirectory() + File.separatorChar + projectName + fileType);
        return projectFile.delete();
    }
}
