package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controller {
	
	@FXML

	public TextArea textArea;
	Circle myCircle = new Circle();

	public void confirmation(MouseEvent e) {
		textArea.appendText("Circle clicked\n");
		myCircle.setFill(Color.BLACK);
		
	}
	
	public void printOutRestart(ActionEvent e) {
		textArea.appendText("Game has been restarted\n");
		
	}
}
