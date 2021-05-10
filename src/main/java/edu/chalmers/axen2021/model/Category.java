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
    OFÖRUTSETT("Oförutsett", ""),
    FINANSIELLAKOSTNADER("Finansiella kostnader", "kr/BOA"),
    //MERVÄRDESKATT("Mervärdeskatt"),
    //INVESTERINGSSTÖD("Investeringsstöd"),
    HYRESINTÄKTER("Hyresintäkter", ""),
    DRIFTOCHUNDERHÅLL("Drift & Underhåll", "");
    //TOMTRÄTTSAVGÄLD("Tomträttsavgäld"),
    //DRIFTNETTO("Driftnetto"),
    //YIELD("Yield");

    /**
     * String name of the category. Must be the same as the string on the category buttons in the input view.
     */
    private String name;

    /**
     * Unit of the category (e.g. "kkr" or "kr/BOA").
     */
    private String unit;

    Category(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    /**
     * Gets a Category from a string input.
     * @param text the category to get.
     * @return Category found from the string.
     */
    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.name.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No enum Category value with name " + text + " found");
    }

    /**
     * Gets the name of the Category.
     * @return name variable.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the unit of the Category.
     * @return unit variable.
     */
    public String getUnit() {
        return unit;
    }
}
