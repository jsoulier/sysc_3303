import java.util.Arrays;

/**
 * The counter holding the baristas and supplier.
 */
public class Counter {

    /** The ingredients on the counter. */
    private Ingredient[] ingredients = null;

    /** The current number of cups. */
    private int cups = 0;
    
    /** The maximum number of cups. */
    private int maxCups = 0;

    /** If the counter has ingredients. */
    private boolean hasIngredients = false;

    /**
     * Create a new counter.
     * @param maxCups The maximum number of cups.
     */
    public Counter(int maxCups) {
        this.maxCups = maxCups;
    }

    /**
     * Make a coffee with the selected ingredient.
     * If the counter has two ingredients and does not contain the selected
     * ingredient, a coffee will be made.
     * If the counter has no ingredients and there are still cups to be made,
     * the caller will wait.
     * If there are no more cups to be made, returns false.
     * @param ingredient The selected ingredient.
     * @return False if there are no more cups to be made.
     */
    public synchronized boolean makeCoffee(Ingredient ingredient) {

        // If there are still cups to be made and there are no ingredients, wait
        while (!hasIngredients && cups < maxCups) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        // If there are no cups to be made, return false
        if (cups >= maxCups) {
            return false;
        }

        // If the caller does not provide the required ingredient
        if (Arrays.asList(ingredients).contains(ingredient)) {
            return true;
        }

        // Print coffee information
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
        
        // Notify the supplier
        hasIngredients = false;
        notifyAll();

        return true;
    }

    /**
     * Put ingredients on the counter.
     * If the counter has ingredients and there are still cups to be made, the
     * caller will wait.
     * If there are no more cups to be made, returns false.
     * @param ingredients The selected ingredients.
     * @return False if there are no more cups to be made.
     */
    public synchronized boolean putIngredients(Ingredient[] ingredients) {

        // If there are still cups to be made and there are ingredients, wait
        while (hasIngredients && cups < maxCups) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        // If there are no more cups to be made, return false
        if (cups >= maxCups) {
            return false;
        }

        // Assign the new ingredients
        this.ingredients = ingredients;

        // Print supplier information
        String string = "Supplied Ingredients For Coffee ";
        string += String.valueOf(cups);
        string += ": ";
        string += this.ingredients[0];
        string += ", ";
        string += this.ingredients[1];
        System.out.println(string);

        // Notify the baristas
        hasIngredients = true;
        notifyAll();

        return true;
    }

    /**
     * Create barista and supplier threads.
     */
    public void serveCustomers() {

        // Create the supplier
        Thread supplier = new Thread(new Supplier(this));

        // Create and start the baristas using each ingredient
        for (Ingredient ingredient : Ingredient.values()) {
            Thread barista = new Thread(new Barista(this, ingredient));
            barista.start();
        }

        // Start the supplier
        supplier.start();
    }
}
