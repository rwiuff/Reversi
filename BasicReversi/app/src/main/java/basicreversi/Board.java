/* -------------------------------- *
 *            Board Class           *
 * -------------------------------- *
 *                                  *
 *  Constructor for reversi board   *
 *  with methods for placing tiles, *
 *  flipping tiles and checking     *
 *  legality of moves.              *
 *                                  *
 *                                  *
 *   Return codes for controller:   *
 *   11: Tile placed                *
 *   12: Field occupied             *
 *   13: Illegal placement          *
 *                                  *
 *   21: End of turn                *
 *   22: You have to forfeit turn   *
 *                                  *
 *   31: Game continues             *
 *   32: 2 x forfeits               *
 *   33: Board filled               *
 *                                  *
 *   41: White wins                 *
 *   42: Black wins                 *
 *   43: Draw                       *
 *                                  *
 * -------------------------------- */
package basicreversi;

import java.util.HashMap;
import java.util.Random;

public class Board {
    int[][] board; // Board field integer array
    int turnCount; // Counts the turn number
    HashMap<String, String> players = new HashMap<String, String>(); // Player's colour assignment
    HashMap<Integer, Integer> codeLog = new HashMap<Integer, Integer>(); // Log of codes for turns

    public Board() { // Constructor for board
        board = new int[8][8]; // Initialises with 0 values
        setPlayers();
    }

    public void setPlayers() {
        Random rand = new Random();
        if (rand.nextInt(0, 2) == 0) {
            players.put("Player 1", "w");
            players.put("Player 2", "b");
        } else {
            players.put("Player 1", "b");
            players.put("Player 2", "w");
        }
    }

    public int[][] getBoard() {
        return board; // Returns board array
    }

    public int getTurn() {
        return turnCount;
    }

    public HashMap<String, String> getPlayers(){
        return players;
    }

    public int colourConvert(char colour) {
        int colourint;
        if (colour == 'a') {
            colourint = 1;
        } else {
            colourint = 2;
        }
        return colourint;
    }

    // public boolean placePoint(int row, int column, int value) {
    //     // Check if the position is already occupied
    //     if (board.get(row).get(column) != null) {
    //         return false;
    //     }

    //     // Place the point
    //     board.get(row).set(column, value);
    //     return true;

    // }

    public int flip(int row, int column, char coulour) {
        int colourint = colourConvert(coulour);

        return 31;
    }

}
