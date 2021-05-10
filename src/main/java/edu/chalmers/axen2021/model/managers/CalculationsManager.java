package edu.chalmers.axen2021.model.managers;

import edu.chalmers.axen2021.model.*;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import edu.chalmers.axen2021.model.projectdata.CostItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        double totalBoa = 0.0;

        for(ApartmentItem apartmentItem : apartments) {
            totalBoa += (apartmentItem.getBOA()* apartmentItem.getAmount());
        }

        return totalBoa;
    }

    public double updateNumberOfApt(ArrayList<ApartmentItem> apartments) {
        double numOfApt = 0.0;

        for(ApartmentItem apartmentItem : apartments) {
            numOfApt += apartmentItem.getAmount();
        }

        return numOfApt;
    }

    public double updateKrPerKvm(ArrayList<ApartmentItem> apartments, double rent) {
        double krPerKvm = 0.0;
        for(ApartmentItem apartmentItem : apartments) {
            double rentPerKvm = apartmentData.getMonthlyRent(apartmentItem.getApartmentType(), rent, apartmentItem.getBOA());
            krPerKvm += apartmentData.getKrPerSqm(apartmentItem.getApartmentType(), rentPerKvm, apartmentItem.getBOA());
        }
        return krPerKvm;
    }

    public double updateSubsidyKKr(double investeringsstod, ArrayList<ApartmentItem> apartments) {
        double subsidy = 0.0;
        for (ApartmentItem a : apartments) {
            subsidy -= apartmentData.getSubsidy(investeringsstod, a.getAmount(), a.getBOA());
        }
        //double subsidy = 0.0;
        //subsidy -= apartmentData.getSubsidy(investeringsstod, quantity, boa);

        return subsidy;
    }

    public double updatedSubsidyKrBoa(double subsidyKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(subsidyKkr, totalBoa);
    }

    public double updatedSubsidyKrBta(double subsidyKkr, double totalBta) {
        return projectCosts.getCostPerBta(subsidyKkr, totalBta);
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

    public double updatedByggherreKkr(ArrayList<CostItem> byggherre, double totalBoa) {
        return projectCosts.getClientCost(projectCosts.getTotalCost(byggherre), totalBoa);
    }

    public double updatedByggherreKrBoa(double byggherreKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(byggherreKkr, totalBoa);
    }

    public double updatedByggherreKrBta(double byggherreKkr, double totalBta) {
        return projectCosts.getCostPerBta(byggherreKkr, totalBta);
    }

    public double updatedEntreprenadKkr(ArrayList<CostItem> entreprenad, double totalBoa) {
        return projectCosts.getContractCost(projectCosts.getTotalCost(entreprenad), totalBoa);
    }

    public double updatedEntreprenadKrBoa(double entreprenadKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(entreprenadKkr, totalBoa) ;
    }

    public double updatedEntreprenadKrBta(double entreprenadKkr, double totalBta) {
        return projectCosts.getCostPerBta(entreprenadKkr, totalBta);
    }

    public double updatedOforutsettKkr(ArrayList<CostItem> entreprenad, double totalBoa, double contractPercent) {
        double entreprenadKkr = projectCosts.getContractCost(projectCosts.getTotalCost(entreprenad), totalBoa);
        return projectCosts.getUnforseenCost(entreprenadKkr, totalBoa, contractPercent);
    }

    public double updatedOforutsettKrBoa(double oforutsettKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(oforutsettKkr, totalBoa);
    }

    public double updatedOforutsettKrBta(double oforutsettKkr, double totalBta) {
        return  projectCosts.getCostPerBta(oforutsettKkr, totalBta);
    }

    public double updatedFinansiellaKkr(ArrayList<CostItem> finansiella, double totalBoa) {
        return projectCosts.getFinancialCost(totalBoa, projectCosts.getTotalCost(finansiella));
    }

    public double updatedFinansiellaKrBoa(double finansiellaKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(finansiellaKkr, totalBoa);
    }

    public double updatedFinansiellaKrBta(double finansiellaKkr, double totalBta) {
        return projectCosts.getCostPerBta(finansiellaKkr, totalBta);
    }

    public double updatedMervardesskattKkr(HashMap<Category, ArrayList<CostItem>> mervardesskatt) {
        return projectCosts.getMervardesskatt(mervardesskatt);
    }

    public double updatedMervardesskattKrBoa(double mervardesskattKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(mervardesskattKkr, totalBoa);
    }

    public double updatedMervardesskattKrBta(double mervardesskattKkr, double totalBta) {
        return  projectCosts.getCostPerBta(mervardesskattKkr, totalBta);
    }

    public double updateProjectCostKkr(HashMap<Category, ArrayList<CostItem>> costsKkr) {
        double projectCosts = 0.0;
        for(Map.Entry<Category, ArrayList<CostItem>> entry : costsKkr.entrySet()) {
            Category key = entry.getKey();
            ArrayList<CostItem> value = costsKkr.get(key);
            for (CostItem costItem : value)
                projectCosts += costItem.getValue();
        }

        return projectCosts/1000;
    }
/**
    public double updatedProjectCostKrBoa(double projektkostnadKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(projektkostnadKkr, totalBoa);
    }

    public double updatedProjectCostKrBta(double projektkostnadKkr, double totalBta) {
        return projectCosts.getCostPerBta(projektkostnadKkr, totalBta);
    }*/

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
