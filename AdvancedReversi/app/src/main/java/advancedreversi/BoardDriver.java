package advancedreversi;

import java.text.DecimalFormat;
import java.util.Scanner;

public class BoardDriver {
    private static DecimalFormat df = new DecimalFormat(" 0");

    public static void main(String[] args) {
        // -------------- Console game: ------------- //
        consoleReversi();
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

    private static void consoleReversi() {
        Board b = new Board();
        Scanner console = new Scanner(System.in);
        System.out.println("Hvid: " + b.getPlayers().get(1));
        int colour;
        String playastring;
        printBoard(b);
        while (b.getTurn() < 2) {
            System.out.println(b.getPlayers().get(1) + ", placer dine startbrikker: x,y");
            String moveStr;
            moveStr = console.next();
            int[] move = new int[2];
            move[0] = Integer.parseInt(moveStr.split(",")[0]);
            move[1] = Integer.parseInt(moveStr.split(",")[1]);
            b.initPlace(move[0], move[1], 1);
            printBoard(b);
        }
        while (b.gameOver() == false) {
            if (b.getTurn() % 2 == 0) {
                colour = 2;
                playastring = b.getPlayers().get(2);
            } else {
                colour = 1;
                playastring = b.getPlayers().get(1);
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
        console.close();
    }
}
