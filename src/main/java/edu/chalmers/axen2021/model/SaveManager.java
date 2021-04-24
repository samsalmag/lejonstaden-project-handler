package edu.chalmers.axen2021.model;

import java.io.*;
import java.util.ArrayList;

/**
 * Saves projects to files on the local computer.
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

    // Private constructor because Singleton class.
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
    private static String getSaveDirectory() {
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
                directory.mkdir();
            }
        } catch (Exception e) {
            System.out.println("SaveManager class creating directory: " + e);
        }
    }

    /**
     * Get the names of all projects that exists
     * @return Project names.
     */
    public static ArrayList<String> getProjectNames() {
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
    public static void saveProject(Project project)  {
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
     * Reads all projects in the save directory.
     * @return Returns a list of all read projects.
     */
    public static ArrayList<Project> readProjects()  {
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
    public static Project readProject(String projectName)  {
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
     * Remove a project file by inputting the project name to be removed
     * @param projectName Name of the project to remove
     * @return Boolean if remove file was successful
     */
    public static boolean removeProjectFile(String projectName) {
        File projectFile = new File(getSaveDirectory() + File.separatorChar + projectName + fileType);
        return projectFile.delete();
    }

    // --------------------------- OLD SAVE SYSTEM BELOW --------------------------- //
    // TODO - Remove or uncomment when save system is set

    /*
    /**
     * Maps for input values and their comments.

    private HashMap<String, Double> valuesMap = new HashMap<>();
    private HashMap<String, String> commentsMap = new HashMap<>();
    */

    /*
    /**
     * Saves the project and its input values to a .txt file

    public void saveProject()  {

        // TODO - filename should be replaced with the correct project name.
        Date date = new Date();
        String filename = saveDirectory() + File.separatorChar + "project_" + date.getTime() +".txt";
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "ISO-8859-1");

            valuesMap.forEach((key, value) -> writeToFile(osw, key, value));
            osw.write("\n");
            commentsMap.forEach((key, value) -> writeToFile(osw, key, value));
            osw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes given key and its value to the given OutputStreamWriter.
     * @param osw OutputStreamWriter.
     * @param key Key Object.
     * @param value Value Object.

    private void writeToFile(OutputStreamWriter osw, Object key, Object value) {
        String line;
        line = "" + key + ":" + value + "\n";
        try {
            osw.write(line);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
