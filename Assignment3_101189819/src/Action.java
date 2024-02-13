public enum Action {
    GREEN,
    YELLOW,
    RED,
    WALK,
    DONT_WALK,
    BLANK;

    public void print() {
        System.out.println(this.name().toUpperCase());
    }
}
