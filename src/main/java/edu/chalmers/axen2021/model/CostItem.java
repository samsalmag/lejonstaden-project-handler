package edu.chalmers.axen2021.model;

import java.io.Serializable;

/**
 * Class used to store values for costs for the different categories in the application.
 * Implements the 'Serializable' interface to allow serialization (saving) of this classes data.
 * @author Sam Salek
 * @author Malte Åkvist
 */
public class CostItem implements Serializable {
    private String comment;
    private double value;
    private String name;
    private boolean moms;

    public CostItem(String name) {
        this.name = name;
        moms = true;
    }

    // Getter and setter methods for all variables
    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public double getValue() { return value; }

    public void setValue(double value) { this.value = value; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean getMoms() { return moms; }

    public void setMoms(boolean moms) { this.moms = moms; }
}
