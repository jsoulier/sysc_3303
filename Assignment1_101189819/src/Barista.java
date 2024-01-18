/**
 * The barista thread.
 */
public class Barista extends Thread {

    /** The shared supplier. */
    private Supplier supplier = null;

    /** The infinite ingredient. */
    private Ingredient ingredient = null;

    /**
     * Create a new barista.
     * @param supplier The shared supplier.
     * @param ingredient The infinite ingredient.
     */
    Barista(Supplier supplier, Ingredient ingredient) {
        super();

        // Initialize instance variables
        this.supplier = supplier;
        this.ingredient = ingredient;
    }

    /**
     * The thread entry point.
     * 
     * Gets ingredients until the maximum number of cups has been reached.
     */
    @Override
    public void run() {
        while (supplier.getIngredients(ingredient)) {}
    }
}
