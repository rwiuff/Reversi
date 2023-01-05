package basicreversi;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
	
	


public class Controller {
	
	@FXML

	public TextArea textArea;
	Circle myCircle = new Circle();
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	

	public void confirmation(MouseEvent e) {
		textArea.appendText("Circle clicked\n");
		myCircle.setFill(Color.BLACK);
		
	}
	
	
	
	public void Restart(ActionEvent e) throws IOException {
		
		root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
       scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
		
		
	}
	
	
		
		
	
}
