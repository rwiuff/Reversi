package advancedreversi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        Alert b = new Alert(AlertType.INFORMATION);
		b.setTitle("Highscore");
		b.setContentText("The HighScore is : " + loadHighScore());
		b.setHeaderText(null);
		ImageView graphic = new ImageView(new Image(getClass().getResourceAsStream("icon32.png")));
        b.setGraphic(graphic);
		ButtonType response = b.showAndWait().orElse(ButtonType.CANCEL);
		if(response == ButtonType.OK){
		    b.close();
		}
		b.close();
    }

    public String loadHighScore() {
        try {
            FileReader hsfr = new FileReader("HighScore.txt");
            BufferedReader hsbr = new BufferedReader(hsfr);
            String l = hsbr.readLine();
            String[] p = l.split(",");
            String navn = p[1];
            String score = p[0];
            String points = score + " set by " + navn;
            hsbr.close();
            hsfr.close();
            return points;
        }
        catch(Exception numberForException) {
            return "0";
        }
    }

    public void exitGame(ActionEvent event){
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        exit(primaryStage);
    }

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