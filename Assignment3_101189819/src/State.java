/**
 * The generic state for all the states to implement.
 */
interface State {

    /**
     * Get the first state.
     * @param context The context to set the state.
     */
    public static void start(Context context) {
        context.setState(new GreenState());
    }

    /**
     * Print the state information.
     */
    public void print();

    /**
     * Sleep the caller thread by a time depending on the state.
     */
    public void sleep();

    /**
     * Handle TIMEOUT event.
     * @param context The context to set the state.
     */
    public void handleTimeout(Context context);

    /**
     * Handle PEDESTRIAN_WAITING event.
     * @param context The context to set the state.
     */
    public void handlePedestrianWaiting(Context context);
}

/**
 * The uninterruptible green light state.
 */
class GreenState implements State {

    /** Flag for if a pedestrian is waiting. */
    private boolean isPedestrianWaiting;

    /**
     * Create a new green light state.
     */
    public GreenState() {
        isPedestrianWaiting = false;
    }

    /**
     * Print the state information.
     */
    public void print() {
        Action.DONT_WALK.print();
        Action.GREEN.print();
    }

    /**
     * Sleep the caller thread.
     */
    public void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle TIMEOUT event.
     * @param context The context to set the state.
     */
    public void handleTimeout(Context context) {
        if (isPedestrianWaiting) {
            context.setState(new YellowState());
        } else {
            context.setState(new GreenIntState());
        }
    }

    /**
     * Handle PEDESTRIAN_WAITING event.
     * @param context The context to set the state.
     */
    public void handlePedestrianWaiting(Context context) {
        isPedestrianWaiting = true;
    }
}

/**
 * The interruptible green light state.
 */
class GreenIntState implements State {

    /**
     * Print the state information.
     */
    public void print() {

    }

    /**
     * Sleep the caller thread.
     */
    public void sleep() {

    }

    /**
     * Handle TIMEOUT event.
     * @param context The context to set the state.
     */
    public void handleTimeout(Context context) {

    }

    /**
     * Handle PEDESTRIAN_WAITING event.
     * @param context The context to set the state.
     */
    public void handlePedestrianWaiting(Context context) {
        context.setState(new YellowState());
    }
}

/**
 * The yellow light state.
 */
class YellowState implements State {

    /**
     * Print the state information.
     */
    public void print() {
        Action.YELLOW.print();
    }

    /**
     * Sleep the caller thread.
     */
    public void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle TIMEOUT event.
     * @param context The context to set the state.
     */
    public void handleTimeout(Context context) {
        context.setState(new WalkState());
    }

    /**
     * Handle PEDESTRIAN_WAITING event.
     * @param context The context to set the state.
     */
    public void handlePedestrianWaiting(Context context) {
    
    }
}

/**
 * The walk light state.
 */
class WalkState implements State {

    /**
     * Print the state information.
     */
    public void print() {
        Action.RED.print();
        Action.WALK.print();
    }

    /**
     * Sleep the caller thread.
     */
    public void sleep() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle TIMEOUT event.
     * @param context The context to set the state.
     */
    public void handleTimeout(Context context) {
        context.setState(new FlashState());
    }

    /**
     * Handle PEDESTRIAN_WAITING event.
     * @param context The context to set the state.
     */
    public void handlePedestrianWaiting(Context context) {

    }
}

/**
 * The flashing light state.
 */
class FlashState implements State {

    /** The maximum number of flashes. */
    private static final int pedestrianFlashCtrMax = 7;

    /** The remaining number of flashes. */
    private int pedestrianFlashCtr;

    /**
     * Create a new flashing light state.
     */
    public FlashState() {
        pedestrianFlashCtr = pedestrianFlashCtrMax;
    }

    /**
     * Print the state information.
     */
    public void print() {

    }

    /**
     * Sleep the caller thread.
     */
    public void sleep() {
        while (pedestrianFlashCtr > 0) {
            if ((pedestrianFlashCtr & 1) == 0) {
                Action.DONT_WALK.print();
            } else {
                Action.BLANK.print();
            }
            try {
                Thread.sleep(2000 / pedestrianFlashCtrMax);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pedestrianFlashCtr--;
        }
    }

    /**
     * Handle TIMEOUT event.
     * @param context The context to set the state.
     */
    public void handleTimeout(Context context) {
        context.setState(new GreenState());
    }

    /**
     * Handle PEDESTRIAN_WAITING event.
     * @param context The context to set the state.
     */
    public void handlePedestrianWaiting(Context context) {

    }
}
