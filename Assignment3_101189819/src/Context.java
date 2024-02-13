
public class Context {
    private State currentState;
    private State oldState;

    public Context() {
        setState(new GreenState());

        new Thread(() -> {
            while (true) {
                oldState = currentState;
                timeout();
            }
        }).start();
    }

    public void timeout() {
        oldState.timeout(this);
    }

    public void pedestrianWaiting() {
        currentState.pedestrianWaiting(this);
    }

    public void setState(State state) {
        currentState = state;
        currentState.print();
    }
}
