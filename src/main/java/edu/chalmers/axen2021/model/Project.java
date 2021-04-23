package edu.chalmers.axen2021.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A class for the projects created in the application.
 * Implements the 'Serializable' interface to allow serialization (saving) of this classes data.
 * @author Sam Salek
 */
public class Project implements Serializable {

    /**
     * Name of the project. Is set through the Constructor.
     */
    private String name;

    /**
     * A map containing all input fields and their corresponding values.
     */
    private HashMap<String, String> inputsMap = new HashMap<>();

    /**
     * Class Constructor. Is used when a new instance of this class is created.
     * @param name Name of the project.
     */
    public Project(String name) {
        this.name = name;
        Model.addProject(this);

        // TODO - Remove when input values from the view can be read.
        // TEST VALUES
        addInput("test1", "1");
        addInput("test2", "2");
        addInput("test3", "3");
        addInput("test4", "4");
        addInput("test5", "5");
        addInput("test6", "6");
        addInput("test7", "7");
    }

    /**
     * Method used for adding new input fields (and their values) to the 'inputsMap' map.
     * @param inputId The input fields id.
     * @param value The input fields value
     */
    public void addInput(String inputId, String value){
        inputsMap.put(inputId, value);
    }

    // ------------------ GETTERS ------------------ //
    /**
     * Getter for the 'name' variable.
     * @return The 'name' variable.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the 'inputsMap' map.
     * @return The 'inputsMap' map.
     */
    public HashMap<String, String> getInputsMap() {
        return inputsMap;
    }
}
