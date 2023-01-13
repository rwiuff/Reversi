package basicreversi;

import java.io.IOException;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ReversiController {

	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	private boolean Player1;
	public TextField Message;

	public void Restart(ActionEvent event) throws IOException {
		Message.appendText("Game has been restarted");
		root = FXMLLoader.load(getClass().getResource("f.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root, 1000, 30);
		scene.setFill(Color.BLACK);
		stage.setScene(scene);
		stage.show();

	}

	public void Pass(ActionEvent event) throws IOException {

		if (Player1 == true) {
			Player1 = false;
			Message.setText("Player2 turn");
			System.out.println("Player2 turn");
		}

		else {
			Player1 = true;
			Message.appendText("Player1 turn");
			System.out.println("Player1 turn");
		}

	}

	public void Firstturn() {

		Random number = new Random();
		int tal = number.nextInt(2);

		if (tal == 0) {
			Player1 = true;
			Message.setText("Player1 turn");
		}

		else {
			Player1 = false;
			Message.setText("Player2 turn");
		}

	}

}
