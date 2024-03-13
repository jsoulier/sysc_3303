/**
 * The generic state for all the states to implement.
 */
interface State {

    /**
     * Get the first state.
     * @param context The context to set the state.
     */
    public static void start(Context context) {
        context.setState(new VehiclesEnabledState());
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
class VehiclesEnabledState implements State {

    /**
     * Print the state information.
     */
    public void print() {
        Action.DONT_WALK.print();
    }

    /**
     * Sleep the caller thread.
     */
    public void sleep() {}

    /**
     * Handle TIMEOUT event.
     * @param context The context to set the state.
     */
    public void handleTimeout(Context context) {
        context.setState(new VehiclesGreenState());
    }

    /**
     * Handle PEDESTRIAN_WAITING event.
     * @param context The context to set the state.
     */
    public void handlePedestrianWaiting(Context context) {}
}

/**
 * The uninterruptible green light state.
 */
class VehiclesGreenState implements State {

    /** Flag for if a pedestrian is waiting. */
    private boolean isPedestrianWaiting;

    /**
     * Create a new state.
     */
    public VehiclesGreenState() {
        isPedestrianWaiting = false;
    }

    /**
     * Print the state information.
     */
    public void print() {
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
            context.setState(new VehiclesYellowState());
        } else {
            context.setState(new VehiclesGreenIntState());
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
class VehiclesGreenIntState implements State {

    /**
     * Print the state information.
     */
    public void print() {}

    /**
     * Sleep the caller thread.
     */
    public void sleep() {}

    /**
     * Handle TIMEOUT event.
     * @param context The context to set the state.
     */
    public void handleTimeout(Context context) {}

    /**
     * Handle PEDESTRIAN_WAITING event.
     * @param context The context to set the state.
     */
    public void handlePedestrianWaiting(Context context) {
        context.setState(new VehiclesYellowState());
    }
}

/**
 * The yellow light state.
 */
class VehiclesYellowState implements State {

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
        context.setState(new PedestriansEnabledState());
    }

    /**
     * Handle PEDESTRIAN_WAITING event.
     * @param context The context to set the state.
     */
    public void handlePedestrianWaiting(Context context) {}
}

/**
 * The walk light state.
 */
class PedestriansEnabledState implements State {

    /**
     * Print the state information.
     */
    public void print() {
        Action.RED.print();
    }

    /**
     * Sleep the caller thread.
     */
    public void sleep() {}

    /**
     * Handle TIMEOUT event.
     * @param context The context to set the state.
     */
    public void handleTimeout(Context context) {
        context.setState(new PedestriansWalkState());
    }

    /**
     * Handle PEDESTRIAN_WAITING event.
     * @param context The context to set the state.
     */
    public void handlePedestrianWaiting(Context context) {}
}

/**
 * The walk light state.
 */
class PedestriansWalkState implements State {

    /**
     * Print the state information.
     */
    public void print() {
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
        context.setState(new PedestriansFlashState());
    }

    /**
     * Handle PEDESTRIAN_WAITING event.
     * @param context The context to set the state.
     */
    public void handlePedestrianWaiting(Context context) {}
}

/**
 * The flashing light state.
 */
class PedestriansFlashState implements State {

    /** The maximum number of flashes. */
    private static final int pedestrianFlashCtrMax = 7;

    /** The remaining number of flashes. */
    private int pedestrianFlashCtr;

    /**
     * Create a new state.
     */
    public PedestriansFlashState() {
        pedestrianFlashCtr = pedestrianFlashCtrMax;
    }

    /**
     * Print the state information.
     */
    public void print() {}

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
        context.setState(new VehiclesGreenState());
    }

    /**
     * Handle PEDESTRIAN_WAITING event.
     * @param context The context to set the state.
     */
    public void handlePedestrianWaiting(Context context) {}
}
