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
 * Return codes for controller:     *
 * 11: Tile placed                  *
 * 12: Field occupied               *
 * 13: Illegal placement            *
 *                                  *
 * 21: Tile flipped                 *
 * 22: Trying to flip own tile      *
 * 23: No tile on field             *
 *                                  *
 * 31: End of turn                  *
 * 32: You have to forfeit turn     *
 * 33: Tiles left to flip           *
 *                                  *
 * 41: White wins                   *
 * 42: Black wins                   *
 * 43: Draw                         *
 *                                  *
 * -------------------------------- */
package basicreversi;

public class Board {
    int[][] board; // Board field integer array

    public Board() { // Constructor for board
        board = new int[8][8]; // Initialises with 0 values
    }

    public int[][] getBoard() {
        return board; // Returns board array
    }

    public int flip(int row, int column, int colour) {
        int code; // Return code declared
        if (board[row][column] == 0) { // If board element is 'empty'
            code = 23; // No tile on field
        } else if (board[row][column] == colour) { // If tile is players own
            code = 22; // Trying to flip own tile
        } else { // Else
            board[row][column] = colour; // Flip tile
            code = 21; // Tile flipped
        }
        return code;
    }

    // public int[][] flippable(int row, int column, int colour) {
        // int[][] positions; // Positions of flippable tiles
        // int[][] neighbours; // Player tiles own neighbours
        // boolean search = true;
        // for (int i = row; i > 0; i--) {
            // if (board[i-1][column] == colour){
                // neighbours[] = 
            // }
        // }

        // return positions; // Return flippable tiles
    // }

}
