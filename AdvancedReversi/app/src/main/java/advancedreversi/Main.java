package advancedreversi;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent menuRoot = menuLoader.load();
            Scene menuScene = new Scene(menuRoot);
            primaryStage.getIcons().addAll(
                    new Image(getClass().getResourceAsStream("icon16.png")),
                    new Image(getClass().getResourceAsStream("icon32.png")),
                    new Image(getClass().getResourceAsStream("icon64.png")));
            primaryStage.setResizable(false);
            File f = new File("HighScore.txt");
            if (!f.exists())
                f.createNewFile();
            primaryStage.setTitle("Reversi");
            primaryStage.setFullScreenExitHint("Press F11 to exit fullscreen");
            primaryStage.setScene(menuScene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
            	event.consume();
            exit(primaryStage);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void beginGame(ActionEvent event) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent mainRoot = mainLoader.load();
        Scene mainScene = new Scene(mainRoot);
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.F11) {
                    primaryStage.setFullScreen(!primaryStage.isFullScreen());
                }
            }
        });
        primaryStage.setScene(mainScene);
        Controller controller = mainLoader.getController();
        controller.setName();
    }

    public void showHighScore(ActionEvent event){
        String highScoreText;
        Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Highscore");
        switch ((int) Controller.loadHighScore()[0]){
            case -1: highScoreText = "No highscore set";
            break;
            default: highScoreText = "The HighScore is " + Controller.loadHighScore()[0] + " set by " + Controller.loadHighScore()[1];
        }
		alert.setContentText(highScoreText);
		alert.setHeaderText(null);
		ImageView graphic = new ImageView(new Image(getClass().getResourceAsStream("icon32.png")));
        alert.setGraphic(graphic);
		ButtonType response = alert.showAndWait().orElse(ButtonType.CANCEL);
		if(response == ButtonType.OK){
		    alert.close();
		}
		alert.close();
    }
    
    // Calls the "exit"-method upon pressing the standard exit-button in the upper corner. 
    public void exitGame(ActionEvent event){
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        exit(primaryStage);
    }
    
    // Prompts the player, upon pressing the "exit"-button, to confirm their choice.
    // Should the player confirm the choice, the program will terminate.
    public void exit(Stage primaryStage){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to exit Reversi?");
        alert.setHeaderText("You are about to exit Reversi");
        alert.setTitle("Exit Reversi");
        ImageView graphic = new ImageView(new Image(getClass().getResourceAsStream("icon32.png")));
        alert.setGraphic(graphic);
        if(alert.showAndWait().get()==ButtonType.OK) Platform.exit();
    }
}