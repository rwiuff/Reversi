package basicreversi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml")); // Load FXML
            Parent root = loader.load(); // Set FXML as root
            Scene scene = new Scene(root); // Set root for scene
            primaryStage.getIcons().addAll( // Add icons to stage
				new Image(getClass().getResourceAsStream("icon16.png")),
				new Image(getClass().getResourceAsStream("icon32.png")),
				new Image(getClass().getResourceAsStream("icon64.png"))
			);
            primaryStage.setResizable(false); // Disable resizable window
            Controller controller = loader.getController(); // Load controller
            controller.in(); // Initiate game
            primaryStage.setTitle("Reversi"); // Window title
            primaryStage.setScene(scene); // Construct scene
            primaryStage.show(); // Show window
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

	public static void main(String[] args) {
		launch(args);

}
}