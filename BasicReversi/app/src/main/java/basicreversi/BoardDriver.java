package basicreversi;

import java.text.DecimalFormat;

public class BoardDriver {
    private static DecimalFormat df = new DecimalFormat(" 0");

    public static void main(String[] args) {
        Board b = new Board();
        printBoard(b.getBoard());
        b.flip(4, 4, 1);
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
}