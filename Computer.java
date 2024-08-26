public class Computer extends Player {

    public Computer(Boolean playerTurn) {
        super(playerTurn);
    }

    public int minimax(Board board, int depth, boolean isMax) {
        int score = board.evaluate(turn);
    
        // If the computer has won the game
        if (score == -1) {
            return score - depth; // Subtract depth to prioritize quicker wins
        }
    
        // If the opponent has won the game
        if (score == 1) {
            return score + depth; // Add depth to prioritize delaying loss
        }
    
        // draw
        if (!board.isMovesLeft()) {
            return 0;
        }
    
        // max move
        if (isMax) {
            int best = Integer.MIN_VALUE;
    
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if the cell is empty
                    if (!board.mask[i][j]) {
                        // Make the move
                        board.mask[i][j] = true;
                        board.side[i][j] = turn;
    
                        best = Math.max(best, minimax(board, depth + 1, false));
    
                        // Undo the move
                        board.mask[i][j] = false;
                        board.side[i][j] = null;
                    }
                }
            }
            return best;
        }
    
        // min move
        else {
            int best = Integer.MAX_VALUE;
    
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if the cell is empty
                    if (!board.mask[i][j]) {
                        // Make the move
                        board.mask[i][j] = true;
                        board.side[i][j] = !turn;
    
                        best = Math.min(best, minimax(board, depth + 1, true));
    
                        // Undo the move
                        board.mask[i][j] = false;
                        board.side[i][j] = null;
                    }
                }
            }
            return best;
        }
    }

    // This will be called to find the best move for the computer
    public int[] findBestMove(Board board) {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = { -1, -1 };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if the cell is empty
                if (!board.mask[i][j]) {
                    // Make the move
                    board.mask[i][j] = true;
                    board.side[i][j] = turn;

                    // Compute evaluation function for this move
                    int moveVal = minimax(board, 0, false);

                    // Undo the move
                    board.mask[i][j] = false;
                    board.side[i][j] = null;

                    // If the value of the current move is more than the best value, update best
                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    // Override makeMove to use the best move found by minimax
    @Override
    public Board makeMove(Board board, int x, int y) {
        int[] bestMove = findBestMove(board);
        return super.makeMove(board, bestMove[0], bestMove[1]);
    }
}