package basicreversi;

import java.text.DecimalFormat;
import java.util.Scanner;

public class BoardDriver {
    private static DecimalFormat df = new DecimalFormat(" 0");

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Board b = new Board();
        b.setPiece(2, 3, 2);
        b.setPiece(2, 4, 1);
        b.setPiece(3, 2, 2);
        b.setPiece(3,3,2);
        b.setPiece(3, 4, 1);
        b.setPiece(4, 2, 1);
        b.setPiece(4,3,1);
        b.setPiece(4, 4, 2);
        printBoard(b.getBoard());
        b.turnState(1);
        b.place(2, 2, 1);
        printBoard(b.getBoard());

        // System.out.println(b.getPlayers().get("White") + ": White");
        // int colour;
        // int opponent;
        // String playastring;
        // printBoard(b.getBoard());
        // for (int i = b.getTurn(); i < 2; i++) {
        //     System.out.println(b.getPlayers().get("White") + ", placer dine startbrikker: x,y");
        //     String moveStr;
        //     moveStr = console.next();
        //     int[] move = new int[2];
        //     move[0] = Integer.parseInt(moveStr.split(",")[0]);
        //     move[1] = Integer.parseInt(moveStr.split(",")[1]);
        //     b.initplace(move[0], move[1], 1);
        //     printBoard(b.getBoard());
        // }
        // while (b.gameState() == 32) {
        //     if(b.getTurn() % 2 == 0){
        //         colour = 2;
        //         opponent = (colour == 2) ? 1 : 2;
        //         playastring = b.getPlayers().get("Black");
        //     } else {
        //         colour = 1;
        //         opponent = (colour == 1) ? 2 : 1;
        //         playastring = b.getPlayers().get("White");
        //     }
        //     System.out.println(b.turnState(colour));
        //     System.out.println(colour);
        //     System.out.println(playastring + ", ya move: x,y");
        //     String moveStr;
        //     moveStr = console.next();
        //     int[] move = new int[2];
        //     move[0] = Integer.parseInt(moveStr.split(",")[0]);
        //     move[1] = Integer.parseInt(moveStr.split(",")[1]);
        //     System.out.println(b.place(move[0], move[1], colour));
        //     printBoard(b.getBoard());
        // }
        // System.out.println(b.checkWinner());
    }

    private static void printBoard(int[][] b) {
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

}