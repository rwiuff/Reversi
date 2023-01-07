package basicreversi;

import java.text.DecimalFormat;
import java.util.HashMap;

public class BoardDriver {
    private static DecimalFormat df = new DecimalFormat(" 0");

    public static void main(String[] args) {
        Board b = new Board();
        printBoard(b.getBoard());
        System.out.println("White: " + b.getPlayers().get("White"));
        System.out.println(b.place(3, 3, 1));
        System.out.println(b.place(4, 4, 1));
        System.out.println(b.place(3, 4, 2));
        System.out.println(b.place(4, 3, 2));
        b.legality(1);
        HashMap<String, HashMap<String, int[]>> moves = b.getValidMoves();
        printBoard(b.getBoard());
        b.legality(1);
        
        System.out.println(b.checkWinner());
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

//if (winner == 1) {
//System.out.println("White has won the game!");
//} else if (winner == 2) {
//System.out.println("Black has won the game!");
//} else {
//System.out.println("The game is a tie! 1/2 ");

}