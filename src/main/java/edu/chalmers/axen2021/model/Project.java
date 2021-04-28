package edu.chalmers.axen2021.model;

import java.io.Serializable;
import java.util.ArrayList;

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

    private ArrayList<CostItem> tomtKostnader = new ArrayList<>();
    private ArrayList<CostItem> nedlagdaByggherre = new ArrayList<>();
    private ArrayList<CostItem> anslutningar = new ArrayList<>();
    private ArrayList<CostItem> byggherrekostnader = new ArrayList<>();
    private ArrayList<CostItem> entrepenad = new ArrayList<>();
    private ArrayList<CostItem> oförutsett = new ArrayList<>();
    private ArrayList<CostItem> finansiellakostnader = new ArrayList<>();
    private ArrayList<CostItem> mervärdeskatt = new ArrayList<>();
    private ArrayList<CostItem> investeringsstöd = new ArrayList<>();
    private ArrayList<CostItem> hyresintäkter = new ArrayList<>();
    private ArrayList<CostItem> driftOchUnderhåll = new ArrayList<>();
    private ArrayList<CostItem> tomträttsAvgäld = new ArrayList<>();
    private ArrayList<CostItem> driftNetto = new ArrayList<>();
    private ArrayList<CostItem> yield = new ArrayList<>();

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
        Model.getInstance().addProject(this);
    }

    /**
     * Method that creates a costItem used for the categories (contains name, comment, etc.)
     */
    public void addCostItem(String name) {

        // TODO - please improve
        if(ProjectManager.getInstance().getActiveCategory() == Category.TOMTKOSTNADER) {
            CostItem costItem = new CostItem(name);
            tomtKostnader.add(costItem);
            ProjectManager.getInstance().getTomtKostnader().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.NEDLAGDABYGGHERRE) {
            CostItem costItem = new CostItem(name);
            nedlagdaByggherre.add(costItem);
            ProjectManager.getInstance().getNedlagdaByggherre().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.ANSLUTNINGAR) {
            CostItem costItem = new CostItem(name);
            anslutningar.add(costItem);
            ProjectManager.getInstance().getAnslutningar().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.BYGGHERREKOSTNADER) {
            CostItem costItem = new CostItem(name);
            byggherrekostnader.add(costItem);
            ProjectManager.getInstance().getByggherrekostnader().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.ENTREPENAD) {
            CostItem costItem = new CostItem(name);
            entrepenad.add(costItem);
            ProjectManager.getInstance().getEntrepenad().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.OFÖRUTSETT) {
            CostItem costItem = new CostItem(name);
            oförutsett.add(costItem);
            ProjectManager.getInstance().getOförutsett().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.FINANSIELLAKOSTNADER) {
            CostItem costItem = new CostItem(name);
            finansiellakostnader.add(costItem);
            ProjectManager.getInstance().getFinansiellakostnader().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.MERVÄRDESKATT) {
            CostItem costItem = new CostItem(name);
            mervärdeskatt.add(costItem);
            ProjectManager.getInstance().getMervärdeskatt().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.INVESTERINGSSTÖD) {
            CostItem costItem = new CostItem(name);
            investeringsstöd.add(costItem);
            ProjectManager.getInstance().getInvesteringsstöd().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.HYRESINTÄKTER) {
            CostItem costItem = new CostItem(name);
            hyresintäkter.add(costItem);
            ProjectManager.getInstance().getHyresintäkter().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.DRIFTOCHUNDERHÅLL) {
            CostItem costItem = new CostItem(name);
            driftOchUnderhåll.add(costItem);
            ProjectManager.getInstance().getDriftOchUnderhåll().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.TOMTRÄTTSAVGÄLD) {
            CostItem costItem = new CostItem(name);
            tomträttsAvgäld.add(costItem);
            ProjectManager.getInstance().getTomträttsAvgäld().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.DRIFTNETTO) {
            CostItem costItem = new CostItem(name);
            driftNetto.add(costItem);
            ProjectManager.getInstance().getDriftNetto().add(costItem.getName());
        }
        else if(ProjectManager.getInstance().getActiveCategory() == Category.YIELD) {
            CostItem costItem = new CostItem(name);
            yield.add(costItem);
            ProjectManager.getInstance().getYield().add(costItem.getName());
        }
    }

    // ------------------ GETTERS ------------------ //
    /**
     * Getter for the 'name' variable.
     * @return The 'name' variable.
     */
    public String getName() {
        return name;
    }

    public ArrayList<CostItem> getTomtKostnader() {
        return tomtKostnader;
    }

    public ArrayList<CostItem> getNedlagdaByggherre() {
        return nedlagdaByggherre;
    }

    public ArrayList<CostItem> getAnslutningar() {
        return anslutningar;
    }

    public ArrayList<CostItem> getByggherrekostnader() {
        return byggherrekostnader;
    }

    public ArrayList<CostItem> getEntrepenad() {
        return entrepenad;
    }

    public ArrayList<CostItem> getOförutsett() {
        return oförutsett;
    }

    public ArrayList<CostItem> getFinansiellakostnader() {
        return finansiellakostnader;
    }

    public ArrayList<CostItem> getMervärdeskatt() {
        return mervärdeskatt;
    }

    public ArrayList<CostItem> getInvesteringsstöd() {
        return investeringsstöd;
    }

    public ArrayList<CostItem> getHyresintäkter() {
        return hyresintäkter;
    }

    public ArrayList<CostItem> getDriftOchUnderhåll() {
        return driftOchUnderhåll;
    }

    public ArrayList<CostItem> getTomträttsAvgäld() {
        return tomträttsAvgäld;
    }

    public ArrayList<CostItem> getDriftNetto() {
        return driftNetto;
    }

    public ArrayList<CostItem> getYield() {
        return yield;
    }

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
