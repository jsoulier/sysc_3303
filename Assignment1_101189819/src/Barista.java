/**
 * The barista runnable.
 */
public class Barista implements Runnable {

    /** The shared counter. */
    private Counter counter = null;

    /** The infinite ingredient. */
    private Ingredient ingredient = null;

    /**
     * Create a new barista.
     * @param counter The shared counter.
     * @param ingredient The infinite ingredient.
     */
    Barista(Counter counter, Ingredient ingredient) {
        this.counter = counter;
        this.ingredient = ingredient;
    }

    /**
     * Start the barista thread and make coffees until the counter says stop.
     */
    @Override
    public void run() {
        while (counter.makeCoffee(ingredient)) {}
    }
}
