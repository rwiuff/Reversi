package advancedreversi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller {

	@FXML
	public Label label = new Label(" "); // Label object for user hints
	public TextField name1 = new TextField(); // Textfield -> player 1 name
	public TextField name2 = new TextField(); // Textfield -> player 2 name
	public Label score1 = new Label(); // Label object for player 1 score
	public Label score2 = new Label(); // Label object for player 2 score
	private boolean gameStarted = false; // Boolean activates gameplay
	public GridPane gridPane = new GridPane(); // Grid of panes -> gameboard
	int playerID1 = 1; // Player 1 internal ID
	int playerID2 = 2; // Player 2 intrnal ID
	int startID = 1; // Beginning player
	int secondViolin = 2; // Seconnd player
	public Button continueBtn = new Button(); // Object for Continue button
	public Button okBtn = new Button(); // Object for OK button
	public boolean first4 = false; // Restricts surrendering before fifth piece
	public Button surrenderBtn = new Button(); // Object for Surrender button

	Board board = new Board(); // Constructs Board object

	// Initiates the game, assigns the players their colours.
	@FXML
	public void in() {
		label.setText(board.getPlayers().get(1) + " is White\n" + board.getPlayers().get(2) + " is Black");
		// Schedule an event after 3 seconds
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
			label.setText(board.getPlayers().get(startID) + " Starts first");
			gameStarted = true; // Allows clicking on gameboard
		}));
		timeline.play();
		int startsquare = (startID == 1) ? 3 : 4; // Variable for center square hints
		for (int i = 3; i < 5; i++) { // Center rows
			for (int j = 3; j < 5; j++) { // Center columns
				String paneID = "#" + i + j; // Plane IDs
				Pane pane = (Pane) gridPane.lookup(paneID); // Find pane object
				drawCircle(startsquare, pane); // Draw hinting circles
			}
		}
	}

	// Restarts the game, so that the board is cleaned,
	// and the player may give themselves new names.
	// The first to make a move last round is now second.
	@FXML
	public void restart(ActionEvent event) throws IOException {
		String player1 = board.getPlayers().get(1); // Save player 1 name
		String player2 = board.getPlayers().get(2); // Save player 2 name
		reset(); // Clears gameboard and resets Board object
		board.setPlayers(playerID1, player1, playerID2, player2);
		gameStarted = false; // Restricts clicking on the gameboard
		startID = (startID == 1) ? 2 : 1; // Switches beginning player
		secondViolin = (secondViolin == 2) ? 1 : 2;
		okBtn.setVisible(true); // Reappears OK button
		name1.setVisible(true); // Reappears player 1 name textfield
		name2.setVisible(true); // Reappears player 2 name textfield
		surrenderBtn.setVisible(true); // Reappears Surrender button
		setName(); // Runs hint: Enter playernames
	}

	// This method controls what happens, when a pane is clicked under different
	// circumstances. It behaves differently depending on the situation,
	// as in if the first four tiles have been placed or not, and will
	// finally get the result of the match and announce the winner
	// and saves the score, if it surpasses the previous high-score.

	@FXML
	public void onPaneClicked(MouseEvent event) throws IOException {
		int row = getRowIndex(event); // Row for which pane has been clicked
		int column = getColumnIndex(event); // Column for which pane has been clicked
		int id; // This turns player
		int opId; // This turns opponent
		String ownString; // This turns player's name
		String opponentString; // This turns opponent's name
		if (gameStarted) { // If gameboard is clickable
			if (board.getTurn() < 2) { // Within the first two clicks
				firstFour(row, column); // Run initial placements
			} else {
				first4 = true; // Enable surrendering
				id = (board.getTurn() % 2 == 1) ? startID : secondViolin; // Assign player based on turn number
				opId = (id == 1) ? 2 : 1; // Assigns opponent
				ownString = (id == 1) ? name1.getText() : name2.getText(); // Assigns players name
				opponentString = (id == 1) ? name2.getText() : name1.getText(); // Assigns opponents name
				if (board.turnState(id) == 21) { // If legal moves exists
					hideLegalMoves(); // Remove old move hints
					switch (board.place(row, column, id)) { // Place piece
						case 11: // If placement is legal
							update(); // Update gameboard
							checkScore(); // Update scoreboard
							label.setText("Good job!\n" + opponentString + "'s turn"); // Encourage player
							showLegalMoves(opId); // Place hints for opponents moves
							break; // Exit switch case
						case 12: // If placement is on top of another piece
							label.setText("Field already occupied\n" + ownString + "'s turn"); // Discourage player
							showLegalMoves(id); // Show hints for legal moves
							break; // Exit switch case
						case 13: // If placement is illegal
							label.setText("Illegal move\n" + ownString + "'s turn"); // Chastise player
							showLegalMoves(id); // Show hints for legal moves
							break; // Exit switch case
					}
				}
				if (board.turnState(opId) == 22) { // If no legal moves exists for opponent
					label.setText(opponentString + " Has no legal moves. \n" + ownString + "'s turn"); // Display
																										// forfeit
																										// message
					showLegalMoves(id); // Show legal moves
					if (board.turnState(id) == 22) { // If no legal moves exists for player
						label.setText("No legal moves. \n Game over! \n Press Continue"); // Declare end of game
						continueBtn.setVisible(true); // Display Continue button
						surrenderBtn.setVisible(false); // Hide Surrender button
					}
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
		} else if (id == 3) {
			// White valid move hint circle
			stroke = Color.rgb(153, 153, 153);
			fill = Color.rgb(204, 204, 204, 0.5);
			c = new Circle(23, fill);
			c.setStroke(stroke);
		} else if (id == 4) {
			// Black valid move hint circle
			stroke = Color.rgb(179, 179, 179);
			fill = Color.rgb(0, 0, 0, 0.5);
			c = new Circle(23, fill);
			c.setStroke(stroke);
		}
		c.setStrokeWidth(3);
		c.setCenterX(pane.getWidth() / 2);
		c.setCenterY(pane.getHeight() / 2);
		pane.getChildren().add(c); // Add circle to pane
	}

	// Commands the phase where players place the initial
	// four tiles in the center of the board.
	public void firstFour(int row, int column) {
		String ownString = (startID == 1) ? board.getPlayers().get(1) : board.getPlayers().get(2); // Starting player's
																									// name
		switch (board.initPlace(row, column, startID)) { // Place initial pieces
			case 11: // If successfull
				update(); // Update gameboard
				if (board.getTurn() == 1) // If first piece is placed
					label.setText(ownString + ": Place second piece"); // Instruct user
					showLegalMoves(startID); // Displays hints for next move
				if (board.getTurn() > 1) // If second piece is placed
					label.setText(ownString + ": Your move!"); // Instuct user
				checkScore(); // Update scoreboard
				break; // Exit switch case
			case 12: // If placement is on top of another piece
				label.setText("Cannot place here"); // Warn user
				break; // Exit switch case
			case 13: // If outside midle square
				label.setText("Illegal Placement, try again"); // Tell user to try again
				break; // Exit switch case
		}
	}

	// Clears the entire board of circles.
	public void reset() {
		board.resetBoard(); // Clear Board object
		for (int i = 0; i < 8; i++) { // For each row
			for (int j = 0; j < 8; j++) { // For each column
				String paneID = "#" + i + j; // Get fx-id
				Pane pane = (Pane) gridPane.lookup(paneID); // Find pane based on fx-id
				pane.getChildren().clear(); // Remove all nodes (circles)
			}
		}
	}

	// On pressing Continue button after game, declare winner and update highscore
	public void gameOver(ActionEvent event) {
		String outcome = ""; // Displayed message initialised
		int score; // Score variable
		String winner; // Winner's name
		int hs = (int) loadHighScore()[0]; // Load highscore
		switch (board.checkWinner()) { // Check winning colour
			case 41: // If white wins
				winner = board.getPlayers().get(1); // Winner's name
				score = board.checkWhiteScore(); // Get winner's score
				if (hs < score) { // If beating highscore
					saveHighScore(score, winner); // Save highscore
					outcome = winner + " wins & sets\n the HighScore! "; // Declare winner to users
					break;
				} else { // if lower than highscore
					outcome = winner + " wins!"; // Declare winner to users
					break;
				}
			case 42: // If black wins
				winner = board.getPlayers().get(2); // Winner's name
				score = board.checkBlackScore(); // Get winner's score
				if (hs < score) { // If beating highscore
					saveHighScore(score, winner); // Save highscore
					outcome = winner + " wins & sets\n the HighScore! "; // Declare winner to users
					break;
				} else { // if lower than highscore
					outcome = winner + " wins!"; // Declare winner to users
					break;
				}
			case 43: // If its a draw
				outcome = "It's a draw!"; // Shame participants
				break;
		}
		label.setText(outcome); // Display outcome
		continueBtn.setVisible(false); // Hide Continue button
	}

	// Allows players to surrender after the first four tiles have been placed.
	// the game will announce the surrender of the first player,
	// and the victory of the second player.
	@FXML
	public void surrender(ActionEvent event) {
		if (first4 == true) { // If surrendering is allowed
			int colour = startID;
			String ownString;
			String opponentString;
			colour = (board.getTurn() % 2 == 1) ? startID : secondViolin;
			ownString = (colour == 1) ? board.getPlayers().get(1) : board.getPlayers().get(2);
			opponentString = (colour == 1) ? board.getPlayers().get(2) : board.getPlayers().get(1);
			label.setText(ownString + " has surrendered! \n" + opponentString + " wins!"); // Tell users who won and who
																							// surrendered
			gameStarted = false; // Disable gameplay
		} else { // If center square is not full
			label.setText("That is just way too early!"); // Tell users its too early
		}
	}

	// Gets the current number of black tiles and white tiles from the board-class.
	@FXML
	public void checkScore() {
		score1.setText(board.getPlayers().get(1) + " = " + board.checkWhiteScore());
		score2.setText(board.getPlayers().get(2) + " = " + board.checkBlackScore());
	}

	// Prompts the players to enter their names.
	@FXML
	public void setName() {
		continueBtn.setVisible(false);
		label.setText("Please enter your names\n in the textfields above:");
		checkScore();
	}

	// Assigns the names written in the textfields to the players.
	// If no names are written, the names will as default be set
	// to "Player 1" and "Player 2". Names have a character
	// limit of 10.
	@FXML
	public void setNameBtn(ActionEvent event) {
		if (name1.getText().isEmpty()) { // If player 1 hasn't entered a name
			name1.setText("Player 1"); // Set name to Player 1
		}
		if (name2.getText().isEmpty()) { // If player 1 hasn't entered a name
			name2.setText("Player 2"); // Set name to Player 2
		}

		if (name1.getText().length() > 10) { // If playername is too long
			label.setText("Player1\n Please enter a shorter name:"); // Prompt for a shorter name
		} else if (name2.getText().length() > 10) {
			label.setText("Player2\n Please enter a shorter name:");
		} else {
			board.setPlayerName(playerID1, name1.getText()); // Set players names in Board object
			board.setPlayerName(playerID2, name2.getText());
			checkScore();
			okBtn.setVisible(false); // Disappear OK button
			name1.setVisible(false); // Disappear player 1 name textfield
			name2.setVisible(false); // Disappear player 2 name textfield
			in(); // Initialise gameplay
		}
	}

	// Draws circle on the panes where a player can place a circle.
	public void showLegalMoves(int colour) {
		board.moveAnalyser(colour); // Run moveAnalyser in Board object
		int circleColour = (colour == 1) ? 3 : 4; // Declare hint colour
		Set<String> validMovesSet = board.getValidMoves().keySet(); // Extract valid moves (stored as keys in a hashmap)
		String[] validMoves = validMovesSet.toArray(new String[validMovesSet.size()]); // Put keys in string array
		for (int i = 0; i < validMoves.length; i++) { // For every key
			String paneID = "#" + validMoves[i].replace(",", ""); // Remove comma from string: fx-id
			Pane pane = (Pane) gridPane.lookup(paneID); // Find pane based on fx-id
			drawCircle(circleColour, pane); // Draw hint circle
		}
	}

	// Clears the board of circles indicating possible moves
	public void hideLegalMoves() {
		Set<String> validMovesSet = board.getValidMoves().keySet(); // Extract valid moves (stored as keys in a hashmap)
		String[] validMoves = validMovesSet.toArray(new String[validMovesSet.size()]); // Put keys in string array
		for (int i = 0; i < validMoves.length; i++) { // For every key
			String paneID = "#" + validMoves[i].replace(",", ""); // Remove comma from string: fx-id
			Pane pane = (Pane) gridPane.lookup(paneID); // Find pane based on fx-id
			pane.getChildren().clear(); // Remove all nodes (circles)
		}
	}

	// Loads the highscore(score & name of the player who has set it) from
	// "HighScore.txt" file.
	// File returns an object array that includes name as string and score as
	// integer.
	public static Object[] loadHighScore() {
		try {
			FileReader hsfr = new FileReader("HighScore.txt"); // Load Highscore file
			BufferedReader hsbr = new BufferedReader(hsfr);
			String l = hsbr.readLine(); // Read highscore
			String[] p = l.split(","); // Convert highscore line to array
			String name = p[1]; // Highscore player to string
			int score = Integer.parseInt(p[0]); // Highscore to int
			hsbr.close(); // close BufferedReader
			hsfr.close(); // close FileReader
			return new Object[] { score, name }; // Return array with score as int and name as string
		} catch (Exception numberForException) { // If Highscore line is empty
			return new Object[] { -1, "" }; // Return score of -1
		}
	}

	// It stores the score and the name of the player separated by comma in the file
	// named "HighScore.txt".
	public void saveHighScore(int score, String name) {
		try {
			FileWriter hsfw = new FileWriter("HighScore.txt", false);
			BufferedWriter hsbw = new BufferedWriter(hsfw);
			hsbw.write(score + "," + name);
			hsbw.close();
			hsfw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void beginGame(ActionEvent event) throws IOException {
		FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("main.fxml")); // Load FXML
		Parent mainRoot = mainLoader.load(); // Put FXML as root
		Scene mainScene = new Scene(mainRoot); // Create scene from root
		Node node = (Node) event.getSource(); // Get node where button was pressede
		Stage primaryStage = (Stage) node.getScene().getWindow(); // Get stage in which node resides
		mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.F11) { // If F11 is pressed
					primaryStage.setFullScreen(!primaryStage.isFullScreen()); // Enter fullscreen
				}
			}
		});
		primaryStage.setScene(mainScene); // Set stage to newly created scene
		Controller controller = mainLoader.getController(); // (Re)load controller
		controller.setName(); // Run setName
	}

	// Shows user Highscore and name of the player who set it, upon presseing the
	// "HighScore" button
	public void showHighScore(ActionEvent event) {
		String highScoreText;
		Alert alert = new Alert(AlertType.INFORMATION); // Alert box
		alert.setTitle("Highscore");
		switch ((int) Controller.loadHighScore()[0]) { // Lad highscore from file
			case -1: // If -1 (No highscore)
				highScoreText = "No highscore set";
				break;
			default: // Load name and score and put in string
				highScoreText = "The HighScore is " + Controller.loadHighScore()[0] + " set by "
						+ Controller.loadHighScore()[1];
		}
		alert.setContentText(highScoreText); // Display string in alertbox
		alert.setHeaderText(null);
		ImageView graphic = new ImageView(new Image(getClass().getResourceAsStream("icon32.png"))); // Load icon
		alert.setGraphic(graphic); // Show icon in alertbox
		ButtonType response = alert.showAndWait().orElse(ButtonType.CANCEL); // Wait for user input
		if (response == ButtonType.OK) {
			alert.close(); // Close alertbox
		}
		alert.close();
	}

	// Calls the "exit"-method upon pressing the standard exit-button in the upper
	// corner.
	public void exitGame(ActionEvent event) {
		Node node = (Node) event.getSource(); // Get node for buttonpress
		Stage primaryStage = (Stage) node.getScene().getWindow(); // Get stage for buttonpress
		Main.exit(primaryStage); // Run exit method from Main class
	}

	// Prompts the user to confirm their decision in returning to the main menu upon
	// pressing the main-menu-button.
	// If the user presses "ok", the user returns to menu.
	public void mainMenu(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION); // Creates Alertbox
		alert.setTitle("Main Menu");
		alert.setContentText("Are you sure you want to go back to main menu?");
		alert.setHeaderText(" By doing this, you will lose all progress!");
		ImageView graphic = new ImageView(new Image(getClass().getResourceAsStream("icon32.png")));
		alert.setGraphic(graphic); // Loads and sets icon as graphic

		if (alert.showAndWait().get() == ButtonType.OK) { // If OK is pressed
			FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu.fxml")); // Load menu FXML
			Parent menuRoot = menuLoader.load(); // Set menu root from FXML file
			Scene menuScene = new Scene(menuRoot); // Create scene from rooot
			Node node = (Node) event.getSource(); // Get node from button press
			Stage primaryStage = (Stage) node.getScene().getWindow(); // Get stage from button press
			primaryStage.setScene(menuScene); // Change scene
		}
	}
}
