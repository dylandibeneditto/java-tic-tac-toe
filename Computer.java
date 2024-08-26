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
    if (!board.isMovesLeft()) {
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