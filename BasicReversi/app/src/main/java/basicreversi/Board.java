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
    HashMap<String, HashMap<String, int[]>> validMoves = new HashMap<>(); //

    public Board() { // Constructor for board
        board = new int[boardsize][boardsize]; // Initialises with 0 values
        setPlayers();
    }

    public void setPlayers() {
        Random rand = new Random();
        if (rand.nextInt(0, 2) == 0) {
            players.put("White", "Player 1");
            players.put("Black", "Player 2");
        } else {
            players.put("White", "Player 2");
            players.put("Black", "Player 1");
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

    public HashMap<String, HashMap<String, int[]>> getValidMoves() {
        return validMoves;
    }

    public int place(int row, int column, int colour) {
        // Check if the position is already occupied
        if (board[row][column] != 0) {
            return 12;
            // } else if (legality(row, column, colour) == 13) {
            // return 13;
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
        for (int i = 0; i < boardsize; i++) {
            for (int j = 0; j < boardsize; j++) {
                if (board[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
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

    public int flip(int row, int column, char coulour) {

        return 31;
    }

    public int legality(int colour) {

        int opponent;
        int[][] checkboard = new int[boardsize + 2][boardsize + 2];
        int[] saveflip;

        opponent = (colour == 1) ? 2 : 1;
        System.out.println(opponent);
        for (int i = 0; i < boardsize; i++) {
            for (int j = 0; j < boardsize; j++) {
                checkboard[i + 1][j + 1] = board[i][j];
            }
        }
        for (int i = 1; i <= boardsize; i++) {
            for (int j = 1; j <= boardsize; j++) {
                if (checkboard[i][j] == 0) {
                    saveflip = new int[] { boardsize + 2, boardsize + 2 };
                    if (checkboard[i - 1][j - 1] == opponent) {
                        int k = i - 1;
                        int l = j - 1;
                        while (k > 0 || l > 0) {
                            if (checkboard[k][l] == colour) {
                                saveflip[0] = k;
                                saveflip[1] = l;
                                break;
                            } else {
                                k--;
                                l--;
                            }
                        }
                        k = i - 1;
                        l = j - 1;
                        if (saveflip[0] < boardsize + 2) {
                            HashMap<String, int[]> flips = new HashMap<>();
                            int count = 0;
                            while (k > saveflip[0]) {
                                flips.put("" + count, new int[] { k, l });
                                count++;
                                k--;
                                l--;
                            }
                            // HashMap<String, int[]> position = new HashMap<>();
                            // position.put("Position", new int[] { i, j });
                            // HashMap<String, HashMap<String, int[]>> container = new HashMap<>();
                            // container.put("Position", position);
                            // container.put("Flips", flips);
                            validMoves.put("" + i + "," + j, flips);
                        }
                    }
                    if (checkboard[i][j - 1] == opponent) {
                        int k = i;
                        int l = j - 1;
                        while (l > 0) {
                            if (checkboard[k][l] == colour) {
                                saveflip[0] = k;
                                saveflip[1] = l;
                                break;
                            } else {
                                l--;
                            }
                        }
                        k = i;
                        l = j - 1;
                        if (saveflip[0] < boardsize + 2) {
                            HashMap<String, int[]> flips = new HashMap<>();
                            int count = 0;
                            while (l > saveflip[0]) {
                                flips.put("" + count, new int[] { k, l });
                                count++;
                                l--;
                            }
                            validMoves.put("" + i + "," + j, flips);
                        }
                    }
                    if (checkboard[i + 1][j - 1] == opponent) {
                        int k = i + 1;
                        int l = j - 1;
                        while (k <= boardsize || l > 0) {
                            if (checkboard[k][l] == colour) {
                                saveflip[0] = k;
                                saveflip[1] = l;
                                break;
                            } else {
                                k++;
                                l--;
                            }
                        }
                        k = i + 1;
                        l = j - 1;
                        if (saveflip[0] < boardsize + 2) {
                            HashMap<String, int[]> flips = new HashMap<>();
                            int count = 0;
                            while (j > saveflip[0]) {
                                flips.put("" + count, new int[] { k, l });
                                count++;
                                k++;
                                l--;
                            }
                            validMoves.put("" + i + "," + j, flips);
                        }
                    }
                    if (checkboard[i - 1][j] == opponent) {
                        int k = i - 1;
                        int l = j;
                        while (k > 0) {
                            if (checkboard[k][l] == colour) {
                                saveflip[0] = k;
                                saveflip[1] = l;
                                break;
                            } else {
                                k--;
                            }
                        }
                        k = i - 1;
                        l = j;
                        if (saveflip[0] < boardsize + 2) {
                            HashMap<String, int[]> flips = new HashMap<>();
                            int count = 0;
                            while (k > saveflip[0]) {
                                flips.put("" + count, new int[] { k, l });
                                count++;
                                k--;
                            }
                            validMoves.put("" + i + "," + j, flips);
                        }
                    }
                    if (checkboard[i + 1][j] == opponent) {
                        int k = i + 1;
                        int l = j;
                        while (k <= boardsize) {
                            if (checkboard[k][l] == colour) {
                                saveflip[0] = k;
                                saveflip[1] = l;
                                break;
                            } else {
                                k++;
                            }
                        }
                        k = i + 1;
                        l = j;
                        if (saveflip[0] < boardsize + 2) {
                            HashMap<String, int[]> flips = new HashMap<>();
                            int count = 0;
                            while (k > saveflip[0]) {
                                flips.put("" + count, new int[] { k, l });
                                count++;
                                k++;
                            }
                            validMoves.put("" + i + "," + j, flips);
                        }
                    }
                    if (checkboard[i - 1][j + 1] == opponent) {
                        int k = i - 1;
                        int l = j + 1;
                        while (k > 0 || l <= boardsize) {
                            if (checkboard[k][l] == colour) {
                                saveflip[0] = k;
                                saveflip[1] = l;
                                break;
                            } else {
                                k--;
                                l++;
                            }
                        }
                        k = i - 1;
                        l = j + 1;
                        if (saveflip[0] < boardsize + 2) {
                            HashMap<String, int[]> flips = new HashMap<>();
                            int count = 0;
                            while (k > saveflip[0]) {
                                flips.put("" + count, new int[] { k, l });
                                count++;
                                k--;
                                l++;
                            }
                            validMoves.put("" + i + "," + j, flips);
                        }
                    }
                    if (checkboard[i][j + 1] == opponent) {
                        int k = i;
                        int l = j + 1;
                        while (l <= boardsize) {
                            if (checkboard[k][l] == colour) {
                                saveflip[0] = k;
                                saveflip[1] = l;
                                break;
                            } else {
                                l++;
                            }
                        }
                        k = i;
                        l = j + 1;
                        if (saveflip[0] < boardsize + 2) {
                            HashMap<String, int[]> flips = new HashMap<>();
                            int count = 0;
                            while (k > saveflip[0]) {
                                flips.put("" + count, new int[] { k, l });
                                count++;
                                l++;
                            }
                            validMoves.put("" + i + "," + j, flips);
                        }
                    }
                    if (checkboard[i + 1][j + 1] == opponent) {
                        int k = i + 1;
                        int l = j + 1;
                        while (k <= boardsize || l <= boardsize) {
                            if (checkboard[k][l] == colour) {
                                saveflip[0] = k;
                                saveflip[1] = l;
                                break;
                            } else {
                                k++;
                                l++;
                            }
                        }
                        k = i + 1;
                        l = j + 1;
                        if (saveflip[0] < boardsize + 2) {
                            HashMap<String, int[]> flips = new HashMap<>();
                            int count = 0;
                            while (k > saveflip[0]) {
                                flips.put("" + count, new int[] { k, l });
                                count++;
                                k++;
                                l++;
                            }
                            validMoves.put("" + i + "," + j, flips);
                        }
                    }
                }
            }
        }
        return 0;
    }
}