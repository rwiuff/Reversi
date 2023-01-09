package basicreversi;

import java.io.IOException;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Controller {
	
		@FXML
		public Label label;
		private Stage stage;
		private Scene scene;
		private Parent root;
		private boolean Player1;
		public Pane pane = new Pane();
		
		
		@FXML
		public void intialize() {
			this.label.setText("hell");
		}
	    
	    
		
		@FXML
		public void Restart(ActionEvent e) throws IOException  {
			
				root = FXMLLoader.load(getClass().getResource("main.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
			}
			
		
		@FXML
			public void onPaneClicked(MouseEvent event) {
			
			 Random rand = new Random();
				
			
				
				if (rand.nextInt(2)==0) {
					Player1 = true;
					DrawBlackCircle(event);
					label.setText("Player 2 turn");
					
				
				 
			}
				else {
					Player1 = false;
					DrawWhiteCircle(event);
					label.setText("Player 1 turn");
					
					
				}
		}
		
		public void DrawBlackCircle(MouseEvent event) {
			
			Pane pane = (Pane) event.getSource();
			Circle c = new Circle(30, Color.BLACK);
			c.setCenterX(pane.getWidth()/2);
			c.setCenterY(pane.getHeight()/2);
			
			pane.getChildren().add(c);
			
		}
		
		public void DrawWhiteCircle(MouseEvent event) {
			
			Pane pane = (Pane) event.getSource();
			Circle c = new Circle(30, Color.WHITE);
			c.setCenterX(pane.getWidth()/2);
			c.setCenterY(pane.getHeight()/2);
			
			pane.getChildren().add(c);
					
			
		}
		
	
}
