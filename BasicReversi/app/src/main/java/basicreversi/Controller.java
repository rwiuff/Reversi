package basicreversi;

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Controller {

	@FXML
	public Label label = new Label(" ");
	private Stage stage;
	private Scene scene;
	private Parent root;

	private boolean gameStarted = false;
	public GridPane gridPane = new GridPane();
	private String Player2;
	public Pane pane = new Pane();
	private boolean player1 = true;
	int color = 1;

	int player1counter = 0;
	int player2counter = 0;

	Board b = new Board();

	@FXML
	public void in() {
		if (b.getPlayers().get("White") == Player2) {
			label.setText("Player 2 is White and Player 1 is Black");
			// Schedule an event after 2 seconds
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
				label.setText("Player 2 Starts first");
				gameStarted = true;
			}));
			timeline.play();
		} else {
			label.setText("Player 1 is White and Player 2 is Black");
			// Schedule an event after 2 seconds
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
				label.setText("Player 1 starts!");
				gameStarted = true;
			}));
			timeline.play();
		}
	}

	@FXML
	public void Restart(ActionEvent e) throws IOException {
		String oldWhite = b.getPlayers().get("White");
		String oldBlack = b.getPlayers().get("Black");
		b.resetBoard();
		update();
		color = (color == 1) ? 2 : 1;
		String newWhite = oldBlack;
		String newBlack = oldWhite;
		b.setPlayers(1, newWhite, 2, newBlack);
		gameStarted = false;
		in();
	}

	@FXML
	public void onPaneClicked(MouseEvent event) {
		int row = getRowIndex(event);
		int column = getColumnIndex(event);
		Pane pane = (Pane) event.getSource();
		if (gameStarted) {
			System.out.println("(" + row + "," + column + ")");
			if (b.getTurn() < 2) {
				firstFour(row, column, color, pane);
			} else {
				color = (b.getTurn() % 2 == 0) ? 2 : 1;
				String ownString = (color == 1) ? b.getPlayers().get("White") : b.getPlayers().get("Black");
				String opponentString = (color == 1) ? b.getPlayers().get("Black") : b.getPlayers().get("White");
				switch (b.turnState(color)) {
					case 22:
						label.setText("No legal moves. " + opponentString + "'s turn");
						break;
					case 21:
						label.setText(ownString + "s turn");
						switch (b.place(row, column, color)) {
							case 11:
								update();
								label.setText("Good job!");
								break;
							case 12:
								label.setText("Field already occupied");
								break;
							case 13:
								label.setText("Illegal move");
								break;
						}
				}
				switch (b.gameState()) {
					case 31:
						String outcome = "";
						switch (b.checkWinner()) {
							case 41:
								outcome = b.getPlayers().get("White") + " wins!";
								break;
							case 42:
								outcome = b.getPlayers().get("Black") + " wins!";
								break;
							case 43:
								outcome = "It's a draw!";
								break;
						}
						label.setText("Game is over!\n" + outcome);
						break;
					case 32:
						label.setText(opponentString + "'s turn");
				}
			}
		}
	}

	public void update() {
		int[][] board = b.getBoard();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				String paneID = "#" + i + j;
				Pane pane = (Pane) gridPane.lookup(paneID);
				DrawCircle(board[i][j], pane);
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

	public void DrawCircle(int color, Pane pane) {
		if (color == 1) {
			Color stroke = Color.rgb(153, 153, 153);
			Circle c = new Circle(30, Color.rgb(204, 204, 204));
			c.setStroke(stroke);
			c.setStrokeWidth(3);
			c.setCenterX(pane.getWidth() / 2);
			c.setCenterY(pane.getHeight() / 2);
			pane.getChildren().add(c);
		} else if (color == 2) {
			Color stroke = Color.rgb(179, 179, 179);
			Circle c = new Circle(30, Color.BLACK);
			c.setStroke(stroke);
			c.setStrokeWidth(3);
			c.setCenterX(pane.getWidth() / 2);
			c.setCenterY(pane.getHeight() / 2);
			pane.getChildren().add(c);
		}
	}

	public void firstFour(int row, int column, int color, Pane pane) {
		String playerTurn = b.getPlayers().get("White");
		switch (b.initplace(row, column, color)) {
			case 11:
				update();
				if (b.getTurn() == 1)
					label.setText(playerTurn + ": Place second tile");
				if (b.getTurn() > 1)
					label.setText(playerTurn + ": Your move!");
				break;
			case 12:
				label.setText("Cannot place here");
				break;
			case 13:
				label.setText("Illegal Placement, try again");
				break;
		}
	}
}
