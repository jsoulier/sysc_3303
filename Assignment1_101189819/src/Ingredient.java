/**
 * The ingredients.
 */
public enum Ingredient {

    // The valid ingredients
    COFFEE_BEANS("Coffee Beans"),
    WATER("Water"),
    SUGAR("Sugar");

    /** The name for the ingredient. */
    private final String name;

    /**
     * Create a new ingredient.
     * @param name The name for the ingredient.
     */
    private Ingredient(String name) {
        this.name = name;
    }

    /**
     * Get the name of the ingredient.
     * @return The name of the ingredient.
     */
    public String toString() {
       return this.name;
    }
}
