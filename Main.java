import java.lang.Math;
import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_GRAY = "\u001B[90m";

    public static void main(String[] args) {
        clearScreen();
        Board board = new Board();
        try (Scanner scanner = new Scanner(System.in)) {
            Boolean playerSide = null;
            while (playerSide == null) {
                System.out.print("Which side would you like to be on? (x/o): ");
                String side = scanner.nextLine();
                if (side.toLowerCase().contains("x")) {
                    playerSide = false;
                } else if (side.toLowerCase().contains("o")) {
                    playerSide = true;
                }
            }
            clearScreen();
            Player player = new Player(playerSide);
            Computer computer = new Computer(!playerSide);
            while (Math.abs(board.evaluate(false)) != 1 && board.isMovesLeft()) {
                if (!playerSide) { // Player is X
                    printBoard(board, false);
                    int[] moves = getUserMoves(scanner);
                    board = player.makeMove(board, moves[0], moves[1]);
                    if (Math.abs(board.evaluate(false)) == 1 || !board.isMovesLeft())
                        break;
                    board = computer.makeMove(board, 0, 0);
                } else { // Player is O
                    board = computer.makeMove(board, 0, 0);
                    printBoard(board, true);
                    if (Math.abs(board.evaluate(false)) == 1 || !board.isMovesLeft())
                        break;
                    int[] moves = getUserMoves(scanner);
                    board = player.makeMove(board, moves[0], moves[1]);
                }
                clearScreen();
            }

            clearScreen();
            printBoard(board, playerSide);
            int result = board.evaluate(false);
            if (result == -1) {
                System.out.println(ANSI_RED + "O wins!");
            } else if (result == 1) {
                System.out.println(ANSI_BLUE + "X wins!");
            } else {
                System.out.println("It's a draw!");
            }
        }
    }

    private static void printBoard(Board board, Boolean move) {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 12; x++) {
                Boolean xBorder = x == 4 || x == 8;
                Boolean yBorder = y == 2 || y == 4;

                double xIndexRaw = (x - 2) / 4.0;
                double yIndexRaw = (y - 1) / 2.0;

                boolean isXValue = ((xIndexRaw + 1) % 1 == 0);
                boolean isYValue = ((yIndexRaw + 1) % 1 == 0);
                boolean isValue = isXValue && isYValue;

                int xIndex = (int) xIndexRaw;
                int yIndex = (int) yIndexRaw;

                if (xBorder && yBorder) { // intersection
                    System.out.print(ANSI_GRAY + "+" + ANSI_RESET);
                } else if (xBorder && !yBorder && y != 0) { // vertical line
                    System.out.print(ANSI_GRAY + "|" + ANSI_RESET);
                } else if (!xBorder && yBorder && x != 0) { // horizontal line
                    System.out.print(ANSI_GRAY + "-" + ANSI_RESET);
                } else if (isXValue && xIndex >= 0 && xIndex < 3 && y == 0) { // x-axis coordinates label
                    System.out.print(ANSI_GRAY + (xIndex + 1));
                } else if (isYValue && yIndex >= 0 && yIndex < 3 && x == 0) { // y-axis coordinates label
                    System.out.print(ANSI_GRAY + (yIndex + 1) + " ");
                } else if (x == 0) {
                    System.out.print("  ");
                } else if (isValue && xIndex >= 0 && xIndex < 3 && yIndex >= 0 && yIndex < 3) { // grid value
                    Boolean filled = board.mask[xIndex][yIndex];
                    Boolean side = board.side[xIndex][yIndex];
                    if (filled) {
                        System.out.print(side ? ANSI_RED + "O" + ANSI_RESET : ANSI_BLUE + "X" + ANSI_RESET);
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        printEval(12, board, move);
    }

    private static void printEval(int width, Board board, Boolean move) {
        Computer computer = new Computer(move);
        int eval = computer.minimax(board, 10, true);
        System.out.print("[");
        for (int i = 0; i < width - 2; i++) {
            if (i < eval + 60 / width) {
                System.out.print("@");
            } else {
                System.out.print(" ");
            }
        }
        System.out.print("]" + ANSI_GRAY + " (" + eval + " at depth 10)\n" + ANSI_RESET);
    }

    private static int[] getUserMoves(Scanner scanner) {
        int x = -1;
        int y = -1;

        Boolean xSuccess = false;
        while (!xSuccess) {
            try {
                System.out.print(ANSI_GRAY + "x: " + ANSI_RESET);
                x = scanner.nextInt();
                if (x < 1 || x > 3) {
                    throw new Exception("Out of Range");
                }
                xSuccess = true;
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Incorrect Input. Please enter a valid integer." + ANSI_RESET);
                xSuccess = false;
            }
        }

        Boolean ySuccess = false;
        while (!ySuccess) {
            try {
                System.out.print(ANSI_GRAY + "y: " + ANSI_RESET);
                y = scanner.nextInt();
                if (y < 1 || y > 3) {
                    throw new Exception("Out of Range");
                }
                ySuccess = true;
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Incorrect Input. Please enter a valid integer." + ANSI_RESET);
                ySuccess = false;
            }
        }

        int[] result = { x - 1, y - 1 };
        return result;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}