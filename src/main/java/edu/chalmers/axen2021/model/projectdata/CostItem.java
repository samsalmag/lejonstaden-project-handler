package edu.chalmers.axen2021.model.projectdata;

import java.io.Serializable;

/**
 * Class used to store values for costs for the different categories in the application.
 * Implements the 'Serializable' interface to allow serialization (saving) of this classes data.
 * @author Sam Salek
 * @author Malte Åkvist
 */
public class CostItem implements Serializable {

    /**
     * Name of the cost item.
     */
    private String name;

    /**
     * The value inputted to the cost item.
     */
    private double value;

    /**
     * The comment inputted to the cost item.
     */
    private String comment;

    /**
     * If moms should be considered for the cost item.
     */
    private boolean moms;

    // Not instantiable outside 'projectdata' package. Add a CostItem through Project class instead.
    CostItem(String name) {
        this.name = name;
        moms = true;
    }

    // Getter and setter methods for all variables
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getValue() { return value; }

    public void setValue(double value) { this.value = value; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public boolean getMoms() { return moms; }

    public void setMoms(boolean moms) { this.moms = moms; }
}
