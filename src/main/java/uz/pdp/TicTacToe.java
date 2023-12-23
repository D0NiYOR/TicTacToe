package uz.pdp;

import java.util.Scanner;


public class TicTacToe {

    static char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printBoard(board);
        char playerSymbol, computerSymbol;

        System.out.println("Choose your symbol (X or O):");
        char chosenSymbol = scanner.next().toUpperCase().charAt(0);
        playerSymbol = chosenSymbol;
        computerSymbol = (chosenSymbol == 'X') ? 'O' : 'X';

        System.out.println("Choose mode:");
        System.out.println("1. Play against a friend");
        System.out.println("2. Play against the computer");
        int mode = scanner.nextInt();

        while (true) {
            printBoard(board);

            if (mode == 1) {
                playTurn(board, playerSymbol);
                if (checkWin(board, playerSymbol)) {
                    System.out.println("Player wins!");
                    break;
                }
            } else {
                playTurn(board, playerSymbol);
                if (checkWin(board, playerSymbol)) {
                    printBoard(board);
                    System.out.println("You win!");
                    break;
                }

                playComputerTurn(board, computerSymbol);
                if (checkWin(board, computerSymbol)) {
                    printBoard(board);
                    System.out.println("Computer wins!");
                    break;
                }
            }

            if (isBoardFull(board)) {
                printBoard(board);
                System.out.println("It's a tie!");
                break;
            }

            if (mode == 1) {
                playerSymbol = (playerSymbol == 'X') ? 'O' : 'X';
            }
        }

        scanner.close();
    }

    private static void printBoard(char[][] board) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    private static void playTurn(char[][] board, char symbol) {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        do {
            System.out.println("Enter row (1-3) and column (1-3) separated by space:");
            row = scanner.nextInt() - 1;
            col = scanner.nextInt() - 1;
        } while (!isValidMove(board, row, col));

        board[row][col] = symbol;
    }

    private static boolean isValidMove(char[][] board, int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }

    private static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWin(char[][] board, char symbol) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }
        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
               (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    private static void playComputerTurn(char[][] board, char symbol) {
        System.out.println("Computer's turn:");

        int[] bestMove = minimax(board, symbol);
        board[bestMove[0]][bestMove[1]] = symbol;
    }

    private static int[] minimax(char[][] board, char symbol) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = (symbol == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = symbol;
                    int score = minimaxHelper(board, 0, false, (symbol == 'O') ? 'X' : 'O');
                    board[i][j] = ' ';

                    if ((symbol == 'O' && score > bestScore) || (symbol == 'X' && score < bestScore)) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    private static int minimaxHelper(char[][] board, int depth, boolean isMaximizing, char symbol) {
        if (checkWin(board, 'O')) {
            return 1;
        }
        if (checkWin(board, 'X')) {
            return -1;
        }
        if (isBoardFull(board)) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = symbol;
                        bestScore = Math.max(bestScore, minimaxHelper(board, depth + 1, !isMaximizing, (symbol == 'O') ? 'X' : 'O'));
                        board[i][j] = ' ';
                    }
                }
            }
            return bestScore;
        }
        else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = symbol;
                        bestScore = Math.min(bestScore, minimaxHelper(board, depth + 1, !isMaximizing, (symbol == 'O') ? 'X' : 'O'));
                        board[i][j] = ' ';
                    }
                }
            }
            return bestScore;
        }
    }
}
