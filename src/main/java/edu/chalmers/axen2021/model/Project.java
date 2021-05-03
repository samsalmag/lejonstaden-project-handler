package edu.chalmers.axen2021.model;

import edu.chalmers.axen2021.managers.ProjectManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class for the projects created in the application.
 * Implements the 'Serializable' interface to allow serialization (saving) of this classes data.
 * @author Sam Salek
 * @author Malte Åkvist
 */
public class Project implements Serializable {

    /**
     * Name of the project. Is set through the Constructor.
     */
    private String name;

    /**
     * A map containing all category lists.
     */
    private HashMap<Category, ArrayList<CostItem>> costItemsMap = new HashMap<>();

    //Tomtkostnader
    private double tomtkostnaderKkr;
    private double tomtkostnaderKrBoa;
    private double tomtkostnaderKrBta;

    //Neglagda bygherre
    private double nedlagdaByggherreKkr;
    private double nedlagdaByggherreKrBoa;
    private double nedlagdaByggherreKrBta;

    //Anslutningar
    private double anslutningarKkr;
    private double anslutningarKrBoa;
    private double anslutningarKrBta;

    //Byggherrekostnader
    private double byggherrekostnaderKkr;
    private double byggherrekostnaderKrBoa;
    private double byggherrekostnaderKrBta;

    //Entrepenad
    private double entreprenadKkr;
    private double entreprenadKrBoa;
    private double entreprenadKrBta;

    //Oforutsett
    private double oforutsettKkr;
    private double oforutsettKrBoa;
    private double oforutsettKrBta;

    //Finansiella kostnader
    private double finansiellaKostnaderKkr;
    private double finansiellaKostnaderKrBoa;
    private double finansiellaKostnaderKrBta;

    //Mervardeskatt
    private double mervardeskattKkr;
    private double mervardeskattKrBoa;
    private double mervardeskattKrBta;

    //Investeringsstod
    private double investeringsstodKkr;
    private double investeringsstodKrBoa;
    private double investeringsstodKrBta;

    //Projektkostnad
    private double projektkostnadKkr;
    private double projektkostnadKrBoa;
    private double projektkostnadKrBta;
    private double projektkostnadKkrMedStod;

    //Hyresintakter
    private double hyresintakterMedStod;
    private double hyresintakterUtanStod;

    //Drift and Underhall
    private double driftUnderhallMedStod;
    private double driftUnderhallUtanStod;

    //Tomtrattsavgald
    private double tomtrattsavgaldMedStod;
    private double tomtrattsavgaldUtanStod;

    //Driftnetto
    private double driftnettoMedStod;
    private double driftnettoUtanStod;

    //Yield
    private double yieldMedStod;
    private double yieldUtanStod;

    //Marknadsvarde
    private double marknadsvardeMedStod;
    private double marknadsvardeUtanStod;

    //Projektvinst
    private double projektvinstMedStod;
    private double projektvinstUtanStod;
    private double projektvinstProcentMedStod;
    private double projektvinstProcentUtanStod;

    /**
     * Class Constructor. Is used when a new instance of this class is created.
     * @param name Name of the project.
     */
    public Project(String name) {
        this.name = name;
        initCostItemsMap();
        ProjectManager.getInstance().addProject(this);
    }

    /**
     * Initializes the category map by creating an ArrayList for each category.
     */
    private void initCostItemsMap() {
        for (Category category : Category.values()) {
            costItemsMap.put(category, new ArrayList<>());
        }
    }

    /**
     * Method that creates a costItem used for the categories (contains name, comment, etc.)
     */
    public void addCostItem(String name) {

        CostItem costItem = new CostItem(name);

        for (Category category : Category.values()) {

            // Add the cost item to the correct category lists
            if(ProjectManager.getInstance().getActiveCategory() == category) {
                costItemsMap.get(category).add(costItem);
                ProjectManager.getInstance().getActiveCostItemNames().add(name);

                // Sort the list to alphabetical order.
                ProjectManager.getInstance().getActiveCostItemNames().sort(String::compareToIgnoreCase);

                return;
            }
        }
    }

    // ------------------ GETTERS ------------------ //
    /**
     * Getter for the name of the project.
     * @return Name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the map of all cost items in the project.
     * @return Map of all cost items.
     */
    public HashMap<Category, ArrayList<CostItem>> getCostItemsMap() {
        return costItemsMap;
    }

    // GETTERS FOR COST ITEM LISTS
    public ArrayList<CostItem> getTomtkostnader() {
        return costItemsMap.get(Category.TOMTKOSTNADER);
    }

    public ArrayList<CostItem> getNedlagdaByggherre() {
        return costItemsMap.get(Category.NEDLAGDABYGGHERRE);
    }

    public ArrayList<CostItem> getAnslutningar() {
        return costItemsMap.get(Category.ANSLUTNINGAR);
    }

    public ArrayList<CostItem> getByggherrekostnader() {
        return costItemsMap.get(Category.BYGGHERREKOSTNADER);
    }

    public ArrayList<CostItem> getEntrepenad() {
        return costItemsMap.get(Category.ENTREPENAD);
    }

    public ArrayList<CostItem> getOförutsett() {
        return costItemsMap.get(Category.OFÖRUTSETT);
    }

    public ArrayList<CostItem> getFinansiellaKostnader() {
        return costItemsMap.get(Category.FINANSIELLAKOSTNADER);
    }

