package edu.chalmers.axen2021.model;

public enum Category {
    TOMTKOSTNADER("Tomtkostnader"),
    NEDLAGDABYGGHERRE("Nedlagda bygherre"),
    ANSLUTNINGAR("Anslutningar"),
    BYGGHERREKOSTNADER("Byggherrekostnader"),
    ENTREPENAD("Entrepenad"),
    OFÖRUTSETT("Oförutsett"),
    FINANSIELLAKOSTNADER("Finansiella kostnader"),
    MERVÄRDESKATT("Mervärdeskatt"),
    INVESTERINGSSTÖD("Investeringsstöd"),
    HYRESINTÄKTER("Hyresintäkter"),
    DRIFTOCHUNDERHÅLL("Drift & Underhåll"),
    TOMTRÄTTSAVGÄLD("Tomträttsavgäld"),
    DRIFTNETTO("Driftnetto"),
    YIELD("Yield");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.name.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No enum Category value with name " + text + " found");
    }
}
