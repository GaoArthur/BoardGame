package boardgame.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StonesTest {

    @Test
    void testOf() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Stones.of(-1));
        assertEquals(Stones.EMPTY, Stones.of(0));
        assertEquals(Stones.GREEN, Stones.of(1));
        assertEquals(Stones.RED, Stones.of(2));
        assertEquals(Stones.YELLOW, Stones.of(3));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Stones.of(7));
    }
}