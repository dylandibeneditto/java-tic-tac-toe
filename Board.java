public class Board {
    /**
     * Used to store which places on the board are occupied by a player
     */
    public Boolean[][] mask;

    /**
     * <p>
     * Used in combination with the mask to find the state of any grid item
     * </p>
     * 
     * 
     * <h3>Cases</h3>
     * <p>
     * mask: false, side: false -> <b>Empty Square</b>
     * </p>
     * <p>
     * mask: true, side: false -> <b>X</b>
     * </p>
     * <p>
     * mask: true, side: true -> <b>O</b>
     * </p>
     */
    public Boolean[][] side;

    /**
     * Arg init
     */
    public Board(Boolean[][] _mask, Boolean[][] _side) {
        mask = _mask != null ? _mask : new Boolean[][] { 
            { false, false, false }, 
            { false, false, false }, 
            { false, false, false } 
        };
        side = _side != null ? _side : new Boolean[][] { 
            { false, false, false }, 
            { false, false, false }, 
            { false, false, false } 
        };
    }

    /**
     * No arg init
     */
    public Board() {
        this(new Boolean[][] { 
                { false, false, false }, 
                { false, false, false }, 
                { false, false, false } 
            }, 
            new Boolean[][] { 
                { false, false, false }, 
                { false, false, false }, 
                { false, false, false } 
            }
        );
    }

    /**
     * Creates copy of board under new object
     * */
    public Board getCopy() {
        Boolean[][] maskCopy = new Boolean[mask.length][mask[0].length];
        Boolean[][] sideCopy = new Boolean[side.length][side[0].length];
        
        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask[i].length; j++) {
                maskCopy[i][j] = mask[i][j];
                sideCopy[i][j] = side[i][j];
            }
        }

        return new Board(maskCopy, sideCopy);
    }

    // Evaluate the board: +1 if the computer wins, -1 if the opponent wins, 0 otherwise
    public int evaluate(Boolean turn) {
        // Check for wins in rows, columns, and diagonals
        for (int row = 0; row < 3; row++) {
            if (mask[row][0] && mask[row][1] && mask[row][2] &&
                side[row][0] == side[row][1] && side[row][1] == side[row][2]) {
                if (side[row][0] == turn) {
                    return +1;
                } else {
                    return -1;
                }
            }
        }
        for (int col = 0; col < 3; col++) {
            if (mask[0][col] && mask[1][col] && mask[2][col] &&
                side[0][col] == side[1][col] && side[1][col] == side[2][col]) {
                if (side[0][col] == turn) {
                    return +1;
                } else {
                    return -1;
                }
            }
        }
        if (mask[0][0] && mask[1][1] && mask[2][2] &&
            side[0][0] == side[1][1] && side[1][1] == side[2][2]) {
            if (side[0][0] == turn) {
                return +1;
            } else {
                return -1;
            }
        }
        if (mask[0][2] && mask[1][1] && mask[2][0] &&
            side[0][2] == side[1][1] && side[1][1] == side[2][0]) {
            if (side[0][2] == turn) {
                return +1;
            } else {
                return -1;
            }
        }
        return 0; // No winner yet
    }
}