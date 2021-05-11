package edu.chalmers.axen2021.model;

import edu.chalmers.axen2021.model.projectdata.CostItem;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Tilda Grönlund
 * @author Ahmad Al-Aref
 * @author Sara Vardheim
 */

public class ProjectCosts {

    /** Anslutningar
     * @param krPerApt cost in kr per apartment.
     * @param totQuantity total quantity of apartments.
     * @return Connection costs of the project.
     */
    public double getConnectionsCost(double krPerApt, double totQuantity) {
        return (krPerApt * totQuantity);
    }

    // Potentiellt ta bort denna metoden.
    /** Byggherre
     * Calculates and returns the Client cost.
     * @param clientBoa BOA of the client.
     * @param totBoa total BOA of the project.
     * @return Client costs of the project.
     */
    public double getClientCost(double clientBoa, double totBoa) {
        return (clientBoa * totBoa) / 1000;
    }

    /** Entreprenad
     * Calculates and returns the Contract costs.
     * @param contract cost per BOA.
     * @param totBoa total BOA of the project.
     * @return Contract costs of the project.
     */
    public double getContractCost(double contract, double totBoa) {
        return (contract * totBoa) ;
    }

    /**
     * Calculates and returns the Unforseen costs.
     * @param contractKkr Total cost of the cotract.
     * @param totBoa total BOA of the project.
     * @param contractPercent percentage of the contract.
     * @return Unforseen costs of the project.
     */
    public double getUnforseenCost(double contractKkr, double totBoa, double contractPercent) {
        double factor = contractPercent/100;
        return (contractKkr * factor);
    }

    /**
     * Calculates and returns the Financial costs.
     * @param totBoa total BOA of the project.
     * @param krPerSqm cost in kr per square meter.
     * @return Financial costs of the project.
     */
    public double getFinancialCost(double totBoa, double krPerSqm) {
        return (totBoa * krPerSqm) ;
    }

    /**
     *
     * @param groundRent the ground rent for the project.
     * @return Site cost for the project.
     */
    public double getSiteCost(double groundRent){
        return groundRent;
    }

    /**
     *
     * @param disusedDeveloperCost disused developer cost (nedlagda byggherre).
     * @return The disused developer cost for the project.
     */
    public double getDisusedDeveloperCost(double disusedDeveloperCost){
        return disusedDeveloperCost;
    }

    /**
     * Checks for every Cost Item in the project if moms should be applied to the Cost Item.
     * If true then the Cost Item's value is multiplied with 0.25 (the tax) and added to the tax sum.
     * @param map An ArrayList containing all of the projects ArrayLists of Cost Items.
     * @return The tax sum for the project (mervärdesskatt)
     */
    public double getMervardesskatt(HashMap<Category, ArrayList<CostItem>> map, double totBoa, double noOfApts) {
        double moms = 0.0;
        for (HashMap.Entry<Category, ArrayList<CostItem>> entry : map.entrySet()) {
            Category c = entry.getKey();
            ArrayList<CostItem> costItems = map.get(c);
            if (c == Category.TOMTKOSTNADER || c == Category.NEDLAGDABYGGHERRE || c == Category.BYGGHERREKOSTNADER) {
                for (CostItem item : costItems) {
                    if (item.getMoms()) {
                        moms += item.getValue();
                    }
                }
            } else if (c == Category.ENTREPENAD || c == Category.FINANSIELLAKOSTNADER) {
                for (CostItem item : costItems) {
                    if (item.getMoms()) {
                        double value = item.getValue() * totBoa;
                        moms += value;
                    }
                }
            } else if (c == Category.ANSLUTNINGAR) {
                for (CostItem item : costItems) {
                    if (item.getMoms()) {
                        double value = item.getValue() * noOfApts;
                        moms += value;
                    }
                }
            } else ;

        }
        return (moms * 0.25) / 1000;
    }

    /**
     * Summarizes the total cost of a specified Cost Item in the project.
     * @param list List of Cost Items
     * @return The sum of the Cost Items
     */
    public double getTotalCost(ArrayList<CostItem> list) {
        double total = 0.0;
        for (CostItem c : list) {
            total += c.getValue();
        }
        return total / 1000;
    }

    /**
     * Calculates cost per BOA for a specified cost.
     * @param variable The specified cost.
     * @param totBoa The total BOA for the project.
     * @return The cost per BOA for the specified cost.
     */
    public double getCostPerBoa(double variable, double totBoa){
        if (totBoa == 0) {
            return 0;
        }
        else {
            return variable/totBoa*1000;
        }
    }

    /**
     * Calculates cost per BTA for a specified cost.
     * @param variable The specified cost.
     * @param totBta The total BTA for the project.
     * @return
     */
    public double getCostPerBta(double variable, double totBta){
        if (totBta == 0) {
            return 0;
        }
        else {
            return variable / totBta * 1000;
        }
    }
}
