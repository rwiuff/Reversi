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
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.getIcons().addAll(
				new Image(getClass().getResourceAsStream("icon16.png")),
				new Image(getClass().getResourceAsStream("icon32.png")),
				new Image(getClass().getResourceAsStream("icon64.png"))
			);
           
            primaryStage.setResizable(false);
            
            primaryStage.setTitle("Reversi");
            primaryStage.setScene(scene);
            File f = new File("HighScore.txt");
		    	if(!f.exists())	f.createNewFile();
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
            	event.consume();
            exit(primaryStage);
            });
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
 
	public static void main(String[] args) {
		launch(args);
	}
	
	public void exit(Stage primaryStage) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION,"Exit Reversi");
		alert.setContentText("Are you sure you want to exit Reversi?");
		alert.setHeaderText("You are about to Reversi");
		alert.setTitle("Exit Reversi");
		alert.setX(450);
		alert.setY(200);
		
		if(alert.showAndWait().get()==ButtonType.OK)
		Platform.exit();
	}
}