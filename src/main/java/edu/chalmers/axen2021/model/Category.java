package edu.chalmers.axen2021.model;

/**
 * Enum class for the different (modal) categories.
 * @author Sam Salek
 */
public enum Category {
    // Some of these are commented because they don't have cost items, AKA no button in the inputView.
    // They should only be uncommented if they have a button.
    // Their variable values still exists in the Project class.
    TOMTKOSTNADER("Tomtkostnader", "kr"),
    NEDLAGDABYGGHERRE("Nedlagda bygherre", "kr"),
    ANSLUTNINGAR("Anslutningar", "kr/lgh"),
    BYGGHERREKOSTNADER("Byggherrekostnader", "kr"),
    ENTREPENAD("Entrepenad", "kr/BOA"),
    //OFÖRUTSETT("Oförutsett", ""),
    FINANSIELLAKOSTNADER("Finansiella kostnader", "kr/BOA"),
    //MERVÄRDESKATT("Mervärdeskatt"),
    //INVESTERINGSSTÖD("Investeringsstöd"),
    HYRESINTÄKTERMEDSTÖD("Med stöd ", "Hyresintäkter med stöd","kr"),
    HYRESINTÄKTERUTANSTÖD("Utan stöd ","Hyresintäkter utan stöd" ,"kr"),
    DRIFTOCHUNDERHÅLLMEDSTÖD("Med stöd","Drift och Underhåll med stöd", "kr/kvm"),
    DRIFTOCHUNDERHÅLLUTANSTÖD("Utan stöd","Drift och Underhåll utan stöd", "kr/kvm");
    //TOMTRÄTTSAVGÄLD("Tomträttsavgäld"),
    //DRIFTNETTO("Driftnetto"),
    //YIELD("Yield");

    /**
     * String text of the button connected to the category.
     * Must be EXACTLY the same as the string on the category buttons in the inputView.
     */
    private final String buttonText;

    /**
     * The name used when displaying the category.
     */
    private final String displayName;



    /**
     * Unit of the category (e.g. "kkr" or "kr/BOA").
     */
    private final String unit;

    /**
     * Constructor. Used when the button text is the same as the display name.
     * @param buttonText Text of the button connected to the category.
     * @param unit The unit for the category.
     */
    Category(String buttonText, String unit) {
        this.buttonText = buttonText;
        this.displayName = buttonText;
        this.unit = unit;
    }

    /**
     * Constructor. Used when the button text is different from the display name.
     * @param buttonText Text of the button connected to the category.
     * @param displayName The display name for the category.
     * @param unit The unit for the category.
     */
    Category(String buttonText, String displayName, String unit) {
        this.buttonText = buttonText;
        this.displayName = displayName;
        this.unit = unit;
    }

    /**
     * Gets a Category from a string input.
     * @param text the category to get.
     * @return Category found from the string.
     */
    public static Category fromButtonText(String text) {
        for (Category category : Category.values()) {
            if (category.buttonText.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No enum Category value with name " + text + " found!");
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
