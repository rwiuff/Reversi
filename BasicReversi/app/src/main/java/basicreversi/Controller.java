package basicreversi;
import java.io.IOException;

import javafx.animation.KeyFrame;
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

public class Controller{
	
		@FXML
		public Label label = new Label(" ");
		private Stage stage;
		private Scene scene;
		private Parent root;
		public GridPane gridPane = new GridPane();
		
		private String Player2;
		public Pane pane;
		private Board board;
		private Main view;
		
		
	    Board b = new Board();
	    
	    
	    
	    
	    public void initializeGame() {
	    	in();
	    	DrawWhiteCircle(null);
	    }
	    
	    
	   
	    @FXML
	    public void in() {
	    
	    
	    	    if(b.getPlayers().get("White") == Player2) {
	    	        label.setText("Player2 is White and Player 1 is Black");
	    	        
	    	        // Schedule an event after 2 seconds
	    	        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> label.setText("Player2 Starts first")));
	    	        timeline.play();
	    	    } else {
	    	        label.setText("Player1 is White and Player2 is Black");
	    	        
	    	        // Schedule an event after 2 seconds
	    	        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> label.setText("Player1 Starts first")));
	    	        timeline.play();
	    	    }
	    	}

		@FXML
		public  void Restart(ActionEvent e) throws IOException  {
			
				root = FXMLLoader.load(getClass().getResource("main.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
			
			}
			
		@FXML
			public void onPaneClicked(MouseEvent event) {
			
			
			DrawBlackCircle(event);
			
		}
		
		public void DrawBlackCircle(MouseEvent event) {
			
			Pane pane = (Pane) event.getSource();
			Circle c = new Circle(30, Color.BLACK);
			c.setCenterX(pane.getWidth()/2);
			c.setCenterY(pane.getHeight()/2);
			pane.getChildren().add(c);
		}
		
		public void getCoordinates(MouseEvent event) {
			try {
				int column = GridPane.getColumnIndex((Node) event.getSource());
				int row = GridPane.getRowIndex((Node) event.getSource());
				
				
				System.out.println(column);
				System.out.println(row);	
				} catch (Exception e) {
					if (GridPane.getColumnIndex(pane)==null) {
						GridPane.setColumnIndex(pane, 0);
					}
					if (GridPane.getRowIndex(pane)==null) {
						GridPane.setRowIndex(pane, 0);
					}
					System.out.println(GridPane.getColumnIndex(pane));
					System.out.println(GridPane.getRowIndex(pane));
				}
		}
		
		public void DrawWhiteCircle(MouseEvent event) {
			
			Pane pane = (Pane) event.getSource();
			Circle c = new Circle(30, Color.WHITE);
			c.setCenterX(pane.getWidth()/2);
			c.setCenterY(pane.getHeight()/2);
			
			pane.getChildren().add(c);
			
					
			
		}
		
		
	


}
