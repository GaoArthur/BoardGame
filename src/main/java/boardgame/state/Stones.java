package boardgame.state;

/**
 * A enumeration of the needed type of Stones.
 */
public enum Stones {

    /**
     * Color of the stones, order by the name of the image file.
     */

    /**
     * When there is no stone.
     */
    EMPTY,
    /**
     * When the stone is GREEN.
     */
    GREEN,
    /**
     * When the stone is RED.
     */
    RED,
    /**
     * When the stone is YELLOW.
     */
    YELLOW;

    /**
     * @param s is the Stone
     * @return the value of that stone
     */
    public static Stones of(int s) {
        return values()[s];
    }

    /**
     * @return The ordinal of this enumeration constant
     */
    public int getValue() {
        return ordinal();
    }
}