    public ArrayList<CostItem> getMervärdeskatt() {
        return costItemsMap.get(Category.MERVÄRDESKATT);
    }

    public ArrayList<CostItem> getInvesteringsstöd() {
        return costItemsMap.get(Category.INVESTERINGSSTÖD);
    }

    public ArrayList<CostItem> getHyresintäkter() {
        return costItemsMap.get(Category.HYRESINTÄKTER);
    }

    public ArrayList<CostItem> getDriftOchUnderhåll() {
        return costItemsMap.get(Category.DRIFTOCHUNDERHÅLL);
    }

    public ArrayList<CostItem> getTomträttsavgäld() {
        return costItemsMap.get(Category.TOMTRÄTTSAVGÄLD);
    }

    public ArrayList<CostItem> getDriftnetto() {
        return costItemsMap.get(Category.DRIFTNETTO);
    }

    public ArrayList<CostItem> getYield() {
        return costItemsMap.get(Category.YIELD);
    }

    // GETTERS FOR ALL THOSE VARIABLES...
    public double getTomtkostnaderKkr() {
        return tomtkostnaderKkr;
    }

    public double getTomtkostnaderKrBoa() {
        return tomtkostnaderKrBoa;
    }

    public double getTomtkostnaderKrBta() {
        return tomtkostnaderKrBta;
    }

    public double getNedlagdaByggherreKkr() {
        return nedlagdaByggherreKkr;
    }

    public double getNedlagdaByggherreKrBoa() {
        return nedlagdaByggherreKrBoa;
    }

    public double getNedlagdaByggherreKrBta() {
        return nedlagdaByggherreKrBta;
    }

    public double getAnslutningarKkr() {
        return anslutningarKkr;
    }

    public double getAnslutningarKrBoa() {
        return anslutningarKrBoa;
    }

    public double getAnslutningarKrBta() {
        return anslutningarKrBta;
    }

    public double getByggherrekostnaderKkr() {
        return byggherrekostnaderKkr;
    }

    public double getByggherrekostnaderKrBoa() {
        return byggherrekostnaderKrBoa;
    }

    public double getByggherrekostnaderKrBta() {
        return byggherrekostnaderKrBta;
    }

    public double getEntreprenadKkr() {
        return entreprenadKkr;
    }

    public double getEntreprenadKrBoa() {
        return entreprenadKrBoa;
    }

    public double getEntreprenadKrBta() {
        return entreprenadKrBta;
    }

    public double getOforutsettKkr() {
        return oforutsettKkr;
    }

    public double getOforutsettKrBoa() {
        return oforutsettKrBoa;
    }

    public double getOforutsettKrBta() {
        return oforutsettKrBta;
    }

    public double getFinansiellaKostnaderKkr() {
        return finansiellaKostnaderKkr;
    }

    public double getFinansiellaKostnaderKrBoa() {
        return finansiellaKostnaderKrBoa;
    }

    public double getFinansiellaKostnaderKrBta() {
        return finansiellaKostnaderKrBta;
    }

    public double getMervardeskattKkr() {
        return mervardeskattKkr;
    }

    public double getMervardeskattKrBoa() {
        return mervardeskattKrBoa;
    }

    public double getMervardeskattKrBta() {
        return mervardeskattKrBta;
    }

    public double getInvesteringsstodKkr() {
        return investeringsstodKkr;
    }

    public double getInvesteringsstodKrBoa() {
        return investeringsstodKrBoa;
    }

    public double getInvesteringsstodKrBta() {
        return investeringsstodKrBta;
    }

    public double getProjektkostnadKkr() {
        return projektkostnadKkr;
    }

    public double getProjektkostnadKrBoa() {
        return projektkostnadKrBoa;
    }

    public double getProjektkostnadKrBta() {
        return projektkostnadKrBta;
    }

    public double getProjektkostnadKkrMedStod() {
        return projektkostnadKkrMedStod;
    }

    public double getHyresintakterMedStod() {
        return hyresintakterMedStod;
    }

    public double getHyresintakterUtanStod() {
        return hyresintakterUtanStod;
    }

    public double getDriftUnderhallMedStod() {
        return driftUnderhallMedStod;
    }

    public double getDriftUnderhallUtanStod() {
        return driftUnderhallUtanStod;
    }

    public double getTomtrattsavgaldMedStod() {
        return tomtrattsavgaldMedStod;
    }

    public double getTomtrattsavgaldUtanStod() {
        return tomtrattsavgaldUtanStod;
    }

    public double getDriftnettoMedStod() {
        return driftnettoMedStod;
    }

    public double getDriftnettoUtanStod() {
        return driftnettoUtanStod;
    }

    public double getYieldMedStod() {
        return yieldMedStod;
    }

    public double getYieldUtanStod() {
        return yieldUtanStod;
    }

    public double getMarknadsvardeMedStod() {
        return marknadsvardeMedStod;
    }

    public double getMarknadsvardeUtanStod() {
        return marknadsvardeUtanStod;
    }

    public double getProjektvinstMedStod() {
        return projektvinstMedStod;
    }

    public double getProjektvinstUtanStod() {
        return projektvinstUtanStod;
    }

    public double getProjektvinstProcentMedStod() {
        return projektvinstProcentMedStod;
    }

    public double getProjektvinstProcentUtanStod() {
        return projektvinstProcentUtanStod;
    }
}
