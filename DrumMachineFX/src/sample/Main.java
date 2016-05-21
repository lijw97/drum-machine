package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Editor.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        String chanceSample = "//Users//Ziad//Documents//Programming//drum-machine//src//sample.mp3";
        String allegroSample = "//Users//Ziad//Documents//Programming//drum-machine//src//allegro.mp3";

        Sound sample1 = new Sound(chanceSample);
        Sound sample2 = new Sound(allegroSample);
        Sound sample3 = new Sound(chanceSample);

        sample1.start();
        sample2.start();
        sample3.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
