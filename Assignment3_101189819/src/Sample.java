/**
 * The sample/test code for the context.
 */
public class Sample {

    /**
     * Sleep the caller thread by seconds.
     * @param seconds The seconds to sleep.
     */
    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * The entrypoint of the sample.
     * @param args Unused
     */
    public static void main(String[] args) {
        Context context = new Context();

        // press during interruptible green light
        sleep(12);
        context.pedestrianWaiting();

        // press during uninterruptible green light
        sleep(25);
        context.pedestrianWaiting();
    }
}
