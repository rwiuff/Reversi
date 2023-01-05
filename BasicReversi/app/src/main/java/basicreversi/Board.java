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
    int boardsize = 8; // n in n x n dimensional board
    HashMap<String, String> players = new HashMap<String, String>(); // Player's colour assignment
    HashMap<Integer, Integer> codeLog = new HashMap<Integer, Integer>(); // Log of codes for turns

    public Board() { // Constructor for board
        board = new int[boardsize][boardsize]; // Initialises with 0 values
        setPlayers();
    }

    public void setPlayers() {
        Random rand = new Random();
        if (rand.nextInt(0, 2) == 0) {
            players.put("Player 1", "white");
            players.put("Player 2", "black");
        } else {
            players.put("Player 1", "black");
            players.put("Player 2", "white");
        }
    }

    public int[][] getBoard() {
        return board; // Returns board array
    }

    public int getTurn() {
        return turnCount;
    }

    public HashMap<String, String> getPlayers() {
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

    public int place(int row, int column, int colour) {
        // Check if the position is already occupied
        if (board[row][column] != 0) {
            return 12;
        } else if (legality(row, column, colour) == 13) {
            return 13;
        } else {
            board[row][column] = colour;
            return 11;
        }
    }

    // hasEmptyPositions() iterates through the elements of the board array and
    // checks if any of them have the value 0.
    // If it finds an empty position, it returns true.
    // If it finishes iterating through the board and doesn't find any empty
    // positions, it returns false.

    public boolean hasEmptyPositions() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public int flip(int row, int column, char coulour) {
        int colourint = colourConvert(coulour);
        return 31;
    }

    public int legality(int row, int column, int colour) {
        
        return 0;
    }
}
