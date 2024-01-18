import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The supplier thread for the baristas.
 */
public class Supplier extends Thread {

    /** The supplied ingredients. */
    private Ingredient[] ingredients = null;

    /** The current number of cups supplied. */
    private int cups = 0;

    /** The maximum number of cups to supply. */
    private int maxCups = 0;

    /** Check if there are available ingredients. */
    private boolean empty = true;

    /**
     * Create a new supplier.
     * @param maxCups The maximum number of cups to supply.
     */
    Supplier(int maxCups) {
        super();

        // Initialize instance variables
        this.ingredients = new Ingredient[2];
        this.maxCups = maxCups;
    }

    /**
     * Get ingredients from the supplier and create a cup.
     *
     * Waits until the barista can acquire the synchronized lock and until the
     * supplier has ingredients available. If the maximum number of cups has been
     * reached, returns false telling the barista to stop.
     * 
     * If the barista has the ingredient required to complete a cup, the barista
     * prints the information regarding the cup, and increments the cup counter.
     *
     * @param ingredient The ingredient supplied from the barista.
     * @return False if the barista should stop.
     */
    public synchronized boolean getIngredients(Ingredient ingredient) {

        // Loop while there are no ingredients and the maximum number of cups has
        // not been reached
        while (empty && cups < maxCups) {
           try {
               wait();
           } catch (InterruptedException e) {}
        }

        // If the maximum number of cups has been reached
        if (cups >= maxCups) {
            return false;
        }

        // If the barista does not have the required ingredient
        if (Arrays.asList(ingredients).contains(ingredient)) {
            return true;
        }

        // Print the information regarding the cup
        String string = "";
        string += "Consumed Ingredients For Coffee ";
        string += String.valueOf(cups);
        string += ": ";
        string += ingredients[0];
        string += ", ";
        string += ingredients[1];
        string += ", ";
        string += ingredient;
        System.out.println(string);

        // Increment the cup counter
        cups++;
        
        // Unlock the supplier thread
        empty = true;
        notifyAll();

        return true;
    }

    /**
     * Put ingredients from the supplier to create a cup.
     * 
     * Waits until the barista can acquire the synchronized lock and until the
     * barista has used the previous ingredients.
     * 
     * Ingredients are acquired by randomly shuffling the available ingredients
     * and acquiring the first two ingredients. Uniqueness is guaranteed between
     * ingredients.
     */
    public synchronized void putIngredients() {

        // Loop while there are ingredients
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        // Shuffle the available ingredients and acquire the first two
        List<Ingredient> ingredients = Arrays.asList(Ingredient.values());
        Collections.shuffle(ingredients);
        this.ingredients[0] = ingredients.get(0);
        this.ingredients[1] = ingredients.get(1);

        // Print the information regarding the ingredients
        String string = "Supplied Ingredients For Coffee ";
        string += String.valueOf(cups);
        string += ": ";
        string += this.ingredients[0];
        string += ", ";
        string += this.ingredients[1];
        System.out.println(string);

        // Unlock the barista threads
        empty = false;
        notifyAll();
    }

    /**
     * The thread entry point.
     * 
     * Puts ingredients until the maximum number of cups has been reached.
     */
    @Override
    public void run() {
        while (cups < maxCups - 1) {
            putIngredients();
        }
    }
}
