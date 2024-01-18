/**
 * The main class.
 */
public class Main {

    /**
     * The main function defining the initial execution point of the application.
     * @param args Unused. Required for correct main signature.
     */
    public static void main(String[] args) {

        // Initialize counter, baristas, and supplier
        Counter counter = new Counter(20);
        counter.serveCustomers();
    }
}