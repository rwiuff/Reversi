package basicreversi;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class BoardDriver {
    private static DecimalFormat df = new DecimalFormat(" 0");

    public static void main(String[] args) {

        // --------- Multi-directional test --------- //
        System.out.println("--------- Multi-directional test ---------");
        Board a = new Board();
        a.turnState(1);
        System.out.println(a.getGreeting());
        a.setPiece(2, 3, 2);
        a.setPiece(2, 4, 1);
        a.setPiece(3, 2, 2);
        a.setPiece(3, 3, 2);
        a.setPiece(3, 4, 1);
        a.setPiece(4, 2, 1);
        a.setPiece(4, 3, 1);
        a.setPiece(4, 4, 2);
        printBoard(a);
        a.turnState(1);
        a.place(2, 2, 1);
        printBoard(a);
        ArrayList<int[]> flipped = a.getFlipped();
        for (int i = 0; i < flipped.size(); i++) {
            System.out.println("Flipped tiles: " + flipped.get(i)[0] + "," + flipped.get(i)[1]);
        }
        System.out.println("--------- Multi-directional test ---------");
        System.out.println("");
        // --------- Multi-directional test --------- //

        // ------------ turnState test ------------- //
        System.out.println("------------ turnState test -------------");
        a.resetBoard();
        printBoard(a);
        testTurnState(a, 1);
        a.setPiece(4, 4, 1);
        a.setPiece(3, 3, 1);
        a.setPiece(3, 4, 2);
        a.setPiece(4, 3, 2);
        printBoard(a);
        testTurnState(a, 2);
        System.out.println("------------ turnState test -------------");
        System.out.println("");
        // ------------ turnState test ------------- //

        // ---- gameState and checkWinner test ---- //
        System.out.println("---- gameState and checkWinner test ----");
        testGameState(a);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                a.setPiece(i, j, 1);
            }
        }
        printBoard(a);
        testGameState(a);
        testCheckWinner(a);
        System.out.println("---- gameState and checkWinner test ----");
        System.out.println("");
        // ---- gameState and checkWinner test ---- //

        // ------------ setPlayers test ------------ //
        System.out.println("------------ setPlayers test ------------");
        a.resetBoard();
        testSetPlayers(a, 1, "Spiller 1", 2, "Spiller 2");
        System.out.println("------------ setPlayers test ------------");
        System.out.println("");
        // ------------ setPlayers test ------------ //

        // ---------- Switch player test ----------- //
        System.out.println("---------- Switch player test -----------");
        Board b = new Board();
        String oldWhite = b.getPlayers().get("White");
        String oldBlack = b.getPlayers().get("Black");
        System.out.println("White: " + b.getPlayers().get("White"));
        System.out.println("Black: " + b.getPlayers().get("Black"));
        b.resetBoard();
        String newWhite = oldBlack;
        String newBlack = oldWhite;
        b.setPlayers(1, newWhite, 2, newBlack);
        System.out.println("White: " + b.getPlayers().get("White"));
        System.out.println("Black: " + b.getPlayers().get("Black"));
        System.out.println("---------- Switch player test -----------");
        System.out.println("");
        // ---------- Switch player test ----------- //

        // -------------- Console game: ------------- //
        // consoleReversi();
        // -------------- Console game: ------------- //
    }

    private static void printBoard(Board board) {
        int[][] b = board.getBoard();
        int size = b.length;
        System.out.print("    ");
        for (int col = 0; col < size; col++) {
            System.out.print(col + "  ");
        }
        System.out.println();
        System.out.println("  #" + "---".repeat(size) + "#");
        for (int row = 0; row < size; row++) {
            System.out.print(row + " |");
            for (int col = 0; col < size; col++) {
                System.out.print(df.format(b[row][col]) + " ");
            }
            System.out.println("|");
        }
        System.out.println("  #" + "---".repeat(size) + "#");
    }

    private static void testTurnState(Board board, int colour) {
        switch (board.turnState(colour)) {
            case 21:
                System.out.println("Legal move on board");
                break;
            case 22:
                System.out.println("Forfeit");
                break;
            default:
                System.out.println("Unexpected code " + board.turnState(colour));
        }
    }

    private static void testGameState(Board board) {
        if (board.gameOver()) {
                System.out.println("End of game");
        }
    }

    private static void testCheckWinner(Board board) {
        switch (board.checkWinner()) {
            case 41:
                System.out.println("White wins");
                break;
            case 42:
                System.out.println("Black wins");
                break;
            default:
                System.out.println("Unexpected code " + board.checkWinner());
        }
    }

    private static void testSetPlayers(Board board, int colour1, String player1, int colour2, String player2) {
        board.setPlayers();
        System.out.println("Players random assignment:");
        System.out.println("White: " + board.getPlayers().get("White"));
        System.out.println("Black: " + board.getPlayers().get("Black"));
        board.setPlayers(0, "Spiller 1", 0, "Spiller 2");
        System.out.println("Players random assignment, custom names:");
        System.out.println("White: " + board.getPlayers().get("White"));
        System.out.println("Black: " + board.getPlayers().get("Black"));
        board.setPlayers(1, "Spiller 1", 2, "Spiller 2");
        System.out.println("Players custom assignment, custom names:");
        System.out.println("White: " + board.getPlayers().get("White"));
        System.out.println("Black: " + board.getPlayers().get("Black"));
        board.setPlayers(2, "Spiller 1", 1, "Spiller 2");
        System.out.println("White: " + board.getPlayers().get("White"));
        System.out.println("Black: " + board.getPlayers().get("Black"));
    }

    private static void consoleReversi() {
        Board b = new Board();
        Scanner console = new Scanner(System.in);
        System.out.println("Hvid: " + b.getPlayers().get("White"));
        int colour;
        String playastring;
        printBoard(b);
        while (b.getTurn() < 2) {
            System.out.println(b.getPlayers().get("White") + ", placer dine startbrikker: x,y");
            String moveStr;
            moveStr = console.next();
            int[] move = new int[2];
            move[0] = Integer.parseInt(moveStr.split(",")[0]);
            move[1] = Integer.parseInt(moveStr.split(",")[1]);
            b.initplace(move[0], move[1], 1);
            printBoard(b);
        }
        while (b.gameOver() == false) {
            if (b.getTurn() % 2 == 0) {
                colour = 2;
                playastring = b.getPlayers().get("Black");
            } else {
                colour = 1;
                playastring = b.getPlayers().get("White");
            }
            switch (b.turnState(colour)) {
                case 21:
                    System.out.println(playastring + " (" + colour + ") " + ", ya move: x,y");
                    String moveStr;
                    moveStr = console.next();
                    int[] move = new int[2];
                    move[0] = Integer.parseInt(moveStr.split(",")[0]);
                    move[1] = Integer.parseInt(moveStr.split(",")[1]);
                    System.out.println(b.place(move[0], move[1], colour));
                    printBoard(b);
                    break;
                case 22:
                    System.out.println("No legal moves. Fofeit turn.");
            }
        }
        System.out.println(b.checkWinner());
    }
}
