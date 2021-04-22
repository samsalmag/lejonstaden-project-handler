package edu.chalmers.axen2021.model;

import java.io.Serializable;
import java.util.HashMap;

public class Project implements Serializable {

    private String name;
    private HashMap<String, String> inputsMap = new HashMap<>();

    public Project(String name) {
        this.name = name;

        addInput("test1", "1");
        addInput("test2", "2");
        addInput("test3", "3");
        addInput("test4", "4");
        addInput("test5", "5");
        addInput("test6", "6");
        addInput("test7", "7");
    }

    public void addInput(String inputId, String value){
        inputsMap.put(inputId, value);
    }


    // ------------------ GETTERS ------------------ //
    public String getName() {
        return name;
    }

    public HashMap<String, String> getInputsMap() {
        return inputsMap;
    }
}
