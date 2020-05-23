package boardgame;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class BoardGame implements Cloneable{

    private Stones[][] board;

    public static final int[][] INITIAL = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    public static final int[][] R = {
            {1, 2, 3},
            {2, 1, 3},
            {3, 1, 2}
    };

    public BoardGame() {
        this(R);
    }

    private void setInitial(int[][] b){
        this.board = new Stones[3][3];
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                this.board[i][j]=Stones.of(b[i][j]);
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

    public static void main(String[] args) {
        BoardGame boardGame = new BoardGame();
        System.out.println(boardGame);
    }
}
