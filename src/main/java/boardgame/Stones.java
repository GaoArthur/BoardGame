package boardgame;

public enum Stones {

    EMPTY,
    RED,
    YELLOW,
    GREEN;

    public static Stones of(int s) {
        return values()[s];
    }

}
