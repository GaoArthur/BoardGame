package boardgame.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {

    @Test
    void testSetInitial() {
        BoardGame boardGame = new BoardGame();
        assertEquals(new BoardGame((new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        })),boardGame);
    }

    @Test
    void testClickStone() {
        BoardGame boardGame = new BoardGame();
        boardGame.ClickStone(1,1);
        assertEquals(new BoardGame((new int[][]{
                {0, 0, 0},
                {0, 2, 0},
                {0, 0, 0}
        })),boardGame);
    }

    @Test
    void testIsFinished() {
        assertFalse(new BoardGame().isFinished());
        assertFalse(new BoardGame((new int[][]{
                {1, 0, 0},
                {0, 2, 0},
                {0, 0, 1}
        })).isFinished());
        assertTrue(new BoardGame((new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        })).isFinished());
        assertTrue(new BoardGame((new int[][]{
                {1, 0, 0},
                {1, 1, 0},
                {1, 0, 0}
        })).isFinished());
        assertTrue(new BoardGame((new int[][]{
                {1, 1, 1},
                {2, 2, 0},
                {2, 0, 0}
        })).isFinished());
    }

    @Test
    void testToString() {
        BoardGame boardGame = new BoardGame();
        assertEquals("EMPTY EMPTY EMPTY \n"
                + "EMPTY EMPTY EMPTY \n"
                + "EMPTY EMPTY EMPTY \n", boardGame.toString());
    }
}