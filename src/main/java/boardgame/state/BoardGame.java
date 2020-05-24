package boardgame.state;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The Main part to control the logic of the game.
 */
@Data
@Slf4j
public class BoardGame implements Cloneable {

    private Stones[][] board;

    /**
     * The initial state of the board, all stones are EMPTY.
     */
    public static final int[][] INITIAL = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    /**
     * A random state of the board, for the test.
     */
    public static final int[][] R = {
            {1, 2, 3},
            {2, 2, 3},
            {3, 1, 2}
    };

    /**
     * Initialize the BoardGame.
     */
    public BoardGame() {
        this(INITIAL);
    }

    /**
     * Make the initial value into the board.
     * @param b board
     */
    private void setInitial(int[][] b) {
        this.board = new Stones[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = Stones.of(b[i][j]);
            }
        }
    }

    /**
     * Set the board into initial.
     * @param initial Initial board
     */
    public BoardGame(int[][] initial) {
        setInitial(initial);
    }

    /**
     * Override the toString function.
     * @return the matrix of the board
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Stones[] stones : board) {
            for (Stones stone : stones) {
                stringBuilder.append(stone).append(' ');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    /**
     * When click the stone, the stone will be change,
     * The order is EMPTY>RED>YELLOW>GREEN, and stay at GREEN.
     * @param row the row number of the board
     * @param col the column number of the board
     */
    public void ClickStone(int row, int col) {
        Stones color = board[row][col];
        switch (color) {
            case EMPTY:
                board[row][col] = Stones.RED;
                break;
            case RED:
                board[row][col] = Stones.YELLOW;
                break;
            case YELLOW:
                board[row][col] = Stones.GREEN;
                break;
            case GREEN:
                log.info("Stone [{}][{}] GREEN cannot be changed.", row, col);
        }
        log.info("Stone [{}][{}] {} is changed into {}.", row, col, color, board[row][col]);
    }

    /**
     * Check whether the game is finished, there should be at least one line is in same color.
     * @return Whether the goal is reached
     */
    public boolean isFinished() {
        if (board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != Stones.EMPTY) return true;
        if (board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != Stones.EMPTY) return true;
        if (board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != Stones.EMPTY) return true;
        if (board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != Stones.EMPTY) return true;
        if (board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != Stones.EMPTY) return true;
        if (board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != Stones.EMPTY) return true;
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != Stones.EMPTY) return true;
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != Stones.EMPTY) return true;
        return false;
    }

    /**
     * A simple test whether the function above is working.
     * Detail test are in the Test folder
     * @param args
     */
    public static void main(String[] args){
        BoardGame boardGame = new BoardGame();
        System.out.println(boardGame);
        boardGame.ClickStone(1, 1);
        System.out.println(boardGame);
        System.out.println(boardGame.isFinished());
    }
}
