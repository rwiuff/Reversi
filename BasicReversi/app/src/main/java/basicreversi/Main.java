package basicreversi;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root  = FXMLLoader.load(getClass().getResource("main.fxml"));
			
	
			primaryStage.getIcons().addAll(
				new Image(getClass().getResourceAsStream("icon16.png")),
				new Image(getClass().getResourceAsStream("icon32.png")),
				new Image(getClass().getResourceAsStream("icon64.png"))
			);
			
			
			Scene scene = new Scene(root);
			Controller controller = new Controller();
			controller.in();
			primaryStage.setTitle("Reversi");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
