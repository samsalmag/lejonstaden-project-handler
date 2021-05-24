package edu.chalmers.axen2021.model.managers;

import edu.chalmers.axen2021.model.*;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import edu.chalmers.axen2021.model.projectdata.CostItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class managing the calculations needed for the different variables in a project.
 * @author Erik Wetter
 * @author Sara Vardheim
 * @author Tilda Grönlund
 * @author Ahmad Al-Aref
 */
public class CalculationsManager implements Serializable {

    /**
     * The instance of this class.
     */
    private static CalculationsManager calculationsManager = null;

    // Singleton class. Use getInstance().
    private CalculationsManager() {}

    /**
     * Returns the instance of this class.
     * @return Instance of this class.
     */
    public static CalculationsManager getInstance() {
        if(calculationsManager == null) {
            calculationsManager = new CalculationsManager();
        }
        return calculationsManager;
    }

    private static final ApartmentData apartmentData = new ApartmentData();
    private static final ProjectCosts projectCosts = new ProjectCosts();

    // Update methods
    public double updatedApartmentItemRentPerMonthLow(ApartmentItem apartmentItem, double normhyraMedStod) {
        return apartmentData.getMonthlyRent(apartmentItem.getApartmentType(), normhyraMedStod, apartmentItem.getBOA());
    }

    public double updatedApartmentItemKrPerKvmLow(ApartmentItem apartmentItem, double normhyraMedStod) {
        return apartmentData.getKrPerSqm(apartmentItem.getApartmentType(), normhyraMedStod, apartmentItem.getBOA());
    }

    public double updatedApartmentItemRentPerMonthHigh(ApartmentItem apartmentItem, double antagenPresumtionshyra) {
        return apartmentData.getMonthlyRent(apartmentItem.getApartmentType(), antagenPresumtionshyra, apartmentItem.getBOA());
    }

    public double updatedApartmentItemKrPerKvmHigh(ApartmentItem apartmentItem, double antagenPresumtionshyra) {
        return apartmentData.getKrPerSqm(apartmentItem.getApartmentType(), antagenPresumtionshyra, apartmentItem.getBOA());
    }

    public double updatedApartmentItemTotalBOA(ApartmentItem apartmentItem) {
        return apartmentItem.getBOA()* apartmentItem.getAmount();
    }

    public double updatedApartmentItemTotalBOAPercent(double apartmentItemTotalBOA, double totalBOA) {
        return (apartmentItemTotalBOA / totalBOA) * 100;
    }

    public double updatedApartmentItemBidrag(ApartmentItem apartmentItem, double investeringsstod) {
        return apartmentData.getSubsidy(investeringsstod, apartmentItem.getAmount(), apartmentItem.getBOA());
    }

    public double updatedTotalKrPerKvmLow(ArrayList<ApartmentItem> apartments) {
        double v = 0;
        for(ApartmentItem apartmentItem : apartments) {
            v += (apartmentItem.getKrPerKvmLow() * apartmentItem.getTotalBOA());
        }
        return v / updatedTotalBoa(apartments);
    }

    public double updatedTotalKrPerKvmHigh(ArrayList<ApartmentItem> apartments) {
        double v = 0;
        for(ApartmentItem apartmentItem : apartments) {
            v += (apartmentItem.getKrPerKvmHigh() * apartmentItem.getTotalBOA());
        }
        return v / updatedTotalBoa(apartments);
    }

    public double updatedTotalBidrag(ArrayList<ApartmentItem> apartments) {
        double v = 0;
        for(ApartmentItem apartmentItem : apartments) {
            v += apartmentItem.getBidrag();
        }
        return v;
    }

    public double updatedTotalBoa(ArrayList<ApartmentItem> apartments) {
        double totalBoa = 0.0;

        for(ApartmentItem apartmentItem : apartments) {
            totalBoa += (apartmentItem.getBOA()* apartmentItem.getAmount());
        }

        return totalBoa;
    }

    public double updatedNumberOfApt(ArrayList<ApartmentItem> apartments) {
        double numOfApt = 0.0;

        for(ApartmentItem apartmentItem : apartments) {
            numOfApt += apartmentItem.getAmount();
        }

        return numOfApt;
    }

    public double updatedHyresintakter(ArrayList<ApartmentItem> apartments, ArrayList<CostItem> extraIntakter, double rent, double totalBoa) {
        double krPerKvm = 0.0;
        for (ApartmentItem apartment : apartments) {
            krPerKvm += apartmentData.getKrPerSqm(apartment.getApartmentType(), rent, apartment.getBOA())
            * apartment.getBOA() * apartment.getAmount();
        }
        double totIntakter = krPerKvm + (projectCosts.getTotalCost(extraIntakter) * totalBoa);
        return (totIntakter)/1000;
    }

