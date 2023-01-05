package basicreversi;

import javafx.application.Application;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ReversiView extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("app");

		// if fxml does not work refersh project first, then try code below
		// System.out.println(ClassLoader.getSystemResource("Reversi/f.fxml"));
		Parent root = FXMLLoader.load(ClassLoader.getSystemResource("Reversi/f.fxml"));

		stage.setScene(new Scene(root, 500, 300));

		stage.show();

	}

}
