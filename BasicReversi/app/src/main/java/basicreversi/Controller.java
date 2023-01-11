package basicreversi;
import javafx.animation.KeyFrame;
import java.io.IOException;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Controller{
	
		@FXML
		public Label label = new Label(" ");
		private Stage stage;
		private Scene scene;
		private Parent root;
		
	
		private String Player2;
		public Pane pane = new Pane();
		private Board board;
		private Main view;
		
		
	    Board b = new Board();
	    private boolean gameStarted = false;
	    @FXML
	    public void in() {
	    
	    
	    	    if(b.getPlayers().get("White") == Player2) {
	    	        label.setText("Player2 is White and Player 1 is Black");
	    	        
	    	        // Schedule an event after 2 seconds
	    	        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> { label.setText("Player2 Starts first");
	    	        gameStarted = true;
	    	    }));
	    	        timeline.play();
	    	    } else {
	    	        label.setText("Player1 is White and Player2 is Black");
	    	        
	    	        // Schedule an event after 2 seconds
	    	        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> { label.setText("Player1 Starts first");
	    	        gameStarted = true;
	    	    }));
	    	        timeline.play();
}
	    }
	    
	    @FXML
	    public void Restart(ActionEvent e) throws IOException  {
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
			
			if (gameStarted) {
				
		        DrawBlackCircle(event);
		        
		    } else {
		        // Show a message to the user indicating that they need to wait
		        // for the game to start
		    }
		}
		
		
		public void DrawBlackCircle(MouseEvent event) {
			Color stroke = Color.rgb(179, 179, 179);
			Pane pane = (Pane) event.getSource();
			Circle c = new Circle(30, Color.BLACK);
			c.setStroke(stroke);
			c.setStrokeWidth(3);
			c.setCenterX(pane.getWidth()/2);
			c.setCenterY(pane.getHeight()/2);
			
			pane.getChildren().add(c);
			
			
		}
		
		public void DrawWhiteCircle(MouseEvent event) {
			Color stroke = Color.rgb(153, 153, 153);
			Color fill = Color.rgb(204, 204, 204);
			Pane pane = (Pane) event.getSource();
			Circle c = new Circle(30, Color.WHITE);
			c.setFill(fill);
			c.setStroke(stroke);
			c.setStrokeWidth(3);
			c.setCenterX(pane.getWidth()/2);
			c.setCenterY(pane.getHeight()/2);
			
			pane.getChildren().add(c);
					
			
		}
		
	


}
