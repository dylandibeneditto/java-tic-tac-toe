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
}