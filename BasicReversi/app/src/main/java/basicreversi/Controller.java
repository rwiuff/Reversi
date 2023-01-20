package basicreversi;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Controller {

	@FXML
	public Label label = new Label(" "); // Label object for user hints
	private boolean gameStarted = false; // Boolean activates gameplay
	public GridPane gridPane = new GridPane(); // Grid of panes -> gameboard
	int playerID1 = 1; // Player 1 internal ID
	int playerID2 = 2; // Player 2 intrnal ID
	int startID = 1; // Beginning player
	int secondViolin = 2; // Seconnd player

	Board board = new Board(); // Constructs Board object

	// Initiates the game, assigns the players their colours.
	@FXML
	public void in() {
		label.setText(board.getPlayers().get(1) + " is White\n" + board.getPlayers().get(2) + " is Black");
		// Schedule an event after 2 seconds
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
			label.setText(board.getPlayers().get(startID) + " Starts first");
			gameStarted = true; // Allows clicking on gameboard
		}));
		timeline.play();
	}

	// Restarts the game, so that the board is cleaned,
	// and the player may give themselves new names.
	// The first to make a move last round is now second.
	@FXML
	public void restart(ActionEvent e) throws IOException {
		String player1 = board.getPlayers().get(1); // Save player 1 name
		String player2 = board.getPlayers().get(2); // Save player 2 name
		reset(); // Clears gameboard and resets Board object
		board.setPlayers(playerID1, player1, playerID2, player2);
		gameStarted = false; // Restricts clicking on the gameboard
		startID = (startID == 1) ? 2 : 1; // Switches beginning player
		secondViolin = (secondViolin == 2) ? 1 : 2;
		in();
	}

	@FXML
	public void onPaneClicked(MouseEvent event) {
		int row = getRowIndex(event); // Row for which pane has been clicked
		int column = getColumnIndex(event); // Column for which pane has been clicked
		int id; // This turns player
		String ownString; // This turns player's name
		String opponentString; // This turns opponent's name
		if (gameStarted) { // If gameboard is clickable
			if (board.getTurn() < 2) { // Within the first two clicks
				firstFour(row, column); // Run initial placements
			} else {
				id = (board.getTurn() % 2 == 1) ? startID : secondViolin;// Assign player based on turn number
				ownString = (id == 1) ? board.getPlayers().get(1) : board.getPlayers().get(2); // Assigns players name
				opponentString = (id == 1) ? board.getPlayers().get(2) : board.getPlayers().get(1); // Assigns opponents name
				switch (board.turnState(id)) { // Valic move
					case 22: // Doesn't exist
						label.setText("No legal moves. " + opponentString + "'s turn");
						break;
					case 21: // Exists
						switch (board.place(row, column, id)) { // Place piece
							case 11: // Piece placed
								update(); // Update gameboard
								label.setText("Good job!\n" + opponentString + "'s turn"); // Encourage player
								break;
							case 12: // Field occupied
								label.setText("Field already occupied\n" + ownString + "'s turn");
								break;
							case 13: // Placement is illegal
								label.setText("Illegal move\n" + ownString + "'s turn");
								break;
						}
				}
				if (board.gameOver()) { // If two players has forfeited
					String outcome = ""; // Outcome string
					switch (board.checkWinner()) { // Check who won
						case 41: // White won
							outcome = board.getPlayers().get(1) + " wins!";
							break;
						case 42: // Black won
							outcome = board.getPlayers().get(2) + " wins!";
							break;
						case 43: // Draw
							outcome = "It's a draw!";
							break;
					}
					label.setText("Game is over!\n" + outcome);
					gameStarted = false;
				}
			}
		}
	}

	// Updates the board and draws circles on the panes according to Board object.
	public void update() {
		int[][] arr = board.getBoard(); // Get placement data
		for (int i = 0; i < 8; i++) { // For each row
			for (int j = 0; j < 8; j++) { // For each column
				String paneID = "#" + i + j; // Construct fx-id
				Pane pane = (Pane) gridPane.lookup(paneID); // Find pane based on fx-id
				drawCircle(arr[i][j], pane); // Draw piece
			}
		}
	}

	// Gets the row index of a pane on the gridpane with a mouseclick.
	public int getRowIndex(MouseEvent event) {
		Pane pane = (Pane) event.getSource();
		return GridPane.getRowIndex(pane);
	}

	// Gets the column index of a pane on the gridpane with a mouseclick.
	public int getColumnIndex(MouseEvent event) {
		Pane pane = (Pane) event.getSource();
		return GridPane.getColumnIndex(pane);
	}

	// Draws a circle and adds it to the center of a pane.
	// The circle's colour depends on the player-turn and on
	// whether it is a placed circle or a circle indicating a valid move.
	public void drawCircle(int id, Pane pane) {
		Color stroke;
		Color fill;
		Circle c = new Circle();
		if (id == 1) {
			// White circle
			stroke = Color.rgb(153, 153, 153);
			fill = Color.rgb(204, 204, 204);
			c = new Circle(23, fill);
			c.setStroke(stroke);
		} else if (id == 2) {
			// Black circle
			stroke = Color.rgb(179, 179, 179);
			fill = Color.rgb(0, 0, 0);
			c = new Circle(23, fill);
			c.setStroke(stroke);
		}
		c.setStrokeWidth(3);
		c.setCenterX(pane.getWidth() / 2);
		c.setCenterY(pane.getHeight() / 2);
		pane.getChildren().add(c);
	}

	public void firstFour(int row, int column) {
		String playerTurn = (startID == 1) ? board.getPlayers().get(1) : board.getPlayers().get(2); // Starting player
		switch (board.initPlace(row, column, startID)) { // Place first piece
			case 11: // Piece placed
				update(); // Update gameboard
				if (board.getTurn() == 1) // If first turn
					label.setText(playerTurn + ": Place second tile");
				if (board.getTurn() > 1) // If second turn
					label.setText(playerTurn + ": Your move!");
				break;
			case 12: // If position already occupied
				label.setText("Cannot place here");
				break;
			case 13: // If outside middle square
				label.setText("Illegal Placement, try again");
				break;
		}
	}

	public void reset() {
		board.resetBoard(); // Resets Board object
		for (int i = 0; i < 8; i++) { // For each row
			for (int j = 0; j < 8; j++) { // For each column
				String paneID = "#" + i + j; // Find fx-id
				Pane pane = (Pane) gridPane.lookup(paneID); // Find pane with fx-id
				pane.getChildren().clear(); // Clear pane
			}
		}
	}

}
