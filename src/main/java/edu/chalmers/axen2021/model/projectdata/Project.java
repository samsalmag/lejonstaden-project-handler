package edu.chalmers.axen2021.model.projectdata;

import edu.chalmers.axen2021.model.Category;
import edu.chalmers.axen2021.model.managers.CalculationsManager;
import edu.chalmers.axen2021.model.managers.ProjectManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class for the projects created in the application.
 * Implements the 'Serializable' interface to allow serialization (saving) of this classes data.
 * @author Sam Salek
 * @author Malte Åkvist
 * @author Sara Vardheim
 * @author Tilda Grönlund
 * @author Ahmad Al-Aref
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

    /**
     * A list of all apartmentItems
     */
    private ArrayList<ApartmentItem> apartmentItems = new ArrayList<>();

    private CalculationsManager calculationsManager = CalculationsManager.getInstance();

    private double numOfApt;

    private double krPerKvm;

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

    //Grundforutsattningar
    private double normhyraMedStod;
    private double investeringsstod;
    private double antagenPresumtionshyra;
    private double totalBoa;
    private double totalLjusBta;
    private double oforutsettPercent;

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
     * Returns a cost item from this project in the active category.
     * Returns null when none with the given name was found.
     * @param name The name of a cost item to find.
     * @return The cost item if found, or null if not.
     */
    public CostItem getActiveCostItem(String name) {
        // Throw exception if activeCategory is null.
        Category activeCategory = ProjectManager.getInstance().getActiveCategory();
        if(activeCategory == null) {
            throw new NullPointerException("The active category is null!");
        }

        for(CostItem costItem : costItemsMap.get(activeCategory)) {
            if(costItem.getName().equals(name)) {
                return costItem;
            }
        }
        return null;
    }

    /**
     * Method that creates a costItem used for the categories (contains name, comment, etc.)
     * @return Returns the cost item that was created.
     */
    public CostItem addCostItem(String name) {
        // Throw exception if activeCategory is null.
        Category activeCategory = ProjectManager.getInstance().getActiveCategory();
        if(activeCategory == null) {
            throw new NullPointerException("The active category is null!");
        }

        CostItem costItem = new CostItem(name);

        // Add the cost item to the correct category lists
        costItemsMap.get(activeCategory).add(costItem);

        // Only add cost item name to ProjectManager if ProjectManager doesn't already contain it in the active category.
        // Used to avoid duplicate cost item names in the same categories,
        // hence avoiding duplicate cost items when populating the category window.
        if(!ProjectManager.getInstance().getActiveCostItemNames().contains(name)) {
            ProjectManager.getInstance().getActiveCostItemNames().add(name);
        }

        // Sort the list to alphabetical order.
        ProjectManager.getInstance().getActiveCostItemNames().sort(String::compareToIgnoreCase);

        System.out.println("Added cost item \"" + name + "\" in category " + activeCategory.getName() + "!");
        return costItem;
    }

    /**
     * Created and adds a new ApartmentItem to this project.
     * @return The ApartmentItem that was created.
     */
    public ApartmentItem addApartmentItem() {
        ApartmentItem apartmentItem = new ApartmentItem();
        apartmentItems.add(apartmentItem);
        return apartmentItem;
    }

    /**
     * Removes a cost item.
     * @param category Category where cost item resides.
     * @param name Name of the cost item
     * @return Returns True if removal was successful, False if not.
     */
    public boolean removeCostItem(Category category, String name) {

        // For every cost item in the given category...if its name is same as given name...
        for (CostItem costItem : costItemsMap.get(category)) {
            if(costItem.getName().equals(name)) {

                // Remove cost item from project, remove cost item name from project manager.
                costItemsMap.get(category).remove(costItem);
                ProjectManager.getInstance().getCostItemNamesMap().get(category).remove(costItem.getName());
                return true;
            }
        }

        throw new IllegalArgumentException("No cost item with name \"" + name + "\" exists in category " + category.getName());
    }

    /**
     * Removes an apartmentItem
     * @param apartmentItem The apartmentItem to remove.
     * @return Returns True if removal was successful, False if not.
     */
    public boolean removeApartmentItem(ApartmentItem apartmentItem) {
        // Only remove apartmentItem if it exists in project.
        if(apartmentItems.contains(apartmentItem)) {
            apartmentItems.remove(apartmentItem);
            return true;
        } else {
            throw new IllegalArgumentException(apartmentItem + " does not exist in this project!");
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

    /**
     * Getter for the list of all apartmentItems in the project.
     * @return List of all apartmentItems.
     */
    public ArrayList<ApartmentItem> getApartmentItems() {
        return apartmentItems;
    }

    // GETTERS FOR COST ITEM LISTS
    public ArrayList<CostItem> getTomtkostnaderCostItems() {
        return costItemsMap.get(Category.TOMTKOSTNADER);
    }

    public ArrayList<CostItem> getNedlagdaByggherreCostItems() {
        return costItemsMap.get(Category.NEDLAGDABYGGHERRE);
    }

    public ArrayList<CostItem> getAnslutningarCostItems() {
        return costItemsMap.get(Category.ANSLUTNINGAR);
    }

    public ArrayList<CostItem> getByggherrekostnaderCostItems() {
        return costItemsMap.get(Category.BYGGHERREKOSTNADER);
    }

    public ArrayList<CostItem> getEntrepenadCostItems() {
        return costItemsMap.get(Category.ENTREPENAD);
    }

    public ArrayList<CostItem> getFinansiellaKostnaderCostItems() {
        return costItemsMap.get(Category.FINANSIELLAKOSTNADER);
    }

    public ArrayList<CostItem> getHyresintäkterCostItems() {
        return costItemsMap.get(Category.HYRESINTÄKTER);
    }

    public ArrayList<CostItem> getDriftOchUnderhållCostItems() {
        return costItemsMap.get(Category.DRIFTOCHUNDERHÅLL);
    }


    // GETTERS FOR ALL THOSE VARIABLES...
    public double getNumOfApt() { return numOfApt; }

    public double getTomtkostnaderKkr() { return tomtkostnaderKkr; }

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

    public double getNormhyraMedStod() {
        return normhyraMedStod;
    }

    public double getInvesteringsstod() {
        return investeringsstod;
    }

    public double getAntagenPresumtionshyra() {
        return antagenPresumtionshyra;
    }

    public double getTotalBoa() {
        return totalBoa;
    }

    public double getTotalLjusBta() {
        return totalLjusBta;
    }

    public double getOforutsettPercent() {
        return oforutsettPercent;
    }

    // SETTERS FOR INPUT VARIABLES
    public void setYieldMedStod(double yieldMedStod) {
        this.yieldMedStod = yieldMedStod;
    }

    public void setNormhyraMedStod(double normhyraMedStod) {
        this.normhyraMedStod = normhyraMedStod;
    }

    public void setInvesteringsstod(double investeringsstod) {
        this.investeringsstod = investeringsstod;
    }

    public void setAntagenPresumtionshyra(double antagenPresumtionshyra) {
        this.antagenPresumtionshyra = antagenPresumtionshyra;
    }

    public void setTotalLjusBta(double totalLjusBta) {
        this.totalLjusBta = totalLjusBta;
    }

    public void setYieldUtanStod(double yieldUtanStod) {
        this.yieldUtanStod = yieldUtanStod;
    }

    public void setOforutsettPercent(double oforutsettPercent) {
        this.oforutsettPercent = oforutsettPercent;
    }

    //Update methods

    public void updateAllVariables() {
        updateNumOfApt();
        updateTotalBoa();

        updateTomtkostnaderKkr();
        updateTomtkostnaderKrBoa();
        updateTomtkostnaderKrBta();

        updateNedlagdaBygherreKkr();
        updateNedlagdaBygherreKrBoa();
        updateNedlagdaBygherreKrBta();

        updateAnslutningarKkr();
        updateAnslutningarKrBoa();
        updateAnslutningarKrBta();

        updateByggherreKkr();
        updateByggherreKrBoa();
        updateByggherreKrBta();

        updateEntreprenadKkr();
        updateEntreprenadKrBoa();
        updateEntreprenadKrBta();

        updateOforutsettKkr();
        updateOforutsettKrBoa();
        updateOforutsettKrBta();

        updateFinansiellaKkr();
        updateFinansiellaKrBoa();
        updateFinansiellaKrBta();

        updateMervardesskattKkr();
        updateMervardesskattKrBoa();
        updateMervardesskattKrBta();

        updateProjektkostnadKkr();
        updateProjektkostnadKrBoa();
        updateProjektkostnadKrBta();

        updateInvesteringsstodKkr();
        updateInvesteringsstodKrBoa();
        updateInvesteringsstodKrBta();

        updateHyresintakterBostadMedStod();
        updateHyresintakterBostadUtanStod();

    }

    private void updateNumOfApt() {
        numOfApt = calculationsManager.updateNumberOfApt(getApartmentItems());
    }
    private void updateTotalBoa() {
        totalBoa = calculationsManager.updateTotalBoa(getApartmentItems());
    }

    private void updateTomtkostnaderKkr() {
        tomtkostnaderKkr = calculationsManager.updatedTomtkostnaderKkr(getTomtkostnaderCostItems());
    }
    private void updateTomtkostnaderKrBoa() {
        tomtkostnaderKrBoa = calculationsManager.updatedTomtkostnaderKrBoa(tomtkostnaderKkr, totalBoa);
    }
    private void updateTomtkostnaderKrBta() {
        tomtkostnaderKrBta = calculationsManager.updatedTomtkostnaderKrBta(tomtkostnaderKkr, totalLjusBta);
    }

    private void updateNedlagdaBygherreKkr() {
        nedlagdaByggherreKkr = calculationsManager.updatedNedlagdaKkr(getNedlagdaByggherreCostItems());
    }
    private void updateNedlagdaBygherreKrBoa() {
        nedlagdaByggherreKrBoa = calculationsManager.updatedNedlagdaKrBoa(nedlagdaByggherreKkr, totalBoa);
    }
    private void updateNedlagdaBygherreKrBta() {
        nedlagdaByggherreKrBta = calculationsManager.updatedNedlagdaKrBta(nedlagdaByggherreKkr, totalLjusBta);
    }

    private void updateAnslutningarKkr() {
        anslutningarKkr = calculationsManager.updatedAnslutningarKkr(getAnslutningarCostItems(), numOfApt);
    }
    private void updateAnslutningarKrBoa() {
        anslutningarKrBoa = calculationsManager.updatedAnslutningarKrBoa(anslutningarKkr, totalBoa);
    }
    private void updateAnslutningarKrBta() {
        anslutningarKrBta = calculationsManager.updatedAnslutningarKrBta(anslutningarKkr, totalLjusBta);
    }

    private void updateByggherreKkr() {
        byggherrekostnaderKkr = calculationsManager.updatedByggherreKkr(getByggherrekostnaderCostItems());
    }

    private void updateByggherreKrBoa() {
        byggherrekostnaderKrBoa = calculationsManager.updatedByggherreKrBoa(byggherrekostnaderKkr, totalBoa);
    }

    private void updateByggherreKrBta() {
        byggherrekostnaderKrBta = calculationsManager.updatedByggherreKrBta(byggherrekostnaderKkr, totalLjusBta);
    }

    private void updateEntreprenadKkr() {
        entreprenadKkr = calculationsManager.updatedEntreprenadKkr(getEntrepenadCostItems(), totalBoa);
    }

    private void updateEntreprenadKrBoa() {
        entreprenadKrBoa = calculationsManager.updatedEntreprenadKrBoa(entreprenadKkr, totalBoa);
    }

    private void updateEntreprenadKrBta() {
        entreprenadKrBta = calculationsManager.updatedEntreprenadKrBta(entreprenadKkr, totalLjusBta);
    }

    private void updateOforutsettKkr() {
        oforutsettKkr = calculationsManager.updatedOforutsettKkr(getEntrepenadCostItems(), totalBoa, oforutsettPercent);
    }

    private void updateOforutsettKrBoa() {
        oforutsettKrBoa = calculationsManager.updatedOforutsettKrBoa(oforutsettKkr, totalBoa);
    }

    private void updateOforutsettKrBta() {
        oforutsettKrBta = calculationsManager.updatedOforutsettKrBta(oforutsettKkr, totalLjusBta);
    }

    private void updateFinansiellaKkr() {
        finansiellaKostnaderKkr = calculationsManager.updatedFinansiellaKkr(getFinansiellaKostnaderCostItems(), totalBoa);
    }

    private void updateFinansiellaKrBoa() {
        finansiellaKostnaderKrBoa = calculationsManager.updatedFinansiellaKrBoa(finansiellaKostnaderKkr, totalBoa);
    }

    private void updateFinansiellaKrBta() {
        finansiellaKostnaderKrBta = calculationsManager.updatedFinansiellaKrBta(finansiellaKostnaderKkr, totalLjusBta);
    }

    private void updateMervardesskattKkr() {
        mervardeskattKkr = calculationsManager.updatedMervardesskattKkr(costItemsMap, totalBoa, numOfApt);
    }

    private void updateMervardesskattKrBoa() {
        mervardeskattKrBoa = calculationsManager.updatedMervardesskattKrBoa(mervardeskattKkr, totalBoa);
    }

    private void updateMervardesskattKrBta() {
        mervardeskattKrBta = calculationsManager.updatedMervardesskattKrBta(mervardeskattKkr, totalLjusBta);
    }

    private void updateProjektkostnadKkr() {
        projektkostnadKkr = calculationsManager.updatedProjectCostKkr(tomtkostnaderKkr, nedlagdaByggherreKkr,anslutningarKkr,
                byggherrekostnaderKkr, entreprenadKkr, oforutsettKkr, finansiellaKostnaderKkr,
                mervardeskattKkr, investeringsstodKkr);
    }

    private void updateProjektkostnadKrBoa() {
        projektkostnadKrBoa = calculationsManager.updatedProjectCostKrBoa(projektkostnadKkr, totalBoa);
    }

    private void updateProjektkostnadKrBta() {
        projektkostnadKrBta = calculationsManager.updatedProjectCostKrBta(projektkostnadKkr, totalLjusBta);
    }

    private void updateInvesteringsstodKkr() {
       investeringsstodKkr = calculationsManager.updateSubsidyKKr(getInvesteringsstod(), getApartmentItems());
    }

    private void updateInvesteringsstodKrBoa() {
        investeringsstodKrBoa = calculationsManager.updatedSubsidyKrBoa(investeringsstodKkr, totalBoa);
    }

    private void updateInvesteringsstodKrBta() {
        investeringsstodKrBta = calculationsManager.updatedSubsidyKrBta(investeringsstodKkr, totalLjusBta);
    }


    private void updateHyresintakterBostadMedStod() {
        krPerKvm = calculationsManager.updateKrPerKvm(apartmentItems, normhyraMedStod);
        hyresintakterMedStod = totalBoa*krPerKvm/1000;
    }

    private void updateHyresintakterBostadUtanStod() {
        krPerKvm = calculationsManager.updateKrPerKvm(apartmentItems, antagenPresumtionshyra);
        hyresintakterUtanStod = totalBoa*krPerKvm/1000;
    }

}
