package advancedreversi;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu.fxml")); // Load main menu FXML
            Parent menuRoot = menuLoader.load(); // Set FXML as root
            Scene menuScene = new Scene(menuRoot); // of scene object
            primaryStage.getIcons().addAll( // Add icons to stage
                    new Image(getClass().getResourceAsStream("icon16.png")),
                    new Image(getClass().getResourceAsStream("icon32.png")),
                    new Image(getClass().getResourceAsStream("icon64.png")));
            primaryStage.setResizable(false); // Disable resizable windows
            File f = new File("HighScore.txt"); // Check for highscore file
            if (!f.exists()) // If file doesn't exist
                f.createNewFile(); // Create file
            primaryStage.setTitle("Reversi"); // Window title
            primaryStage.setFullScreenExitHint("Press F11 to exit fullscreen"); // Displays when entering fullscreen
            primaryStage.setScene(menuScene); // Set menu scene in stage
            primaryStage.show(); // Show window
            primaryStage.setOnCloseRequest(event -> {
            	event.consume();
            exit(primaryStage); // When x'ing window -> run exit method
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
 
    // Prompts the player, upon pressing the "exit"-button, to confirm their choice.
    // Should the player confirm the choice, the program will terminate.
    public static void exit(Stage primaryStage){
        Alert alert = new Alert(AlertType.CONFIRMATION); // Create Alert box
        alert.setContentText("Are you sure you want to exit Reversi?");
        alert.setHeaderText("You are about to exit Reversi");
        alert.setTitle("Exit Reversi");
        ImageView graphic = new ImageView(new Image(Main.class.getResourceAsStream("icon32.png")));
        alert.setGraphic(graphic); // Load icon as graphic
        if(alert.showAndWait().get()==ButtonType.OK) Platform.exit(); // Pressing OK -> closing app
    }
}