package edu.chalmers.axen2021.model;

/**
 * Enum class for the different (modal) categories.
 * @author Sam Salek
 */
public enum Category {
    // Some of these are commented because they don't have cost items, AKA no button in the inputView.
    // They should only be uncommented if they have a button.
    // Their variable values still exists in the Project class.
    TOMTKOSTNADER("tomtkostnaderButton", "Tomtkostnader","kr"),
    NEDLAGDABYGGHERRE("nedlagdaByggherreButton", "Nedlagda byggherre","kr"),
    ANSLUTNINGAR("anslutningarButton", "Anslutningar","kr/lgh"),
    BYGGHERREKOSTNADER("byggherrekostnaderButton", "Byggherrekostnader","kr"),
    ENTREPENAD("entrepenadButton","Entrepenad", "kr/BOA"),
    //OFÖRUTSETT("Oförutsett", ""),
    FINANSIELLAKOSTNADER("finansiellaKostnaderButton", "Finansiella kostnader","kr/BOA"),
    //MERVÄRDESKATT("Mervärdeskatt"),
    //INVESTERINGSSTÖD("Investeringsstöd"),
    HYRESINTÄKTERMEDSTÖD("hyresintäkterMedStödButton", "Hyresintäkter med stöd","kr/kvm"),
    HYRESINTÄKTERUTANSTÖD("hyresintäkterUtanStödButton","Hyresintäkter utan stöd" ,"kr/kvm"),
    DRIFTOCHUNDERHÅLLMEDSTÖD("driftOchUnderhållMedStödButton","Drift och Underhåll med stöd", "kr/kvm"),
    DRIFTOCHUNDERHÅLLUTANSTÖD("driftOchUnderhållUtanStödButton","Drift och Underhåll utan stöd", "kr/kvm");
    //TOMTRÄTTSAVGÄLD("Tomträttsavgäld"),
    //DRIFTNETTO("Driftnetto"),
    //YIELD("Yield");

    /**
     * Fx:id of the button connected to the category.
     * Must be EXACTLY the same as the id on the category buttons in the inputView.
     */
    private final String buttonFxid;

    /**
     * The name used when displaying the category.
     */
    private final String displayName;

    /**
     * Unit of the category (e.g. "kkr" or "kr/BOA").
     */
    private final String unit;

    /**
     * Constructor.
     * @param buttonFxid Text of the button connected to the category.
     * @param displayName The display name for the category.
     * @param unit The unit for the category.
     */
    Category(String buttonFxid, String displayName, String unit) {
        this.buttonFxid = buttonFxid;
        this.displayName = displayName;
        this.unit = unit;
    }

    /**
     * Gets a Category from a string input.
     * @param id Id of the category to get.
     * @return Category found from the string.
     */
    public static Category fromButtonFxid(String id) {
        for (Category category : Category.values()) {
            if (category.buttonFxid.equalsIgnoreCase(id)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No enum Category value with id " + id + " found!");
    }

    /**
     * Gets the display name of the Category.
     * @return displayName variable.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the unit of the Category.
     * @return unit variable.
     */
    public String getUnit() {
        return unit;
    }
}
