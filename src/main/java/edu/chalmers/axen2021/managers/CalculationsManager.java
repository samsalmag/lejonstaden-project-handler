package edu.chalmers.axen2021.managers;

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
    public double updateTotalBoa(ArrayList<ApartmentType> apartments) {
        double totalBoa = 0;

        for(ApartmentType apartmentType: apartments) {
            totalBoa += (apartmentType.getBOA()*apartmentType.getAmount());
        }

        return totalBoa;
    }

    public double updateNumberOfApt(ArrayList<ApartmentType> apartments) {
        double numOfApt = 0;

        for(ApartmentType apartmentType: apartments) {
            numOfApt += apartmentType.getAmount();
        }

        return numOfApt;
    }

    public double updatedTomtkostnaderKkr(ArrayList<CostItem> tomtkostnader) {
        double tomtkostnaderKkr = 0;

        for(CostItem costItem: tomtkostnader) {
            tomtkostnaderKkr += costItem.getValue();
        }

        return tomtkostnaderKkr;
    }

    public double updatedTomtkostnaderKrBoa(double tomtkostnaderKkr, double numOfApt) {
        return (tomtkostnaderKkr*1000)/numOfApt;
    }

    public double updatedTomtkostnaderKrBta(double tomtkostnaderKkr, double totalBta) {
        return (tomtkostnaderKkr*1000)/totalBta;
    }

    public double updatedNedlagdaKkr(ArrayList<CostItem> nedlagda) {
        double nedlagdaKkr = 0;

        for(CostItem costItem: nedlagda) {
            nedlagdaKkr += costItem.getValue();
        }

        return nedlagdaKkr;
    }

    public double updatedNedlagdaKrBoa(double nedlagdaKkr, double numOfApt) {
        return (nedlagdaKkr*1000)/numOfApt;
    }

    public double updatedNedlagdaKrBta(double nedlagdaKkr, double totalBta) {
        return (nedlagdaKkr*1000)/totalBta;
    }

    public double updatedAnslutningarKkr(ArrayList<CostItem> anslutningar, double numOfApt) {
        double anslutningarPerApt = 0;
        for(CostItem costItem: anslutningar) {
            anslutningarPerApt += costItem.getValue();
        }
        return projectCosts.getConnectionsCost(anslutningarPerApt, numOfApt);
    }

    public double updatedAnslutningarKrBoa(double anslutningarKkr, double totalBoa) {
        return (anslutningarKkr*1000)/totalBoa;
    }

    public double updatedAnslutningarKrBta(double anslutningarKkr, double totalBta) {
        return (anslutningarKkr*1000)/totalBta;
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
