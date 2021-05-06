package edu.chalmers.axen2021.model;

import java.util.ArrayList;

/**
 * Class for calculating the apartment data.
 * @author Ahmad Al-Aref
 * @author Sara Vardheim
 * @author Tilda Grönlund
 */
public class ApartmentData {
    private SaboTable sabo;
    private final int rh = 121; // Swedish standard value

    public ApartmentData() {
        this.sabo = new SaboTable();
    }

    /**
     * Calculates and returns the monthly rent.
     * @param type apartment type, no of rooms
     * @param rent normrent if low, presumed rent if high
     * @param boa living area of the apartment
     * @return high or low monthly rent depending on the rent
     */
    public double getMonthlyRent(String type, double rent, double boa) {
        return (sabo.getRE(type)+boa)*((rent*77)/rh)/12;
    }

    /**
     * Calculates and returns kr per sqm of the apartment.
     * @param type apartment type, no of rooms
     * @param rent normrent if low, presumed rent if high
     * @param boa living area of the apartment
     * @return kr per sqm of the apartment
     */
    public double getKrPerSqm(String type, double rent, double boa) {
        return getMonthlyRent(type, rent, boa)*12/boa;
    }

    /**
     * Calculates and returns subsidy for specified apartment type.
     * @param investmentSub investment subsidy
     * @param quantity  no of apartments of specified type
     * @param boa living area of the apartment
     * @return subsidy for specified apartment type
     */
    public double getSubsidy(double investmentSub, double quantity, double boa) {
        double x;
        if(boa > 70)
            x = 35*investmentSub+(35*investmentSub)/2;
        else if (boa > 35)
            x = 35*investmentSub+((boa-35)*investmentSub)/2;
        else
            x = boa*investmentSub;

        return x/1000*quantity*1.75;
    }
}
