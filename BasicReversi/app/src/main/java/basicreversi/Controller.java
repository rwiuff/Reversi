package basicreversi;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controller {

	@FXML
	public Label label = new Label(" ");

	private boolean gameStarted = false;
	public GridPane gridPane = new GridPane();
	public Pane pane = new Pane();
	int color = 1;

	int player1counter = 0;
	int player2counter = 0;

	Board b = new Board();

	@FXML
	public void in() {
		label.setText(b.getPlayers().get("White") + " is White and " + b.getPlayers().get("Black") + " is Black");
		// Schedule an event after 2 seconds
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
			label.setText(b.getPlayers().get("White") + " Starts first");
			gameStarted = true;
		}));
		timeline.play();
	}

	@FXML
	public void Restart(ActionEvent e) throws IOException {
		String White = b.getPlayers().get("White");
		String Black = b.getPlayers().get("Black");
		reset();
		color = (color == 1) ? 2 : 1;
		b.setPlayers(1, White, 2, Black);
		b.getPlayers();
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
								label.setText("Good job!\n" + opponentString + "'s turn");
								break;
							case 12:
								label.setText("Field already occupied\n" + ownString + "'s turn");
								break;
							case 13:
								label.setText("Illegal move\n" + ownString + "'s turn");
								break;
						}
				}
				if (b.gameOver()) {
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

	public void reset() {
		b.resetBoard();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				String paneID = "#" + i + j;
				Pane pane = (Pane) gridPane.lookup(paneID);
				pane.getChildren().clear();
			}
		}
	}
}
