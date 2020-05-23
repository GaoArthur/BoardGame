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

    public Stones[][] ClickStone(int row, int col) {
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
        return board;
    }

    public boolean isFinished(int x, int y) {
        int countL = 0, countR = 0, i;
        if (board[x][0] == board[x][1] && board[x][1] == board[x][2]) return true;
        if (board[0][y] == board[1][y] && board[1][y] == board[2][y]) return true;
        for (i = -2; i <= 2; i++) {
            if (x + i < 0 || x + i > 2 || y + i < 0 || y + i > 2) ;
            else {
                if (board[x + i][y + i] == board[x][y]) countL++;
                if (board[x + i][y - i] == board[x][y]) countR++;
            }
            if (countL >= 3) return true;
            if (countR >= 3) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        BoardGame boardGame = new BoardGame();
        System.out.println(boardGame);
        boardGame.ClickStone(1, 1);
        System.out.println(boardGame);
        System.out.println(boardGame.isFinished(1, 1));
    }
}
