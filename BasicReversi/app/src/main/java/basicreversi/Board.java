package basicreversi;

public class Board {
    int[][] board;

    public Board(){
        board = new int[8][8];
    }

    public int[][] getBoard() {
        return board;
    }

    public String flip(int i, int j, int colour){
        String message = "Godt arbejde!";
        if(board[i][j] == 0) message = "Du skal vende brikker";
        if(board[i][j] == colour) message = "Du skal vende modstanderens brikker";
        else if(board[i][j] != colour) board[i][j] = colour;
        return message;
    }

}
