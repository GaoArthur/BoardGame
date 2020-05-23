package boardgame.state;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class BoardGame implements Cloneable {

    private Stones[][] board;
    private int rangeX, rangeY;

    public static final int[][] INITIAL = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    public static final int[][] R = {
            {1, 2, 3},
            {2, 2, 3},
            {3, 1, 2}
    };

    public BoardGame() {
        this(R);
    }

    private void setInitial(int[][] b) {
        this.board = new Stones[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = Stones.of(b[i][j]);
            }
        }
    }

    public BoardGame(int[][] initial) {
        setInitial(initial);
    }

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

    public BoardGame clone() {
        BoardGame copy = null;
        try {
            copy = (BoardGame) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        copy.board = new Stones[board.length][];
        for (int i = 0; i < board.length; ++i) {
            copy.board[i] = board[i].clone();
        }
        return copy;
    }

    public static void main(String[] args) throws Exception {
        BoardGame boardGame = new BoardGame();
        System.out.println(boardGame);
        boardGame.ClickStone(1, 1);
        System.out.println(boardGame);
        System.out.println(boardGame.isFinished());
    }
}
