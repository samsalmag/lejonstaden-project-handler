package edu.chalmers.axen2021.model.projectdata;

import edu.chalmers.axen2021.model.Category;
import edu.chalmers.axen2021.model.managers.CalculationsManager;
import edu.chalmers.axen2021.model.managers.ProjectManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
    private final HashMap<Category, ArrayList<CostItem>> costItemsMap = new HashMap<>();

    /**
     * A list of all apartmentItems
     */
    private final ArrayList<ApartmentItem> apartmentItems = new ArrayList<>();

    private final CalculationsManager calculationsManager = CalculationsManager.getInstance();

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
    private double tomtPercent;

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
        ProjectManager.getInstance().getActiveCostItemNames().sort(Collections.reverseOrder());

        System.out.println("Added cost item \"" + name + "\" in category " + activeCategory.getDisplayName() + "!");
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

        throw new IllegalArgumentException("No cost item with name \"" + name + "\" exists in category " + category.getDisplayName());
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

    // ------------------ GETTERS & SETTERS------------------ //
    /**
     * Getter for the name of the project.
     * @return Name of the project.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ArrayList<CostItem> getHyresintäkterMedStödCostItems() {
        return costItemsMap.get(Category.HYRESINTÄKTERMEDSTÖD);
    }

    public ArrayList<CostItem> getHyresintäkterUtanStödCostItems() {
        return costItemsMap.get(Category.HYRESINTÄKTERUTANSTÖD);
    }

    public ArrayList<CostItem> getDriftOchUnderhållMedStödCostItems() {
        return costItemsMap.get(Category.DRIFTOCHUNDERHÅLLMEDSTÖD);
    }

    public ArrayList<CostItem> getDriftOchUnderhållUtanCostItems() {
        return costItemsMap.get(Category.DRIFTOCHUNDERHÅLLUTANSTÖD);
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

    public double getTotalKrPerKvmLow() {
        return calculationsManager.updatedTotalKrPerKvmLow(apartmentItems);
    }

    public double getTotalKrPerKvmHigh() {
        return calculationsManager.updatedTotalKrPerKvmHigh(apartmentItems);
    }

    public double getTotalBidrag() {
        return calculationsManager.updatedTotalBidrag(apartmentItems);
    }

    public double getTomtPercent() { return tomtPercent; }


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

    public void setTomtPercent(double tomtPercent) {
        this.tomtPercent = tomtPercent;
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

        updateInvesteringsstodKkr();
        updateInvesteringsstodKrBoa();
        updateInvesteringsstodKrBta();

        updateHyresintakterBostadMedStod();
        updateHyresintakterBostadUtanStod();

        updateDriftOchUnderhallMedStod();
        updateDriftOchUnderhallUtanStod();

        updateDriftnettoMedStod();
        updateDriftnettoUtanStod();

        updateMarknadsvardeMedStod();
        updateMarknadsvardeUtanStod();

        // Do this method  *almost*  last.
        // Should be run before updateProjektkostnad methods, and maybe some more...
        updateApartmentItemsVariables();

        updateProjektkostnadMedStodKkr();
        updateProjektkostnadMedStodKrBoa();
        updateProjektkostnadMedStodKrBta();

        updateTomtrattsavgald();

        updateProjektvinstMedStod();
        updateProjektvinstUtanStod();

        updateProjektvinstProcentMedStod();
        updateProjektvinstProcentUtanStod();

        updateProjektkostnadUtanStodKkr();
    }

    /**
     * Goes through each apartment item in the project and calculates and updates all of its variable values.
     */
    public void updateApartmentItemsVariables() {
        for(ApartmentItem apartmentItem : apartmentItems) {
            // If one of the inputs equals zero then don't do calculations. Continue to next apartment item.
            if(apartmentItem.getBOA() == 0 || apartmentItem.getAmount() == 0) {
                apartmentItem.setRentPerMonthLow(0);
                apartmentItem.setKrPerKvmLow(0);
                apartmentItem.setRentPerMonthHigh(0);
                apartmentItem.setKrPerKvmHigh(0);
                apartmentItem.setTotalBOA(0);
                apartmentItem.setTotalBOAPercent(0);
                apartmentItem.setBidrag(0);
                continue;
            }

            // Calculate and set values...
            apartmentItem.setRentPerMonthLow(calculationsManager.updatedApartmentItemRentPerMonthLow(apartmentItem, normhyraMedStod));
            apartmentItem.setKrPerKvmLow(calculationsManager.updatedApartmentItemKrPerKvmLow(apartmentItem, normhyraMedStod));
            apartmentItem.setRentPerMonthHigh(calculationsManager.updatedApartmentItemRentPerMonthHigh(apartmentItem, antagenPresumtionshyra));
            apartmentItem.setKrPerKvmHigh(calculationsManager.updatedApartmentItemKrPerKvmHigh(apartmentItem, antagenPresumtionshyra));
            apartmentItem.setTotalBOA(calculationsManager.updatedApartmentItemTotalBOA(apartmentItem));
            apartmentItem.setTotalBOAPercent(calculationsManager.updatedApartmentItemTotalBOAPercent(apartmentItem.getTotalBOA(), totalBoa));
            apartmentItem.setBidrag(calculationsManager.updatedApartmentItemBidrag(apartmentItem, investeringsstod));
        }
    }

    private void updateNumOfApt() {
        numOfApt = calculationsManager.updatedNumberOfApt(getApartmentItems());
    }
    private void updateTotalBoa() {
        totalBoa = calculationsManager.updatedTotalBoa(getApartmentItems());
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
        mervardeskattKkr = calculationsManager.updatedMervardesskattKkr(costItemsMap, totalBoa, numOfApt)
        + oforutsettKkr*0.25;
    }

    private void updateMervardesskattKrBoa() {
        mervardeskattKrBoa = calculationsManager.updatedMervardesskattKrBoa(mervardeskattKkr, totalBoa);
    }

    private void updateMervardesskattKrBta() {
        mervardeskattKrBta = calculationsManager.updatedMervardesskattKrBta(mervardeskattKkr, totalLjusBta);
    }

    private void updateProjektkostnadMedStodKkr() {
        projektkostnadKkrMedStod = calculationsManager.updatedProjectCostKkr(tomtkostnaderKkr, nedlagdaByggherreKkr,anslutningarKkr,
                byggherrekostnaderKkr, entreprenadKkr, oforutsettKkr, finansiellaKostnaderKkr,
                mervardeskattKkr, investeringsstodKkr);
    }

    private void updateProjektkostnadMedStodKrBoa() {
        projektkostnadKrBoa = calculationsManager.updatedProjectCostKrBoa(projektkostnadKkrMedStod, totalBoa);
    }

    private void updateProjektkostnadMedStodKrBta() {
        projektkostnadKrBta = calculationsManager.updatedProjectCostKrBta(projektkostnadKkrMedStod, totalLjusBta);
    }

    private void updateProjektkostnadUtanStodKkr() {
        projektkostnadKkr = projektkostnadKkrMedStod-investeringsstodKkr;
    }

    private void updateInvesteringsstodKkr() {
       investeringsstodKkr = calculationsManager.updatedSubsidyKKr(getInvesteringsstod(), getApartmentItems());
    }

    private void updateInvesteringsstodKrBoa() {
        investeringsstodKrBoa = calculationsManager.updatedSubsidyKrBoa(investeringsstodKkr, totalBoa);
    }

    private void updateInvesteringsstodKrBta() {
        investeringsstodKrBta = calculationsManager.updatedSubsidyKrBta(investeringsstodKkr, totalLjusBta);
    }


    private void updateHyresintakterBostadMedStod() {
        hyresintakterMedStod = calculationsManager.updatedHyresintakter(apartmentItems, getHyresintäkterMedStödCostItems(), normhyraMedStod, totalBoa);
    }

    private void updateHyresintakterBostadUtanStod() {
        hyresintakterUtanStod = calculationsManager.updatedHyresintakter(apartmentItems, getHyresintäkterUtanStödCostItems(), antagenPresumtionshyra, totalBoa);
    }

    // Denna och nästa måste bli olika med resp utan stöd
    private void updateDriftOchUnderhallMedStod() {
        driftUnderhallMedStod = -calculationsManager.updatedDriftOchUnderhall(getDriftOchUnderhållMedStödCostItems(), totalBoa);
    }

    private void updateDriftOchUnderhallUtanStod() {
        driftUnderhallUtanStod = -calculationsManager.updatedDriftOchUnderhall(getDriftOchUnderhållUtanCostItems(), totalBoa);
    }

    // Samma beräkning för med och utan stöd
    private void updateTomtrattsavgald() {
        tomtrattsavgaldMedStod = -(tomtkostnaderKkr*tomtPercent)/100;
        tomtrattsavgaldUtanStod = -(tomtkostnaderKkr*tomtPercent)/100;
    }

    private void updateDriftnettoMedStod() {
        driftnettoMedStod = hyresintakterMedStod+driftUnderhallMedStod+tomtrattsavgaldMedStod;
    }

    private void updateDriftnettoUtanStod() {
        driftnettoUtanStod = hyresintakterUtanStod+driftUnderhallUtanStod+tomtrattsavgaldUtanStod;
    }

    private void updateMarknadsvardeMedStod() {
        if(yieldMedStod == 0) {
            marknadsvardeMedStod = 0;
            return;
        }
        marknadsvardeMedStod = driftnettoMedStod/(yieldMedStod/100);
    }

    private void updateMarknadsvardeUtanStod() {
        if(yieldUtanStod == 0) {
            marknadsvardeUtanStod = 0;
            return;
        }
        marknadsvardeUtanStod = driftnettoUtanStod/(yieldUtanStod/100);
    }

    private void updateProjektvinstMedStod() {
        projektvinstMedStod = marknadsvardeMedStod-projektkostnadKkrMedStod;
    }

    private void updateProjektvinstUtanStod() {
        projektvinstUtanStod = marknadsvardeUtanStod-projektkostnadKkr;
    }

    private void updateProjektvinstProcentMedStod() {
        if(projektkostnadKkrMedStod == 0) {
            projektvinstProcentMedStod = 0;
            return;
        }
        projektvinstProcentMedStod = projektvinstMedStod/projektkostnadKkrMedStod*100;
    }

    private void updateProjektvinstProcentUtanStod() {
        if(projektkostnadKkr == 0) {
            projektvinstProcentUtanStod = 0;
            return;
        }
        projektvinstProcentUtanStod = projektvinstUtanStod/projektkostnadKkr*100;
    }
}
