public class Player {
    /**
     * <h3>Cases:</h3>
     * <p>false -> <b>X</b></p>
     * <p>true -> <b>O</b></p>
     */
    public static Boolean turn;

    public Player(Boolean playerTurn) {
        turn = playerTurn;
    }

    public Board makeMove(Board board, int x, int y) {
        Board b = board.getCopy();
        if(!b.mask[x][y]) {
            b.mask[x][y] = true;
            b.side[x][y] = turn;
        }
        return b;
    }
}
