/**
 * The main class.
 */
public class Main {

    /**
     * The main function defining the initial execution point of the application.
     * @param args Unused. Required for correct main signature.
     */
    public static void main(String[] args) {

        // Create the shared supplier
        Supplier supplier = new Supplier(20);

        // Create the different baristas using the shared supplier
        for (Ingredient ingredient : Ingredient.values()) {
            Barista barista = new Barista(supplier, ingredient);
            barista.start();
        }

        // Start the shared supplier to also start the baristas
        supplier.start();
    }
}