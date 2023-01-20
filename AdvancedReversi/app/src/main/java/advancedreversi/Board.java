/* --------------------------------- *
 *            Board Class            *
 * --------------------------------- *
 *                                   *
 *  Constructor for reversi board    *
 *  with methods for placing pieces, *
 *  flipping pieces and checking     *
 *  legality of moves.               *
 *                                   *
 *                                   *
 *   Return codes for controller:    *
 *   11: Piece placed                *
 *   12: Field occupied              *
 *   13: Illegal placement           *
 *                                   *
 *   21: Legal move on board         *
 *   22: Forfeit                     *
 *                                   *
 *   31: End of game                 *
 *   32: Game continues              *
 *                                   *
 *   41: White wins                  *
 *   42: Black wins                  *
 *   43: Draw                        *
 *                                   *
 * --------------------------------- */
package advancedreversi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Board {
    private int[][] board; // Board field integer array
    private int turnCount = 0; // Counts the turn number
    private int boardsize = 8; // n in n x n dimensional board
    private ArrayList<int[]> flipped = new ArrayList<int[]>();
    private HashMap<Integer, String> players = new HashMap<Integer, String>(); // Player's colour assignment
    private HashMap<String, HashMap<Integer, HashMap<Integer, Integer[]>>> validMoves; //

    public Board() { // Constructor for board
        board = new int[boardsize][boardsize]; // Initialises with 0 values
        setPlayers(); // Assigns players
    }

    public String getGreeting() {
        return "Kilroy was here";
    }

    public int[][] getBoard() {
        return board; // Returns board array
    }

    public void setPiece(int row, int column, int colour) {
        board[row][column] = colour; // Manually places piece disregarding gameplay rules
    }

    public int getTurn() {
        return turnCount; // Return turn number
    }

    public void turnClock() {
        turnCount++; // Increment turn number
    }

    public HashMap<Integer, String> getPlayers() {
        return players; // Returns hashmap of player names and identifiers
    }

    public HashMap<String, HashMap<Integer, HashMap<Integer, Integer[]>>> getValidMoves() {
        return validMoves; // Returns hashmap of hashmaps of hashmaps conaining all valid moves at time
                           // generated
    }

    public void setPlayers() {
        players.put(1, "Player 1"); // Default Player 1 values
        players.put(2, "Player 2"); // Default Player 2 values
    }

    public void setPlayers(int identifier1, String player1, int identifier2, String player2) {
        players.put(1, player1); // Custom Player 1 values
        players.put(2, player2); // CUstom Player 2 values
    }

    public void setPlayerName(int indentifier, String playerName) {
        players.put(indentifier, playerName); // Overwrites player name and identifier
    }

    public void resetBoard() {
        for (int i = 0; i < 8; i++) { // For each row
            for (int j = 0; j < 8; j++) { // For each column
                board[i][j] = 0; // Empty position
            }
        }
        turnCount = 0; // Reset turn number
    }

    public int turnState(int colour) {
        moveAnalyser(colour); // Generate hashmap of valid moves
        if (validMoves.keySet().isEmpty()) { // If there is no valid moves
            turnClock(); // Next turn
            return 22; // Return code 22
        }
        return 21; // Else return 21
    }

    public int initPlace(int row, int column, int colour) { // Places pieces with special conditions for the first moves
        if (board[row][column] != 0) // If position is not empty
            return 12;
        if (row > 2 && row < 5) { // If position is within middle rows
            if (column > 2 && column < 5) { // If position is within middle columns
                if (turnCount == 0) { // If this is the first placement
                    board[row][column] = colour; // Assign piece to position
                    turnClock(); // Increment turn
                } else if (turnCount == 1) { // If this is the second placement
                    board[row][column] = colour; // Assign piece to position
                    for (int i = 3; i < 5; i++) { // For middle rows
                        for (int j = 3; j < 5; j++) { // For middle columns
                            int opponent = (colour == 1) ? 2 : 1; // Assign opponent colour
                            if (board[i][j] == 0) { // If position is empty
                                board[i][j] = opponent; // Place opponent piece
                                flipped.add(new int[] { i, j }); // Add piece to flipped log
                                turnClock(); // Increment turn count
                            }
                        }
                    }
                }
                return 11;
            } else {
                return 13;
            }
        }
        return 0;
    }

    public int place(int row, int column, int colour) {
        String move = "" + row + "," + column; // Construkt string from position
        if (board[row][column] != 0) { // If position is occupied
            return 12;
        } else if (validMoves.containsKey(move)) { // If move string is within validMove Hashmap
            board[row][column] = colour; // Assign colour to position
            turnClock(); // Increment turncounter
            flip(move, colour); // Flip incapsulated opponent pieces
            return 11;
        } else {
            return 13;
        }

    }

    public void flip(String move, int colour) {
        if (flipped.size() < 0) // If fliplog is not empty
            flipped.clear(); // Clear
        HashMap<Integer, HashMap<Integer, Integer[]>> directionSet = validMoves.get(move); // Get Directions for placed
                                                                                           // piece from validMoves
        Set<Integer> directions = directionSet.keySet(); // Get set of directions with flippable pieces
        for (Integer direction : directions) { // For each direction
            HashMap<Integer, Integer[]> flipSet = directionSet.get(direction); // Get flippable pieces from direction
            Set<Integer> flips = flipSet.keySet(); // Get set of flippable pieces
            for (Integer flip : flips) { // For each flippable piece
                Integer[] k = flipSet.get(flip); // Get position
                board[k[0]][k[1]] = colour; // Flip piece
                flipped.add(new int[] { k[0], k[1] });
            }
        }
    }

    // Returns the result of the match, depending on who has more pieces on the
    // board.
    public int checkWinner() {
        int Black = 0;
        int White = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int value = board[i][j];
                if (value == 1) {
                    White++;
                } else if (value == 2) {
                    Black++;
                }
            }
        }

        if (Black > White) {
            return 42;
        } else if (White > Black) {
            return 41;
        } else {
            return 43;
        }
    }

    // Returns the number of white pieces on the board.
    public int checkWhiteScore() {
        int White = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int value = board[i][j];
                if (value == 1) {
                    White++;
                }
            }
        }
        return White;
    }

    // Returns the number of black pieces on the board.
    public int checkBlackScore() {
        int Black = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int value = board[i][j];
                if (value == 2) {
                    Black++;
                }
            }
        }
        return Black;
    }

    public void moveAnalyser(int colour) {
        validMoves = new HashMap<>(); // Map og valid moves
        HashMap<Integer, Integer[]> flips; // Map of flippable opponent pieces
        int direction; // Search direction
        int[] ownPiece; // Neighbouring own piece
        int[][] checkboard = new int[boardsize + 2][boardsize + 2]; // New board with padding zeros
        int opponent = (colour == 1) ? 2 : 1; // Opponent colour assignment

        for (int i = 0; i < boardsize; i++) { // For each row
            for (int j = 0; j < boardsize; j++) { // For each column
                checkboard[i + 1][j + 1] = board[i][j]; // Copy board into checkboard
            }
        }

        /*
         * Direction indices:
         * Up Left: 1
         * Left: 2
         * Down Left: 3
         * Up: 4
         * Down: 5
         * Up Right: 6
         * Right: 7
         * Down Right: 8
         */

        for (int i = 1; i <= boardsize; i++) { // For each row
            for (int j = 1; j <= boardsize; j++) { // For each column
                if (checkboard[i][j] == 0) { // If position is empty
                    HashMap<Integer, HashMap<Integer, Integer[]>> directionFlips = new HashMap<>(); // Create Hashmap
                                                                                                    // for directions
                    if (checkboard[i - 1][j - 1] == opponent) { // If upper left position is opponent's
                        direction = 1; // Set direction
                        ownPiece = findOwn(checkboard, i, j, direction, colour); // Iterate and search in diagonal
                                                                                 // direction for incapsulation
                        if (ownPiece[0] < boardsize + 2) { // If incapsulation is possible
                            flips = saveFlips(ownPiece, i, j, direction); // Save incapsulated opponen pieces to hashmap
                            directionFlips.put(direction, flips); // Add direction's flips to directionFlips
                        }
                    }
                    if (checkboard[i][j - 1] == opponent) { // If left position is opponent's
                        direction = 2; // Set direction
                        ownPiece = findOwn(checkboard, i, j, direction, colour); // Iterate and search in horisontal
                                                                                 // direction for incapsulation
                        if (ownPiece[0] < boardsize + 2) { // If incapsulation is possible
                            flips = saveFlips(ownPiece, i, j, direction); // Save incapsulated opponen pieces to hashmap
                            directionFlips.put(direction, flips); // Add direction's flips to directionFlips
                        }
                    }
                    if (checkboard[i + 1][j - 1] == opponent) { // If lower left position is opponent's
                        direction = 3; // Set direction
                        ownPiece = findOwn(checkboard, i, j, direction, colour); // Iterate and search in diagonal
                                                                                 // direction for incapsulation
                        if (ownPiece[0] < boardsize + 2) { // If incapsulation is possible
                            flips = saveFlips(ownPiece, i, j, direction); // Save incapsulated opponen pieces to hashmap
                            directionFlips.put(direction, flips); // Add direction's flips to directionFlips
                        }
                    }
                    if (checkboard[i - 1][j] == opponent) { // If upper position is opponent's
                        direction = 4; // Set direction
                        ownPiece = findOwn(checkboard, i, j, direction, colour); // Iterate and search in vertical
                                                                                 // direction for incapsulation
                        if (ownPiece[0] < boardsize + 2) { // If incapsulation is possible
                            flips = saveFlips(ownPiece, i, j, direction); // Save incapsulated opponen pieces to hashmap
                            directionFlips.put(direction, flips); // Add direction's flips to directionFlips
                        }
                    }
                    if (checkboard[i + 1][j] == opponent) { // If lower position is opponent's
                        direction = 5; // Set direction
                        ownPiece = findOwn(checkboard, i, j, direction, colour); // Iterate and search in vertical
                                                                                 // direction for incapsulation
                        if (ownPiece[0] < boardsize + 2) { // If incapsulation is possible
                            flips = saveFlips(ownPiece, i, j, direction); // Save incapsulated opponen pieces to hashmap
                            directionFlips.put(direction, flips); // Add direction's flips to directionFlips
                        }
                    }
                    if (checkboard[i - 1][j + 1] == opponent) { // If upper right position is opponent's
                        direction = 6; // Set direction
                        ownPiece = findOwn(checkboard, i, j, direction, colour); // Iterate and search in diagonal
                                                                                 // direction for incapsulation
                        if (ownPiece[0] < boardsize + 2) { // If incapsulation is possible
                            flips = saveFlips(ownPiece, i, j, direction); // Save incapsulated opponen pieces to hashmap
                            directionFlips.put(direction, flips); // Add direction's flips to directionFlips
                        }
                    }
                    if (checkboard[i][j + 1] == opponent) { // If right position is opponent's
                        direction = 7; // Set direction
                        ownPiece = findOwn(checkboard, i, j, direction, colour); // Iterate and search in horizontal
                                                                                 // direction for incapsulation
                        if (ownPiece[0] < boardsize + 2) { // If incapsulation is possible
                            flips = saveFlips(ownPiece, i, j, direction); // Save incapsulated opponen pieces to hashmap
                            directionFlips.put(direction, flips); // Add direction's flips to directionFlips
                        }
                    }
                    if (checkboard[i + 1][j + 1] == opponent) { // If lower right position is opponent's
                        direction = 8; // Set direction
                        ownPiece = findOwn(checkboard, i, j, direction, colour); // Iterate and search in diagonal
                                                                                 // direction for incapsulation
                        if (ownPiece[0] < boardsize + 2) { // If incapsulation is possible
                            flips = saveFlips(ownPiece, i, j, direction); // Save incapsulated opponen pieces to hashmap
                            directionFlips.put(direction, flips); // Add direction's flips to directionFlips
                        }
                    }
                    if (directionFlips.size() > 0) // If there is any incapsulation in any diection
                        validMoves.put("" + (i - 1) + "," + (j - 1), directionFlips); // Save flips with position key
                }
            }
        }
    }

    public int[] findOwn(int[][] checkboard, int i, int j, int direction, int colour) {
        int[] ownPiece = new int[] { boardsize + 2, boardsize + 2 }; // Arbitrary size
        if (direction == 1) { // If searching up and left
            i--; // Start with one step up and left
            j--;
            while (i > 0 && j > 0) { // While still on the board
                if (checkboard[i][j] == 0) { // If encountering empty position, break search
                    break;
                } else if (checkboard[i][j] == colour) { // If encountering own neighbouring piece
                    ownPiece[0] = i; // Save encapsulating piece
                    ownPiece[1] = j;
                    break;
                } else { // Or continue search
                    i--; // Up
                    j--; // Left
                }
            }
        } else if (direction == 2) { // If searching left
            j--; // Start with one step left
            while (j > 0) { // While still on the board
                if (checkboard[i][j] == 0) { // If encountering empty position, break search
                    break;
                } else if (checkboard[i][j] == colour) { // If encountering own neighbouring piece
                    ownPiece[0] = i; // Save encapsulating piece
                    ownPiece[1] = j;
                    break;
                } else { // Or continue search
                    j--; // Left
                }
            }
        } else if (direction == 3) { // If searching down and left
            i++; // Start with one step down and left
            j--;
            while (i < checkboard[0].length && j > 0) { // While still on the board
                if (checkboard[i][j] == 0) { // If encountering empty position, break search
                    break;
                } else if (checkboard[i][j] == colour) { // If encountering own neighbouring piece
                    ownPiece[0] = i; // Save encapsulating piece
                    ownPiece[1] = j;
                    break;
                } else { // Or continue search
                    i++; // Down
                    j--; // Left
                }
            }
        } else if (direction == 4) { // If searching up
            i--; // Start with one step up
            while (i > 0) { // While still on the board
                if (checkboard[i][j] == 0) { // If encountering empty position, break search
                    break;
                } else if (checkboard[i][j] == colour) { // If encountering own neighbouring piece
                    ownPiece[0] = i; // Save encapsulating piece
                    ownPiece[1] = j;
                    break;
                } else { // Or continue search
                    i--; // Up
                }
            }
        } else if (direction == 5) { // If searching down
            i++; // Start with one step down
            while (i < checkboard[0].length) { // While still on the board
                if (checkboard[i][j] == 0) { // If encountering empty position, break search
                    break;
                } else if (checkboard[i][j] == colour) { // If encountering own neighbouring piece
                    ownPiece[0] = i; // Save encapsulating piece
                    ownPiece[1] = j;
                    break;
                } else { // Or continue search
                    i++; // Down
                }
            }
        } else if (direction == 6) { // If searching up and right
            i--; // Start with one step up and right
            j++;
            while (i > 0 && j < checkboard[0].length) { // While still on the board
                if (checkboard[i][j] == 0) { // If encountering empty position, break search
                    break;
                } else if (checkboard[i][j] == colour) { // If encountering own neighbouring piece
                    ownPiece[0] = i; // Save encapsulating piece
                    ownPiece[1] = j;
                    break;
                } else { // Or continue search
                    i--; // Up
                    j++; // Right
                }
            }
        } else if (direction == 7) { // If searching right
            j++; // Start with one step right
            while (j < checkboard[0].length) { // While still on the board
                if (checkboard[i][j] == 0) { // If encountering empty position, break search
                    break;
                } else if (checkboard[i][j] == colour) { // If encountering own neighbouring piece
                    ownPiece[0] = i; // Save encapsulating piece
                    ownPiece[1] = j;
                    break;
                } else { // Or continue search
                    j++; // Right
                }
            }
        } else if (direction == 8) { // If searching down and right
            i++; // Start with one step down and right
            j++;
            while (i < checkboard[0].length && j < checkboard[0].length) { // While still on the board
                if (checkboard[i][j] == 0) { // If encountering empty position, break search
                    break;
                } else if (checkboard[i][j] == colour) { // If encountering own neighbouring piece
                    ownPiece[0] = i; // Save encapsulating piece
                    ownPiece[1] = j;
                    break;
                } else { // Or continue search
                    i++; // Down
                    j++; // Right
                }
            }
        }
        return ownPiece; // Return encapsulating piece position
    }

    public HashMap<Integer, Integer[]> saveFlips(int[] ownPiece, int i, int j, int direction) {
        HashMap<Integer, Integer[]> flips = new HashMap<>();
        Integer index = 0; // Hashmap index counter
        if (direction == 1) { // If flips are up and left
            i--; // Start with one step up and left
            j--;
            while (i > ownPiece[0]) { // While not at neighbour position
                flips.put(index, new Integer[] { i - 1, j - 1 }); // Save flip position
                index++; // Increment index
                i--; // Continue up
                j--; // and left
            }
        } else if (direction == 2) { // If flips are left
            j--; // Start with one step left
            while (j > ownPiece[1]) { // While not at neighbour position
                flips.put(index, new Integer[] { i - 1, j - 1 }); // Save flip position
                index++; // Increment index
                j--; // Continue left
            }
        } else if (direction == 3) { // If flips are down and left
            i++; // Start with one step down and left
            j--;
            while (j > ownPiece[1]) { // While not at neighbour position
                flips.put(index, new Integer[] { i - 1, j - 1 }); // Save flip position
                index++; // Increment index
                i++; // Continue down
                j--; // and left
            }
        } else if (direction == 4) { // If flips are up
            i--; // Start with one step up
            while (i > ownPiece[0]) { // While not at neighbour position
                flips.put(index, new Integer[] { i - 1, j - 1 }); // Save flip position
                index++; // Increment index
                i--; // Continue up
            }
        } else if (direction == 5) { // If flips are down
            i++; // Start with one step down
            while (i < ownPiece[0]) { // While not at neighbour position
                flips.put(index, new Integer[] { i - 1, j - 1 }); // Save flip position
                index++; // Increment index
                i++; // Continue down
            }
        } else if (direction == 6) { // If flips are up and right
            i--; // Start with one step up and right
            j++;
            while (i > ownPiece[0]) { // While not at neighbour position
                flips.put(index, new Integer[] { i - 1, j - 1 }); // Save flip position
                index++; // Increment index
                i--; // Continue up
                j++; // and right
            }
        } else if (direction == 7) { // If flips are right
            j++; // Start with one step right
            while (j < ownPiece[1]) { // While not at neighbour position
                flips.put(index, new Integer[] { i - 1, j - 1 }); // Save flip position
                index++; // Increment index
                j++; // Continue right
            }
        } else if (direction == 8) { // If flips are down and right
            i++; // Start with one step down and right
            j++;
            while (i < ownPiece[0]) { // While not at neighbour position
                flips.put(index, new Integer[] { i - 1, j - 1 }); // Save flip position
                index++; // Increment index
                i++; // Continue down
                j++; // and right
            }
        }
        return flips; // Return flip positions
    }
}