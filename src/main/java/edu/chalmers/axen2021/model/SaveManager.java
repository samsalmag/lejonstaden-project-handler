package edu.chalmers.axen2021.model;

import edu.chalmers.axen2021.observers.IInputObserver;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Saves projects to files on the local computer.
 * @author Sam Salek
 * @author Malte Åkvist
 */
public class SaveManager implements IInputObserver {

    /**
     * The instance of this class.
     */
    private static SaveManager instance = null;

    /**
     * Maps for input values and their comments.
     */
    private HashMap<String, Double> valuesMap = new HashMap<>();
    private HashMap<String, String> commentsMap = new HashMap<>();

    // Private constructor because Singleton class.
    private SaveManager(){}

    /**
     * This class acts as a Singleton.
     * Returns the instance of the class.
     * @return
     */
    public static SaveManager getInstance() {
        if(instance == null) {
            instance = new SaveManager();
            instance.init();
        }
        return instance;
    }

    @Override
    public void updateValue(String variableName, Double value) {
        valuesMap.put(variableName, value);
    }

    @Override
    public void updateComment(String variableName, String comment) {
        commentsMap.put(variableName, comment);
    }

    private void init() {
        verifyDirectory();

        // TODO - Remove when Observer is fully implemented.
        // TEST VALUES
        /*
        valuesMap.put("test1", 43.0);
        valuesMap.put("test2", 456.0);
        valuesMap.put("test3", 434.0);
        valuesMap.put("test4", 4.0);
        commentsMap.put("test1", "some value");
        commentsMap.put("test2", "some value noice");
        commentsMap.put("test3", "some value yre");
        commentsMap.put("test4", "some ve");
         */
    }

    /**
     * The path directory for where project are saved.
     * @return The directory.
     */
    private String saveDirectory() {
        return System.getProperty("user.home") + File.separatorChar + ".axen2021";
    }

    /**
     * Get the names of all projects that exists
     * @return Project names.
     */
    public ArrayList<String> getProjectNames() {
        ArrayList<String> projects = new ArrayList<>();

        File projectDir = new File(saveDirectory());
        if (projectDir.isDirectory()) {
            File[] files = projectDir.listFiles();

            for (File currentFile : files) {
                if (!currentFile.isHidden() && currentFile.getName().endsWith(".txt")) {
                    String fileName = currentFile.getName().substring(0, currentFile.getName().lastIndexOf('.'));  // Remove extension .txt of the name
                    projects.add(fileName);
                }
            }
        }
        return projects;
    }

    /**
     * Remove a project file by inputting the project name to be removed
     *
     * @param projectName Name of the project to remove
     * @return Boolean if remove file was successful
     */
    public boolean removeProjectFile(String projectName) {

        File projectFile = new File(saveDirectory() + File.separatorChar + projectName + ".txt");
        return projectFile.delete();
    }

    /**
     * Checks if the save directory exists. One is created if not.
     */
    private void verifyDirectory() {
        File directory;
        try {
            directory = new File(saveDirectory());
            if (!directory.exists()) {
                directory.mkdir();
            }
        } catch (Exception e) {
            System.out.println("SaveManager class creating directory: " + e);
        }
    }

    /**
     * Saves the project and its input values to a .txt file
     */
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
     */
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
}
