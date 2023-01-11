package basicreversi;

import java.io.IOException;
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
import javafx.util.Duration;

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

	int player1counter = 0;
	int player2counter = 0;

	Board b = new Board();

	@FXML
	public void in() {
		if (b.getPlayers().get("White") == Player2) {
			label.setText("Player2 is White and Player 1 is Black");
			// set white = 1 and black = 2
			int player2 = 1;
			int player1 = 2;
			// Schedule an event after 2 seconds
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
				label.setText("White Starts first");
				gameStarted = true;
			}));
			timeline.play();
		} else {
			label.setText("Player1 is White and Player2 is Black");
			// set white = 1 and black = 2
			int player1 = 1;
			int player2 = 2;
			// Schedule an event after 2 seconds
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
				label.setText("White Starts first");
				gameStarted = true;
			}));
			timeline.play();
		}
	}

	@FXML
	public void Restart(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("main.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		String oldWhite = b.getPlayers().get("White");
		String oldBlack = b.getPlayers().get("Black");
		b.resetBoard();
		String newWhite = oldBlack;
		String newBlack = oldWhite;
		b.setPlayers(1, newWhite, 2, newBlack);
		gameStarted = false;
	}

	@FXML
	public void onPaneClicked(MouseEvent event) {
		int color = 0;
		if (gameStarted) {
			System.out.println("(" + getRowIndex(event) + "," + getColumnIndex(event) + ")");
			First4(getRowIndex(event), getColumnIndex(event), 1, event);
			while (b.gameState() == 32) {
				switch (b.getTurn() % 2) {
					case 0:
						color = 1;
					case 1:
						color = 2;
				}
				switch (b.turnState(color)) {
					case 22:
						label.setText("Melder Pas");
					case 21:
						switch (b.place(getRowIndex(event), getColumnIndex(event), color)) {
							case 12:
								label.setText("Occupied");
							case 13:
								label.setText("Illegal name");
							case 11:
								label.setText("Good Job");
								DrawCircle(event, color);
						}
				}
			}
			Winner();
		}
	}

	public int getRowIndex(MouseEvent event) {
		Pane pane = (Pane) event.getSource();
		try {
			int row = GridPane.getRowIndex((Node) event.getSource());
		} catch (Exception e) {
			if (GridPane.getRowIndex(pane) == null) {
				GridPane.setRowIndex(pane, 0);
			}
		}
		return GridPane.getRowIndex(pane);
	}

	public int getColumnIndex(MouseEvent event) {
		Pane pane = (Pane) event.getSource();
		try {
			int column = GridPane.getColumnIndex((Node) event.getSource());
		} catch (Exception e) {
			if (GridPane.getColumnIndex(pane) == null)
				GridPane.setColumnIndex(pane, 0);
		}
		return GridPane.getColumnIndex(pane);
	}

	public void DrawCircle(MouseEvent event, int color) {

		Pane pane = (Pane) event.getSource();

		if (color == 1) {
			Color stroke = Color.rgb(153, 153, 153);
			Circle c = new Circle(30, Color.rgb(204, 204, 204));
			c.setStroke(stroke);
			c.setStrokeWidth(3);
			c.setCenterX(pane.getWidth() / 2);
			c.setCenterY(pane.getHeight() / 2);

			pane.getChildren().add(c);
		} else {
			Color stroke = Color.rgb(179, 179, 179);
			Circle c = new Circle(30, Color.BLACK);
			c.setStroke(stroke);
			c.setStrokeWidth(3);
			c.setCenterX(pane.getWidth() / 2);
			c.setCenterY(pane.getHeight() / 2);

			pane.getChildren().add(c);
		}
	}

	public void Winner() {
		if (b.getPlayers().get("White") == Player2) {
			if (b.checkWinner() == 41) {
				// white wins
				label.setText("Player1 Wins!");
			} else if (b.checkWinner() == 42) {
				// black wins
				label.setText("Player2 Wins!");
			} else {
				label.setText("It is a Draw!");
			}
		} else {
			if (b.checkWinner() == 41) {
				// white wins
				label.setText("Player2 Wins!");
			} else if (b.checkWinner() == 42) {
				// black wins
				label.setText("Player1 Wins!");
			} else {
				label.setText("It is a Draw!");
			}
		}
	}

	public void First4(int r, int c, int color, MouseEvent e) {
		if (color == 1) {
			while (b.getTurn() < 2) {
				int returnvalue = b.initplace(r, c, color);
				if (returnvalue == 11) {
					DrawCircle(e, color);
					label.setText("White Turn again");
				} else if (returnvalue == 12) {
					label.setText("Cannot place here");
					First4(r, c, color, e);
				} else {
					label.setText("Illegal Placement, try again");
					First4(r, c, color, e);
				}
			}
		} else {
			while (b.getTurn() < 2) {
				int returnvalue = b.initplace(r, c, color);
				if (returnvalue == 11) {
					DrawCircle(e, color);
					label.setText("Black Turn again");
				} else if (returnvalue == 12) {
					label.setText("Cannot place here");
					First4(r, c, color, e);
				} else {
					label.setText("Illegal Placement, try again");
					First4(r, c, color, e);
				}
			}
		}
	}
}
