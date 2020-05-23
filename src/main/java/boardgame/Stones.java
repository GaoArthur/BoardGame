package boardgame;

public enum Stones {

    EMPTY,
    RED,
    YELLOW,
    GREEN;

    public static Stones of(int s) {
        return values()[s];
    }

    public Stones stones(){
        switch (this) {
            case EMPTY:return RED;
            case RED:return YELLOW;
            case YELLOW:return GREEN;
        }
        throw new AssertionError();
    }

}
