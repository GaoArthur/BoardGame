package boardgame.state;

public enum Stones {

    EMPTY,
    GREEN,
    RED,
    YELLOW;

    public static Stones of(int s) {
        return values()[s];
    }

    public int getValue() {
        return ordinal();
    }
}
