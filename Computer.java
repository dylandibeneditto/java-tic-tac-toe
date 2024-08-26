public class Computer extends Player {

    public Computer(Boolean playerTurn) {
        super(playerTurn);
    }

    // Check if there are any moves left on the board
    private boolean isMovesLeft(Board board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!board.mask[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // Minimax function
    public int minimax(Board board, int depth, boolean isMax) {
        int score = board.evaluate(turn);

        // If the computer has won the game
        if (score == 1) {
            return score - depth; // Subtract depth to prioritize quicker wins
        }

        // If the opponent has won the game
        if (score == -1) {
            return score + depth; // Add depth to prioritize delaying loss
        }

        // If there are no moves left and no winner
        if (!isMovesLeft(board)) {
            return 0;
        }

        // Maximizer's move (Computer)
        if (isMax) {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if the cell is empty
                    if (!board.mask[i][j]) {
                        // Make the move
                        board.mask[i][j] = true;
                        board.side[i][j] = turn;

                        // Call minimax recursively and choose the maximum value
                        best = Math.max(best, minimax(board, depth + 1, false));

                        // Undo the move
                        board.mask[i][j] = false;
                        board.side[i][j] = null;
                    }
                }
            }
            return best;
        }

        // Minimizer's move (Opponent)
        else {
            int best = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if the cell is empty
                    if (!board.mask[i][j]) {
                        // Make the move
                        board.mask[i][j] = true;
                        board.side[i][j] = !turn;

                        // Call minimax recursively and choose the minimum value
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
        int[] bestMove = {-1, -1};

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
        return super.makeMove(board, bestMove[1], bestMove[0]); // x and y might be flipped depending on your board's row-column setup
    }
}