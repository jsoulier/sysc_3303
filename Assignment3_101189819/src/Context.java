/**
 * The context or state machine for the states.
 */
public class Context {

    /** The state to be used by the thread. */
    private State currentState;

    /** The state being used by the thread. */
    private State oldState;

    /**
     * Create a new context. Creates a thread to transition between the states.
     */
    public Context() {
        // get the first state
        State.start(this);

        // create thread to transition between the states
        new Thread(() -> {
            while (true) {
                oldState = currentState;
                oldState.sleep();
                timeout();
            }
        }).start();
    }

    /**
     * Send TIMEOUT event to the state being used by the thread.
     */
    public void timeout() {
        oldState.handleTimeout(this);
    }

    /**
     * Send PEDESTRIAN_WAITING event to the state to be used by the thread.
     */
    public void pedestrianWaiting() {
        currentState.handlePedestrianWaiting(this);
    }

    /**
     * Set the new state.
     * @param state The new state.
     */
    public void setState(State state) {
        currentState = state;
        currentState.print();
    }
}
