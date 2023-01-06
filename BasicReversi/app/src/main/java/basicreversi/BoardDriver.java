package basicreversi;

import java.text.DecimalFormat;

public class BoardDriver {
    private static DecimalFormat df = new DecimalFormat(" 0");

    public static void main(String[] args) {
        Board b = new Board();
        printBoard(b.getBoard());
        System.out.println("Player 1: " + b.getPlayers().get("Player 1"));
        System.out.println(b.place(4, 4, 1));
        printBoard(b.getBoard());
        System.out.println(b.place(4, 4, 1));
        System.out.println(b.hasEmptyPositions());
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                b.place(i, j, 1);
            }
        }
        printBoard(b.getBoard());
        System.out.println(b.hasEmptyPositions());
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