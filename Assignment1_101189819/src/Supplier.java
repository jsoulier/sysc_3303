import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The supplier thread.
 */
public class Supplier implements Runnable {

    /** The shared counter. */
    private Counter counter = null;

    /** The ingredients for the counter. */
    private Ingredient[] ingredients = null;

    /**
     * Create a new supplier.
     * @param counter The shared counter.
     */
    public Supplier(Counter counter) {
        this.counter = counter;
        this.ingredients = new Ingredient[2];
    }

    /**
     * Put two random and unique ingredients on the counter.
     * @return False if the supplier should stop.
     */
    private boolean putIngredients() {

        // Get the ingredients as a list
        List<Ingredient> ingredients = Arrays.asList(Ingredient.values());

        // Shuffle the list
        Collections.shuffle(ingredients);

        // Get the first two ingredients to guarantee random and uniqueness
        this.ingredients[0] = ingredients.get(0);
        this.ingredients[1] = ingredients.get(1);

        // Place the ingredients on the counter
        return counter.putIngredients(this.ingredients);
    }

    /**
     * Start the supplier thread and supply ingredients until the counter says stop.
     */
    @Override
    public void run() {
        while (putIngredients()) {}
    }
}
