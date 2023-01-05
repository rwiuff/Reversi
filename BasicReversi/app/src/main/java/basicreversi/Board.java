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
 *   21: Tile flipped               *
 *   22: Trying to flip own tile    *
 *   23: No tile on field           *
 *                                  *
 *   31: End of turn                *
 *   32: You have to forfeit turn   *
 *                                  *
 *   41: White wins                 *
 *   42: Black wins                 *
 *   43: Draw                       *
 *                                  *
 * -------------------------------- */
package basicreversi;

import java.util.Map;

public class Board {
    int[][] board; // Board field integer array
    int turnCount; // Counts the turn number
    Map players; // Player's colour assignment
    Map codeLog; // Log of codes for turns

    public Board() { // Constructor for board
        board = new int[8][8]; // Initialises with 0 values
    }

    public int[][] getBoard() {
        return board; // Returns board array
    }

    public int getTurn() {
        return turnCount;
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

    public int flip(int row, int column, char coulour) {
        int colourint = colourConvert(coulour);

        

        return 31;
    }

}
