public class Sample {

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
