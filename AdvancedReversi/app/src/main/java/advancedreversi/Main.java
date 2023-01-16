package advancedreversi;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.getIcons().addAll(
				new Image(getClass().getResourceAsStream("icon16.png")),
				new Image(getClass().getResourceAsStream("icon32.png")),
				new Image(getClass().getResourceAsStream("icon64.png"))
			);
            primaryStage.setResizable(false);
            Controller controller = loader.getController();
            controller.in();
            primaryStage.setTitle("Reversi");
            primaryStage.setScene(scene);
            primaryStage.setFullScreenExitHint("Press F11 to exit fullscreen");
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent e) {
                    if(e.getCode() == KeyCode.F11) {
                        primaryStage.setFullScreen(!primaryStage.isFullScreen());
                    }
                }
                });
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		launch(args);

}
}