package basicreversi;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Controller {

	@FXML

	public TextArea textArea;
	public Circle circle1 = new Circle();
	public Circle circle2 = new Circle();
	public Circle circle = new Circle();
	private Stage stage;
	private Scene scene;
	private Parent root;

	public void changeColorToBlack(MouseEvent e) {
		textArea.appendText("Circle painted blacc\n");
		circle1.setFill(Color.BLACK);
		circle1 = circle2;
	}

	public void changeColorToWhite(MouseEvent e) {
		textArea.appendText("Circle painted WHITE\n");
		circle1.setFill(Color.WHITE);
		circle2 = circle1;
	}

	public void Restart(ActionEvent e) throws IOException {
		
		textArea.appendText("Game has been restarted\n");
		root = FXMLLoader.load(getClass().getResource("main.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	public void onPaneClicked(MouseEvent event) {
		// Get the x and y coordinates of the mouse click
		double x = event.getX();
		double y = event.getY();

		// Create a new circle with its center at (x, y)
		circle.setRadius(36);
		circle.setCenterX(x);
		circle.setCenterY(y);
		circle.setStroke(Color.BLACK);
		circle.setFill(Color.WHITE);

		// Add the circle to the pane
		GridPane pane = (GridPane) event.getSource();
		pane.getChildren().add(circle);
		System.out.println(x);
		System.out.println(y);
	}
}