    public double updatedSubsidyKKr(double investeringsstod, ArrayList<ApartmentItem> apartments) {
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
        return projectCosts.getConnectionsCost(projectCosts.getTotalCost(anslutningar), numOfApt)/1000;
    }

    public double updatedAnslutningarKrBoa(double anslutningarKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(anslutningarKkr, totalBoa);
    }

    public double updatedAnslutningarKrBta(double anslutningarKkr, double totalBta) {
        return projectCosts.getCostPerBta(anslutningarKkr, totalBta);
    }

    public double updatedByggherreKkr(ArrayList<CostItem> byggherre) {
        return projectCosts.getTotalCost(byggherre);
    }

    public double updatedByggherreKrBoa(double byggherreKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(byggherreKkr, totalBoa);
    }

    public double updatedByggherreKrBta(double byggherreKkr, double totalBta) {
        return projectCosts.getCostPerBta(byggherreKkr, totalBta);
    }

    public double updatedEntreprenadKkr(ArrayList<CostItem> entreprenad, double totalBoa) {
        return projectCosts.getContractCost(projectCosts.getTotalCost(entreprenad), totalBoa)/1000;
    }

    public double updatedEntreprenadKrBoa(double entreprenadKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(entreprenadKkr, totalBoa) ;
    }

    public double updatedEntreprenadKrBta(double entreprenadKkr, double totalBta) {
        return projectCosts.getCostPerBta(entreprenadKkr, totalBta);
    }

    public double updatedOforutsettKkr(ArrayList<CostItem> entreprenad, double totalBoa, double contractPercent) {
        double entreprenadKkr = projectCosts.getContractCost(projectCosts.getTotalCost(entreprenad), totalBoa);
        return projectCosts.getUnforseenCost(entreprenadKkr, totalBoa, contractPercent)/1000;
    }

    public double updatedOforutsettKrBoa(double oforutsettKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(oforutsettKkr, totalBoa);
    }

    public double updatedOforutsettKrBta(double oforutsettKkr, double totalBta) {
        return  projectCosts.getCostPerBta(oforutsettKkr, totalBta);
    }

    public double updatedFinansiellaKkr(ArrayList<CostItem> finansiella, double totalBoa) {
        return projectCosts.getFinancialCost(totalBoa, (projectCosts.getTotalCost(finansiella)/1000));
    }

    public double updatedFinansiellaKrBoa(double finansiellaKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(finansiellaKkr, totalBoa);
    }

    public double updatedFinansiellaKrBta(double finansiellaKkr, double totalBta) {
        return projectCosts.getCostPerBta(finansiellaKkr, totalBta);
    }

    public double updatedMervardesskattKkr(HashMap<Category, ArrayList<CostItem>> costItemMap, double totalBoa, double noOfApts) {
        return projectCosts.getMervardesskatt(costItemMap, totalBoa, noOfApts);
    }

    public double updatedMervardesskattKrBoa(double mervardesskattKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(mervardesskattKkr, totalBoa);
    }

    public double updatedMervardesskattKrBta(double mervardesskattKkr, double totalBta) {
        return  projectCosts.getCostPerBta(mervardesskattKkr, totalBta);
    }

    public double updatedProjectCostKkr(double tomtkostnaderKkr, double nedlagdaByggherreKkr, double anslutningarKkr,
                                       double byggherreKkr, double entreprenadKkr, double oforutsettKkr,
                                       double finansiellaKkr, double mervardesskattKkr, double investeringsstodKkr) {
        double projectCosts = tomtkostnaderKkr + nedlagdaByggherreKkr + anslutningarKkr + byggherreKkr +
                entreprenadKkr + oforutsettKkr + finansiellaKkr + mervardesskattKkr + investeringsstodKkr;
        return projectCosts;
    }

    public double updatedProjectCostKrBoa(double projektkostnadKkr, double totalBoa) {
        return projectCosts.getCostPerBoa(projektkostnadKkr, totalBoa);
    }

    public double updatedProjectCostKrBta(double projektkostnadKkr, double totalBta) {
        return projectCosts.getCostPerBta(projektkostnadKkr, totalBta);
    }

    public double updatedDriftOchUnderhall(ArrayList<CostItem> driftunderhall, double totalBoa) {
        return projectCosts.getTotalCost(driftunderhall)*totalBoa/1000;
    }
}
