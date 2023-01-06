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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Controller {

	@FXML
	public TextArea textArea;
	
	@FXML
	private Button st;
	
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	private boolean Player1;
	public Pane pane = new Pane();
	
	

	
	@FXML
	public void Restart()  {
		
		st.setOnAction(e -> {
		
		try {
			root = FXMLLoader.load(getClass().getResource("main.fxml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		textArea.appendText("The game has been restarted \n");
		
		});
		
		

	}

	@FXML
		public void onPaneClicked(MouseEvent event) {
			Random rand = new Random();
			
			
			if (rand.nextInt(2)==0) {
				Player1 = true;
				DrawBlackCircle(event);
				textArea.appendText(" Player 2 turn \n");
				textArea.appendText("\n");
			
			 
		}
			else {
				Player1 = false;
				DrawWhiteCircle(event);
				textArea.appendText(" Player 1 turn\n");
				textArea.appendText("\n");
				
			}
	}
	
	public void DrawBlackCircle(MouseEvent event) {
		// Create a new circle with its center at (x, y)
					Circle circle = new Circle(event.getX(),event.getY(),30);
					circle.setStroke(Color.BLACK);
					circle.setFill(Color.BLACK);
			
					// Add the circle to the pane
					Pane pane = (Pane) event.getSource();
					pane.getChildren().add(circle);
					
		
	}
	
	public void DrawWhiteCircle(MouseEvent event) {
		// Create a new circle with its center at (x, y)
		
					Circle circle = new Circle(event.getX(),event.getY(),30);
					circle.setStroke(Color.BLACK);
					circle.setFill(Color.WHITE);
			
					// Add the circle to the pane
					Pane pane = (Pane) event.getSource();
					pane.getChildren().add(circle);
				
		
	}
}
