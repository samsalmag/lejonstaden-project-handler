package edu.chalmers.axen2021.model.managers;

import edu.chalmers.axen2021.model.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The main class for access to the applications model.
 * @author Sam Salek
 */
public class CalculationsManager implements Serializable {

    private static CalculationsManager calculationsManager = null;

    private CalculationsManager() {}

    public static CalculationsManager getInstance() {
        if(calculationsManager == null) {
            calculationsManager = new CalculationsManager();
        }
        return calculationsManager;
    }

    private static ApartmentData apartmentData = new ApartmentData();
    private static ProjectCosts projectCosts = new ProjectCosts();

    //Update methods
    public double updateTotalBoa(ArrayList<ApartmentItem> apartments) {
        double totalBoa = 0;

        for(ApartmentItem apartmentItem : apartments) {
            totalBoa += (apartmentItem.getBOA()* apartmentItem.getAmount());
        }

        return totalBoa;
    }

    public double updateNumberOfApt(ArrayList<ApartmentItem> apartments) {
        double numOfApt = 0;

        for(ApartmentItem apartmentItem : apartments) {
            numOfApt += apartmentItem.getAmount();
        }

        return numOfApt;
    }

    public double updatedTomtkostnaderKkr(ArrayList<CostItem> tomtkostnader) {
        return projectCosts.getTotalCost(tomtkostnader);
    }

    public double updatedTomtkostnaderKrBoa(double tomtkostnaderKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(tomtkostnaderKkr, totalBoa);
    }

    public double updatedTomtkostnaderKrBta(double tomtkostnaderKkr, double totalBta) {
        return projectCosts.getCostPerBta(tomtkostnaderKkr, totalBta);
    }

    public double updatedNedlagdaKkr(ArrayList<CostItem> nedlagda) {
        return projectCosts.getTotalCost(nedlagda);
    }

    public double updatedNedlagdaKrBoa(double nedlagdaKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(nedlagdaKkr, totalBoa);
    }

    public double updatedNedlagdaKrBta(double nedlagdaKkr, double totalBta) {
        return projectCosts.getCostPerBta(nedlagdaKkr, totalBta);
    }

    public double updatedAnslutningarKkr(ArrayList<CostItem> anslutningar, double numOfApt) {
        return projectCosts.getConnectionsCost(projectCosts.getTotalCost(anslutningar), numOfApt);
    }

    public double updatedAnslutningarKrBoa(double anslutningarKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(anslutningarKkr, totalBoa);
    }

    public double updatedAnslutningarKrBta(double anslutningarKkr, double totalBta) {
        return projectCosts.getCostPerBta(anslutningarKkr, totalBta);
    }

    // ------------------ GETTERS ------------------ //

    /**
     * Getter for the 'ApartmentData' class.
     * @return The 'ApartmentData' class.
     */
    public ApartmentData getApartmentData() {
        return apartmentData;
    }

    /**
     * Getter for the 'ProjectCosts' class.
     * @return The 'ProjectCosts' class.
     */
    public ProjectCosts getProjectCosts() {
        return projectCosts;
    }



}
