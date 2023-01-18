package advancedreversi;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controller {

	@FXML
	private BorderPane ReversiTimerpane;	
	@FXML
	public Label label = new Label(" ");
    
	private boolean gameStarted = false;
	public GridPane gridPane = new GridPane();
	public Pane pane = new Pane();
	int playerID1 = 1;
	int playerID2 = 2;
	int startID = 1;
	int secondViolin = 2;
	boolean restart = false;
	public boolean speedReversi = false;

	Board b = new Board();

	@FXML
	public void Restart(ActionEvent e) throws IOException {
		String player1 = b.getPlayers().get(1);
		String player2 = b.getPlayers().get(2);
		
		if (speedReversi == true) {
		time1.clear();
		time2.clear();
		}
		
		reset();
		playerID1 = 1;
		playerID2 = 2;
		b.setPlayers(playerID1, player1, playerID2, player2);
		gameStarted = false;
		startID = (startID == 1) ? 2 : 1;
		secondViolin = (secondViolin == 2) ? 1 : 2;
		in();	
	}
	
	int currentplayer;
	@FXML
	public void in() {
	label.setText(b.getPlayers().get(1) + " is White\n" + b.getPlayers().get(2) + " is Black");
	
	ReversiTimerpane.setTop(time1);
	ReversiTimerpane.setBottom(time2);
	time1.start();time1.pause();
	time2.start();time2.pause();
	

	if (b.getPlayers().get(startID).equals("Player 1")) currentplayer = 1;
	else currentplayer = 2;
	
	// Schedule an event after 2 seconds
	Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
	label.setText(b.getPlayers().get(startID) + " Starts first");
	gameStarted = true;
	}));
	
	timeline.play(); 
	
	}

	ReversiTimer time1 = new ReversiTimer(1,this);
	ReversiTimer time2 = new ReversiTimer(2,this);

	@FXML
	public void onPaneClicked(MouseEvent event) {
		
		int row = getRowIndex(event);
		int column = getColumnIndex(event);
		Pane pane = (Pane) event.getSource();
		int color = startID;
		String ownString;
		String opponentString;
		
		if (gameStarted && !time1.timeout() && !time2.timeout()) { 
			System.out.println("(" + row + "," + column + ")");
			if (b.getTurn() < 2) {
				firstFour(row, column, color, pane);
			} else {
				
				
				if (currentplayer == 2) {
					
					time2.pause();
					time1.resume();

				}   else {
					
					time1.pause();
					time2.resume();
					
				}
				
				color = (b.getTurn() % 2 == 1) ? startID : secondViolin;
				ownString = (color == 1) ? b.getPlayers().get(1) : b.getPlayers().get(2);
				opponentString = (color == 1) ? b.getPlayers().get(2) : b.getPlayers().get(1);
	
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
							outcome = b.getPlayers().get(1) + " wins!";
							break;
						case 42:
							outcome = b.getPlayers().get(2) + " wins!";
							break;
						case 43:
							outcome = "It's a draw!";
							break;
					}
					label.setText("Game is over!\n" + outcome);
				}
				
			}
		}else {
			if (time1.timeout()) {
				
				time1.pause();	
				time2.pause();
				
				String outcome = "Player 2  wins!";
				label.setText("Game is over!\n" + outcome);
				gameStarted = false;
				
			}else if (time2.timeout()) {
				
				time1.pause();	
				time2.pause();
				
				String outcome = "Player 1  wins!";
				label.setText("Game is over!\n" + outcome);
				gameStarted = false;
			}
		}
		
		if (currentplayer == 2) currentplayer = 1;
		else currentplayer = 2;
		
	}

	public int getCurrentplayer() {
		return currentplayer;
	}


	public void setCurrentplayer(int currentplayer) {
		this.currentplayer = currentplayer;
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
		Color stroke;
		Color fill;
		Circle c = new Circle();
		if (color == 1) {
			stroke = Color.rgb(153, 153, 153);
			fill = Color.rgb(204, 204, 204);
			c = new Circle(23, fill);
			c.setStroke(stroke);
		} else if (color == 2) {
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

	public void firstFour(int row, int column, int startID, Pane pane) {
		String playerTurn = (startID == 1) ? b.getPlayers().get(1) : b.getPlayers().get(2);
		switch (b.initplace(row, column, startID)) {
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
