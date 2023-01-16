package advancedreversi;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Controller {

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
	private Stage stage;
	private Scene scene;
	private Parent root;
	private Button genstart;
	int highscore = 0;
	

	Board b = new Board();

	@FXML
	public void in() {
		label.setText(b.getPlayers().get(1) + " is White\n" + b.getPlayers().get(2) + " is Black");
		// Schedule an event after 2 seconds
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
			label.setText(b.getPlayers().get(startID) + " Starts first");
			gameStarted = true;
		}));
		timeline.play();
	}

	@FXML
	public void restart(ActionEvent e) throws IOException {
		String player1 = b.getPlayers().get(1);
		String player2 = b.getPlayers().get(2);
		reset();
		playerID1 = 1;
		playerID2 = 2;
		b.setPlayers(playerID1, player1, playerID2, player2);
		gameStarted = false;
		startID = (startID == 1) ? 2 : 1;
		secondViolin = (secondViolin == 2) ? 1 : 2;
		in();
	}

	@FXML
	public void onPaneClicked(MouseEvent event) {
		int row = getRowIndex(event);
		int column = getColumnIndex(event);
		Pane pane = (Pane) event.getSource();
		int id;
		String ownString;
		String opponentString;
		if (gameStarted) {
			System.out.println("(" + row + "," + column + ")");
			if (b.getTurn() < 2) {
				firstFour(row, column, pane);
			} else {
				hideLegalMoves();
				id = (b.getTurn() % 2 == 1) ? startID : secondViolin;
				ownString = (id == 1) ? b.getPlayers().get(1) : b.getPlayers().get(2);
				opponentString = (id == 1) ? b.getPlayers().get(2) : b.getPlayers().get(1);
				switch (b.turnState(id)) {
					case 22:
						label.setText("No legal moves. " + opponentString + "'s turn");
						break;
					case 21:
						switch (b.place(row, column, id)) {
							case 11:
								update();
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
				if (b.gameOver()) {
					
					String outcome = "";
					int z = b.peicesCount();
					saveHighScore(z);
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
		}
	}

	public void update() {
		int[][] board = b.getBoard();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				String paneID = "#" + i + j;
				Pane pane = (Pane) gridPane.lookup(paneID);
				drawCircle(board[i][j], pane);
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
		String playerTurn = (startID == 1) ? b.getPlayers().get(1) : b.getPlayers().get(2);
		switch (b.initPlace(row, column, startID)) {
			case 11:
				update();
				if (b.getTurn() == 1)
					label.setText(playerTurn + ": Place second tile");
				showLegalMoves(startID);
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

	public void showLegalMoves(int colour) {
		b.moveAnalyser(colour);
		int circleColour = (colour == 1) ? 3 : 4;
		Set<String> validMovesSet = b.getValidMoves().keySet();
		String[] validMoves = validMovesSet.toArray(new String[validMovesSet.size()]);
		for (int i = 0; i < validMoves.length; i++) {
			String paneID = "#" + validMoves[i].replace(",", "");
			Pane pane = (Pane) gridPane.lookup(paneID);
			drawCircle(circleColour, pane);
		}
	}

	public void hideLegalMoves() {
		Set<String> validMovesSet = b.getValidMoves().keySet();
		String[] validMoves = validMovesSet.toArray(new String[validMovesSet.size()]);
		for (int i = 0; i < validMoves.length; i++) {
			String paneID = "#" + validMoves[i].replace(",", "");
			Pane pane = (Pane) gridPane.lookup(paneID);
			pane.getChildren().clear();
		}
	}

	
	public void start(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(ClassLoader.getSystemResource("advancedreversi//main.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    stage.setScene(scene);
	    stage.getIcons().addAll(
					new Image(getClass().getResourceAsStream("icon16.png")),
					new Image(getClass().getResourceAsStream("icon32.png")),
					new Image(getClass().getResourceAsStream("icon64.png"))
				);
	  // showing null error have to fix it.
		   genstart.fire();
		   
		   stage.show();
	   }
	
	
	public void exit(ActionEvent event) {
		
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setTitle("Exit Reversi");
		a.setContentText("Are you sure you want to exit Reversi?");
		a.setHeaderText("You are exiting Reversi ");
		
		
		if(a.showAndWait().get()==ButtonType.OK)
		Platform.exit();
	}
	
	public void showHighScore(ActionEvent e ) {
		Alert b = new Alert(AlertType.INFORMATION);
		b.setTitle("Highscore");
		b.setContentText("The HighScore is : " + loadHighScore() + "!");
		b.setHeaderText(null);
		b.setX(420);
		b.setY(200);
		
		ButtonType response = b.showAndWait().orElse(ButtonType.CANCEL);
		if(response == ButtonType.OK){
		    b.close();
		}
		b.close();

	}
	
		public void saveHighScore(int score) {
		     if( highscore < score) {
		    	 highscore = score;
		         
		    	File f = new File("HighScore.txt");
		    	if(!f.exists())
		    	try {
		    		f.createNewFile();
		    	}
		    	
		      catch(IOException e) {
		    	  e.printStackTrace();
		     }
		    	 
		    	 try { 	 
		     FileWriter hsfw = new FileWriter("HighScore.txt", true);
		     BufferedWriter hsbw = new BufferedWriter(hsfw);
		     hsbw.write(Integer.toString(highscore));
		     hsbw.newLine();
		      hsbw.close();
		       hsfw.close();

		        } catch (IOException e) {
		          e.printStackTrace();
		      }
           }
	   }
		
		public int loadHighScore() {
			try {
				FileReader hsfr = new FileReader("HighScore.txt");
				BufferedReader hsbr = new BufferedReader(hsfr);
				highscore = Integer.parseInt(hsbr.readLine());
				hsbr.close();
				hsfr.close();
				return highscore;
			}
			catch(Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		
		
		public void mainMenu(ActionEvent event) throws IOException {
			Alert mm = new Alert(AlertType.CONFIRMATION);
			mm.setTitle("Main Menu");
			mm.setContentText("Are you sure you want to go back to main menu?");
			mm.setHeaderText(" By doing this, you cannot continue the game again!");
			
			if(mm.showAndWait().get()==ButtonType.OK)
			 root = FXMLLoader.load(ClassLoader.getSystemResource("advancedreversi//ad.fxml"));
			stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		    scene = new Scene(root);
		    stage.setScene(scene);
		    stage.getIcons().addAll(
						new Image(getClass().getResourceAsStream("icon16.png")),
						new Image(getClass().getResourceAsStream("icon32.png")),
						new Image(getClass().getResourceAsStream("icon64.png"))
					);
			   
			   stage.show();
		   }
		
		
}
		   



