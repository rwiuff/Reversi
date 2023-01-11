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
 *   21: Legal move on board        *
 *   22: Forfeit                    *
 *                                  *
 *   31: End of game                *
 *   32: Game continues             *
 *                                  *
 *   41: White wins                 *
 *   42: Black wins                 *
 *   43: Draw                       *
 *                                  *
 * -------------------------------- */
package basicreversi;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Board {
    private int[][] board; // Board field integer array
    private int turnCount = 0; // Counts the turn number
    private int boardsize = 8; // n in n x n dimensional board
    private int forfeitCounter = 0;
    private HashMap<String, String> players = new HashMap<String, String>(); // Player's colour assignment
    private HashMap<String, HashMap<Integer, HashMap<Integer, Integer[]>>> validMoves; //

    public Board() { // Constructor for board
        board = new int[boardsize][boardsize]; // Initialises with 0 values
        setPlayers();
    }

    public String getGreeting() {
        return "Kilroy was here";
    }

    public int[][] getBoard() {
        return board; // Returns board array
    }

    public void setPiece(int row, int column, int colour) {
        board[row][column] = colour;
    }

    public int getTurn() { // Return Turn number
        return turnCount;
    }

    public void turnClock() {
        turnCount++;
    }

    public HashMap<String, String> getPlayers() {
        return players;
    }

    public HashMap<String, HashMap<Integer, HashMap<Integer, Integer[]>>> getValidMoves() {
        return validMoves;
    }

    public void setPlayers() {
        Random rand = new Random();
        if (rand.nextInt(0, 2) == 0) {
            players.put("White", "Player1");
            players.put("Black", "Player2");
        } else {
            players.put("White", "Player2");
            players.put("Black", "Player1");
        }
    }

    public void setPlayers(int colour1, String player1, int colour2, String player2) {
        switch (colour1) {
            case 1:
                players.put("White", player1);
                players.put("Black", player2);
                break;
            case 2:
                players.put("White", player2);
                players.put("Black", player1);
                break;
            default:
                Random rand = new Random();
                if (rand.nextInt(0, 2) == 0) {
                    players.put("White", player1);
                    players.put("Black", player2);
                } else {
                    players.put("White", player2);
                    players.put("Black", player1);
                }
        }
    }

    public void resetBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
            }
        }
        turnCount = 0;
        forfeitCounter = 0;
    }

    public int turnState(int colour) {
        moveAnalyser(colour);
        if (validMoves.keySet().isEmpty()) {
            forfeitCounter++;
            turnClock();
            return 22;
        }
        return 21;
    }

    public int gameState() {
        if (forfeitCounter == 2) {
            return 31;
        } else if (filled()) {
            return 31;
        } else {
            return 32;
        }
    }

    public int initplace(int row, int column, int colour) {
        if (board[row][column] != 0)
            return 12;
        if (row > 2 && row < 5) {
            if (column > 2 && column < 5) {
                if (turnCount == 0) {
                    board[row][column] = colour;
                    turnClock();
                } else if (turnCount == 1) {
                    board[row][column] = colour;
                    for (int i = 3; i < 5; i++) {
                        for (int j = 3; j < 5; j++) {
                            int opponent = (colour == 1) ? 2 : 1;
                            if (board[i][j] == 0)
                                board[i][j] = opponent;
                            turnClock();
                        }
                    }
                }
            }
            return 11;
        } else {
            return 13;
        }
    }

    public int place(int row, int column, int colour) {
        // Check if the position is already occupied
        String move = "" + row + "," + column;
        forfeitCounter = 0;
        if (board[row][column] != 0) {
            return 12;
        } else if (validMoves.containsKey(move)) {
            board[row][column] = colour;
            turnClock();
            flip(move, colour);
            return 11; // false , You can't place a point here
        } else {
            return 13; // true, it's OK to place point.
        }

    }

    public void flip(String move, int colour) {
        HashMap<Integer, HashMap<Integer, Integer[]>> directionSet = validMoves.get(move);
        Set<Integer> directions = directionSet.keySet();
        for (Integer direction : directions) {
            HashMap<Integer, Integer[]> flipSet = directionSet.get(direction);
            Set<Integer> flips = flipSet.keySet();
            for (Integer flip : flips) {
                Integer[] k = flipSet.get(flip);
                board[k[0]][k[1]] = colour;
            }

        }
    }

    // Filled() iterates through the elements of the board array and
    // checks if any of them have the value 0.
    // If it finds an empty position, it returns false.
    // If it finishes iterating through the board and doesn't find any empty
    // positions, it returns true.

    public boolean filled() {
        for (int i = 0; i < boardsize; i++) {
            for (int j = 0; j < boardsize; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

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

    public void moveAnalyser(int colour) {
        validMoves = new HashMap<>(); //
        HashMap<Integer, Integer[]> flips;
        int direction;
        int[] ownTile;
        int[][] checkboard = new int[boardsize + 2][boardsize + 2];
        int opponent = (colour == 1) ? 2 : 1;

        for (int i = 0; i < boardsize; i++) {
            for (int j = 0; j < boardsize; j++) {
                checkboard[i + 1][j + 1] = board[i][j];
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

        for (int i = 1; i <= boardsize; i++) {
            for (int j = 1; j <= boardsize; j++) {
                if (checkboard[i][j] == 0) {
                    HashMap<Integer, HashMap<Integer, Integer[]>> directionFlips = new HashMap<>();
                    if (checkboard[i - 1][j - 1] == opponent) {
                        direction = 1;
                        ownTile = findOwn(checkboard, i, j, direction, colour);
                        if (ownTile[0] < boardsize + 2) {
                            flips = saveFlips(ownTile, i, j, direction);
                            directionFlips.put(direction, flips);
                        }
                    }
                    if (checkboard[i][j - 1] == opponent) {
                        direction = 2;
                        ownTile = findOwn(checkboard, i, j, direction, colour);
                        if (ownTile[0] < boardsize + 2) {
                            flips = saveFlips(ownTile, i, j, direction);
                            directionFlips.put(direction, flips);
                        }
                    }
                    if (checkboard[i + 1][j - 1] == opponent) {
                        direction = 3;
                        ownTile = findOwn(checkboard, i, j, direction, colour);
                        if (ownTile[0] < boardsize + 2) {
                            flips = saveFlips(ownTile, i, j, direction);
                            directionFlips.put(direction, flips);
                        }
                    }
                    if (checkboard[i - 1][j] == opponent) {
                        direction = 4;
                        ownTile = findOwn(checkboard, i, j, direction, colour);
                        if (ownTile[0] < boardsize + 2) {
                            flips = saveFlips(ownTile, i, j, direction);
                            directionFlips.put(direction, flips);
                        }
                    }
                    if (checkboard[i + 1][j] == opponent) {
                        direction = 5;
                        ownTile = findOwn(checkboard, i, j, direction, colour);
                        if (ownTile[0] < boardsize + 2) {
                            flips = saveFlips(ownTile, i, j, direction);
                            directionFlips.put(direction, flips);
                        }
                    }
                    if (checkboard[i - 1][j + 1] == opponent) {
                        direction = 6;
                        ownTile = findOwn(checkboard, i, j, direction, colour);
                        if (ownTile[0] < boardsize + 2) {
                            flips = saveFlips(ownTile, i, j, direction);
                            directionFlips.put(direction, flips);
                        }
                    }
                    if (checkboard[i][j + 1] == opponent) {
                        direction = 7;
                        ownTile = findOwn(checkboard, i, j, direction, colour);
                        if (ownTile[0] < boardsize + 2) {
                            flips = saveFlips(ownTile, i, j, direction);
                            directionFlips.put(direction, flips);
                        }
                    }
                    if (checkboard[i + 1][j + 1] == opponent) {
                        direction = 8;
                        ownTile = findOwn(checkboard, i, j, direction, colour);
                        if (ownTile[0] < boardsize + 2) {
                            flips = saveFlips(ownTile, i, j, direction);
                            directionFlips.put(direction, flips);
                        }
                    }
                    if (directionFlips.size() > 0)
                        validMoves.put("" + (i - 1) + "," + (j - 1), directionFlips);
                }
            }
        }
    }

    public int[] findOwn(int[][] checkboard, int i, int j, int direction, int colour) {
        int[] ownTile = new int[] { boardsize + 2, boardsize + 2 };
        if (direction == 1) {
            i--;
            j--;
            while (i > 0 && j > 0) {
                if (checkboard[i][j] == colour) {
                    ownTile[0] = i;
                    ownTile[1] = j;
                    break;
                } else {
                    i--;
                    j--;
                }
            }
        } else if (direction == 2) {
            j--;
            while (j > 0) {
                if (checkboard[i][j] == colour) {
                    ownTile[0] = i;
                    ownTile[1] = j;
                    break;
                } else {
                    j--;
                }
            }
        } else if (direction == 3) {
            i++;
            j--;
            while (i < checkboard[0].length && j > 0) {
                if (checkboard[i][j] == colour) {
                    ownTile[0] = i;
                    ownTile[1] = j;
                    break;
                } else {
                    i++;
                    j--;
                }
            }
        } else if (direction == 4) {
            i--;
            while (i > 0) {
                if (checkboard[i][j] == colour) {
                    ownTile[0] = i;
                    ownTile[1] = j;
                    break;
                } else {
                    i--;
                }
            }
        } else if (direction == 5) {
            i++;
            while (i < checkboard[0].length) {
                if (checkboard[i][j] == colour) {
                    ownTile[0] = i;
                    ownTile[1] = j;
                    break;
                } else {
                    i++;
                }
            }
        } else if (direction == 6) {
            i--;
            j++;
            while (i > 0 && j < checkboard[0].length) {
                if (checkboard[i][j] == colour) {
                    ownTile[0] = i;
                    ownTile[1] = j;
                    break;
                } else {
                    i--;
                    j++;
                }
            }
        } else if (direction == 7) {
            j++;
            while (j < checkboard[0].length) {
                if (checkboard[i][j] == colour) {
                    ownTile[0] = i;
                    ownTile[1] = j;
                    break;
                } else {
                    j++;
                }
            }
        } else if (direction == 8) {
            i++;
            j++;
            while (i < checkboard[0].length && j < checkboard[0].length) {
                if (checkboard[i][j] == colour) {
                    ownTile[0] = i;
                    ownTile[1] = j;
                    break;
                } else {
                    i++;
                    j++;
                }
            }
        }
        return ownTile;
    }

    public HashMap<Integer, Integer[]> saveFlips(int[] ownTile, int i, int j, int direction) {
        HashMap<Integer, Integer[]> flips = new HashMap<>();
        Integer count = 0;
        if (direction == 1) {
            i--;
            j--;
            while (i > ownTile[0]) {
                flips.put(count, new Integer[] { i - 1, j - 1 });
                count++;
                i--;
                j--;
            }
        } else if (direction == 2) {
            j--;
            while (j > ownTile[1]) {
                flips.put(count, new Integer[] { i - 1, j - 1 });
                count++;
                j--;
            }
        } else if (direction == 3) {
            i++;
            j--;
            while (j > ownTile[1]) {
                flips.put(count, new Integer[] { i - 1, j - 1 });
                count++;
                i++;
                j--;
            }
        } else if (direction == 4) {
            i--;
            while (i > ownTile[0]) {
                flips.put(count, new Integer[] { i - 1, j - 1 });
                count++;
                i--;
            }
        } else if (direction == 5) {
            i++;
            while (i < ownTile[0]) {
                flips.put(count, new Integer[] { i - 1, j - 1 });
                count++;
                i++;
            }
        } else if (direction == 6) {
            i--;
            j++;
            while (i > ownTile[0]) {
                flips.put(count, new Integer[] { i - 1, j - 1 });
                count++;
                i--;
                j++;
            }
        } else if (direction == 7) {
            j++;
            while (j < ownTile[1]) {
                flips.put(count, new Integer[] { i - 1, j - 1 });
                count++;
                j++;
            }
        } else if (direction == 8) {
            i++;
            j++;
            while (i < ownTile[0]) {
                flips.put(count, new Integer[] { i - 1, j - 1 });
                count++;
                i++;
                j++;
            }
        }
        return flips;
    }

}