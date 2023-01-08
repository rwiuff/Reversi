package basicreversi;

import java.text.DecimalFormat;
import java.util.Set;

public class BoardDriver {
    private static DecimalFormat df = new DecimalFormat(" 0");

    public static void main(String[] args) {
        Board b = new Board();
        printBoard(b.getBoard());
        System.out.println("White: " + b.getPlayers().get("White"));
        System.out.println("Turn " + b.getTurn());
        b.initplace(3, 3, 1);
        printBoard(b.getBoard());
        System.out.println("Turn " + b.getTurn());
        b.initplace(4, 4, 1);
        printBoard(b.getBoard());
        b.moveAnalyser(1);
        System.out.println("Turn " + b.getTurn());
        Set<int[]> moves = b.getValidMoves().keySet();
        for (int[] key : moves) {
            b.place(key[0], key[1], 1);
        }
        printBoard(b.getBoard());
    }

    private static void printBoard(int[][] b) {
        int size = b.length;
        System.out.println("#" + "---".repeat(size) + "#");
        for (int row = 0; row < size; row++) {
            System.out.print("|");
            for (int col = 0; col < size; col++) {
                System.out.print(df.format(b[row][col]) + " ");
            }
            System.out.println("|");
        }
        System.out.println("#" + "---".repeat(size) + "#");
    }

    // if (winner == 1) {
    // System.out.println("White has won the game!");
    // } else if (winner == 2) {
    // System.out.println("Black has won the game!");
    // } else {
    // System.out.println("The game is a tie! 1/2 ");

}