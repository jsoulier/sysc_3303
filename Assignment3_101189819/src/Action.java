/**
 * The actions between states.
 */
public enum Action {

    /** The signal green light to vehicles action. */
    GREEN,

    /** The signal yellow light to vehicles action. */
    YELLOW,

    /** The signal red light to vehicles action. */
    RED,

    /** The signal walk light to pedestrians action. */
    WALK,

    /** The signal don't walk light to pedestrians action. */
    DONT_WALK,

    /** The signal blank light to pedestrians action. */
    BLANK;

    /**
     * Print the action to stdout.
     */
    public void print() {
        System.out.println(this.name());
    }
}
