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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller {

	@FXML
	public Label label = new Label(" ");
	public TextField name1 = new TextField();
	public TextField name2 = new TextField();
	public Label score1 = new Label();
	public Label score2 = new Label();
	private boolean gameStarted = false;
	public GridPane gridPane = new GridPane();
	public Pane pane = new Pane();
	int playerID1 = 1;
	int playerID2 = 2;
	int startID = 1;
	int secondViolin = 2;
	boolean restart = false;
	public Button okBtn = new Button();
	public Pane topPane = new Pane();

	Board board = new Board();

	@FXML
	public void in() {
		label.setText(board.getPlayers().get(1) + " is White\n" + board.getPlayers().get(2) + " is Black");
		// Schedule an event after 3 seconds
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
			label.setText(board.getPlayers().get(startID) + " Starts first");
			gameStarted = true;
		}));
		timeline.play();
		int startsquare = (startID == 1) ? 3 : 4;
		for (int i = 3; i < 5; i++) {
			for (int j = 3; j < 5; j++) {
				String paneID = "#" + i + j;
				Pane pane = (Pane) gridPane.lookup(paneID);
				if (board.getBoard()[i][j] == 0)
					drawCircle(startsquare, pane);
			}
		}
	}

	@FXML
	public void restart(ActionEvent event) throws IOException {
		String player1 = board.getPlayers().get(1);
		String player2 = board.getPlayers().get(2);
		reset();
		playerID1 = 1;
		playerID2 = 2;
		board.setPlayers(playerID1, player1, playerID2, player2);
		gameStarted = false;
		startID = (startID == 1) ? 2 : 1;
		secondViolin = (secondViolin == 2) ? 1 : 2;

		okBtn.setVisible(true);
		name1.setVisible(true);
		name2.setVisible(true);
		setName();
	}

	@FXML
	public void onPaneClicked(MouseEvent event) throws IOException {
		int row = getRowIndex(event);
		int column = getColumnIndex(event);
		Pane pane = (Pane) event.getSource();
		int id;
		String ownString;
		String opponentString;
		if (gameStarted) {
			if (board.getTurn() < 2) {
				firstFour(row, column, pane);
			} else {
				hideLegalMoves();
				id = (board.getTurn() % 2 == 1) ? startID : secondViolin;
				ownString = (id == 1) ? name1.getText() : name2.getText();
				opponentString = (id == 1) ? name2.getText() : name1.getText();
				if (board.turnState(id) == 22) {
					label.setText("No legal moves. \n" + opponentString + "'s turn");
				} else {
					switch (board.place(row, column, id)) {
						case 11:
							update();
							checkScore();
							label.setText("Good job!\n" + opponentString + "'s turn");
							showLegalMoves((id == 1) ? 2 : 1);
							break;
						case 12:
							label.setText("Field already occupied\n" + ownString + "'s turn");
							showLegalMoves(id);
							break;
						case 13:
							label.setText("Illegal move\n" + ownString + "'s turn");
							showLegalMoves(id);
							break;
					}
				}
				if (board.gameOver()) {
					String outcome = "";
					int score;
					String winner;
					int hs = (int) loadHighScore()[0];
					switch (board.checkWinner()) {
						case 41:
							winner = board.getPlayers().get(1);
							score = board.checkWhiteScore();
							if(hs < score) {
							saveHighScore(score, winner);
							outcome = winner + " wins & sets\n the HighScore! ";
							break;
					}else {
						outcome = winner + " wins!";
						break;}
						case 42:
							winner = board.getPlayers().get(2);
							score = board.checkBlackScore();
							if(hs < score) {
								saveHighScore(score, winner);
								outcome = winner + " wins & sets\n the HighScore! ";
								break;
						}else {
							outcome = winner + " wins!";
							break;}
						case 43:
							outcome = "It's a draw!";
							break;
					}
					label.setText("Game is over!\n" + outcome);
				}
			}
		}
	}

	public void update() {
		int[][] arr = board.getBoard();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				String paneID = "#" + i + j;
				Pane pane = (Pane) gridPane.lookup(paneID);
				drawCircle(arr[i][j], pane);
			}
		}
	}

	public int getRowIndex(MouseEvent event) {
		Pane pane = (Pane) event.getSource();
		return GridPane.getRowIndex(pane);
	}

	public int getColumnIndex(MouseEvent event) {
		Pane pane = (Pane) event.getSource();
		return GridPane.getColumnIndex(pane);
	}

	public void drawCircle(int id, Pane pane) {
		Color stroke;
		Color fill;
		Circle c = new Circle();
		if (id == 1) {
			stroke = Color.rgb(153, 153, 153);
			fill = Color.rgb(204, 204, 204);
			c = new Circle(23, fill);
			c.setStroke(stroke);
		} else if (id == 2) {
			stroke = Color.rgb(179, 179, 179);
			fill = Color.rgb(0, 0, 0);
			c = new Circle(23, fill);
			c.setStroke(stroke);
		} else if (id == 3) {
			stroke = Color.rgb(153, 153, 153);
			fill = Color.rgb(204, 204, 204, 0.5);
			c = new Circle(23, fill);
			c.setStroke(stroke);
		} else if (id == 4) {
			stroke = Color.rgb(179, 179, 179);
			fill = Color.rgb(0, 0, 0, 0.5);
			c = new Circle(23, fill);
			c.setStroke(stroke);
		}
		c.setStrokeWidth(3);
		c.setCenterX(pane.getWidth() / 2);
		c.setCenterY(pane.getHeight() / 2);
		pane.getChildren().add(c);
	}

	public void firstFour(int row, int column, Pane pane) {
		String playerTurn = (startID == 1) ? board.getPlayers().get(1) : board.getPlayers().get(2);
		switch (board.initPlace(row, column, startID)) {
			case 11:
				update();
				if (board.getTurn() == 1)
					label.setText(playerTurn + ": Place second tile");
				showLegalMoves(startID);
				if (board.getTurn() > 1)
					label.setText(playerTurn + ": Your move!");
				checkScore();
				break;
			case 12:
				label.setText("Cannot place here");
				break;
			case 13:
				label.setText("Illegal Placement, try again");
				break;
		}
	}

	public void reset() {
		board.resetBoard();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				String paneID = "#" + i + j;
				Pane pane = (Pane) gridPane.lookup(paneID);
				pane.getChildren().clear();
			}
		}
	}

	@FXML
	public void surrender(ActionEvent event) {
		int color = startID;
		String ownString;
		String opponentString;
		color = (board.getTurn() % 2 == 1) ? startID : secondViolin;
		ownString = (color == 1) ? board.getPlayers().get(1) : board.getPlayers().get(2);
		opponentString = (color == 1) ? board.getPlayers().get(2) : board.getPlayers().get(1);
		label.setText(ownString + " has surrendered! \n" + opponentString + " wins!");
		gameStarted = false;
	}

	@FXML
	public void checkScore() {
		score1.setText(board.getPlayers().get(1) + " = " + board.checkWhiteScore());
		score2.setText(board.getPlayers().get(2) + " = " + board.checkBlackScore());
	}

	@FXML
	public void setName() {
		label.setText("Please enter your names\n in the textfields above:");
		checkScore();
	}

	@FXML
	public void setNameBtn(ActionEvent event) {
		if (name1.getText().isEmpty()) {
			name1.setText("Player 1");
		}
		if (name2.getText().isEmpty()) {
			name2.setText("Player 2");
		}
		
		if (name1.getText().length()>10) {
			label.setText("Player1\n Please enter a shorter name:");
		} else if (name2.getText().length()>10) {
			label.setText("Player2\n Please enter a shorter name:");
		} else {
		board.setPlayerName(playerID1, name1.getText());
		board.setPlayerName(playerID2, name2.getText());
		checkScore();
		okBtn.setVisible(false);
		name1.setVisible(false);
		name2.setVisible(false);
		in();
		}
	}

	public void showLegalMoves(int colour) {
		board.moveAnalyser(colour);
		int circleColour = (colour == 1) ? 3 : 4;
		Set<String> validMovesSet = board.getValidMoves().keySet();
		String[] validMoves = validMovesSet.toArray(new String[validMovesSet.size()]);
		for (int i = 0; i < validMoves.length; i++) {
			String paneID = "#" + validMoves[i].replace(",", "");
			Pane pane = (Pane) gridPane.lookup(paneID);
			drawCircle(circleColour, pane);
		}
	}

	public void hideLegalMoves() {
		Set<String> validMovesSet = board.getValidMoves().keySet();
		String[] validMoves = validMovesSet.toArray(new String[validMovesSet.size()]);
		for (int i = 0; i < validMoves.length; i++) {
			String paneID = "#" + validMoves[i].replace(",", "");
			Pane pane = (Pane) gridPane.lookup(paneID);
			pane.getChildren().clear();
		}
	}

	public static Object[] loadHighScore() {
		try {
			FileReader hsfr = new FileReader("HighScore.txt");
			BufferedReader hsbr = new BufferedReader(hsfr);
			String l = hsbr.readLine();
			String[] p = l.split(",");
			String name = p[1];
			int score = Integer.parseInt(p[0]);
			hsbr.close();
			hsfr.close();
			return new Object[] { score, name };
		} catch (Exception numberForException) {
			return new Object[] { -1, "" };
		}
	}

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

	public void mainMenu(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Main Menu");
		alert.setContentText("Are you sure you want to go back to main menu?");
		alert.setHeaderText(" By doing this, you cannot continue the game again!");
		ImageView graphic = new ImageView(new Image(getClass().getResourceAsStream("icon32.png")));
		alert.setGraphic(graphic);

		if (alert.showAndWait().get() == ButtonType.OK) {
			FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
			Parent menuRoot = menuLoader.load();
			Scene menuScene = new Scene(menuRoot);
			Node node = (Node) event.getSource();
			Stage primaryStage = (Stage) node.getScene().getWindow();
			primaryStage.setScene(menuScene);
		}
	}
}
