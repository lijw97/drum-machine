package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application{
    VBox soundboard = new VBox();
    ArrayList<HBox> instrumentList = new ArrayList<HBox>();
    String baseFile = new File("").getAbsolutePath() + "\\src\\";
    ArrayList<Sound> sounds = new ArrayList<Sound>();
    Set<ToggleButton> buttons = new HashSet();
    Merger merge = new Merger();
    final FileChooser fileChooser = new FileChooser();
    boolean stop = false;

    Player player = new Player();
    int tempo = 200;
    private Desktop desktop = Desktop.getDesktop();
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        System.out.println(baseFile);
//        Parent root = FXMLLoader.load(getClass().getResource("Editor.fxml"));
        Group root = new Group();
        stage= primaryStage;
        addInstrument("Closed Hi-Hat", baseFile+"closed-hihat.wav");
        addInstrument("Snare Drum", baseFile+"snare.wav");
        addInstrument("Kick", baseFile+ "kick.wav");
        addInstrument("Clap", baseFile+"clap.wav");
        addInstrument("Open Hi-Hat", baseFile+"open-hihat.wav");
        root.getChildren().add(soundboard);
        Button playButton = new Button("Play");
        Button clearButton = new Button("Clear");
        Button submit = new Button("Submit");
//        root.getChildren().add(playButton);
//        root.getChildren().add(clearButton);
        playButton.setOnAction(e -> play());
        clearButton.setOnAction(e -> clear());
        soundboard.setLayoutY(220);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("WAV", "*.wav*")
        );
        Button openButton = new Button("Upload WAV file");


        openButton.setOnAction(e -> handle());
        openButton.setLayoutX(100);
        openButton.setLayoutY(100);
        Button stopButton = new Button("Stop");
        Label currTempo = new Label("Curent Tempo: " + 60000 / (4*tempo) + " beats per minute");
        root.getChildren().add(currTempo);
        TextField notification = new TextField();
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Set tempo (beats per min) to: "), 2, 0);
        grid.add(notification, 3, 0);
        grid.add(playButton, 0, 0);
        grid.add(clearButton, 1, 0);
        grid.add(submit, 4, 0);
        grid.add(openButton, 5, 0);
        submit.setOnAction(e -> changeTempo(notification, currTempo));
        stopButton.setOnAction(e -> changeStop());
        Label description = new Label("Welcome to the drum machine! The machine plays sixteen beats worth of sounds for you. \n" +
                "Click on the toggle buttons to play a sound on that specific beat. Type a tempo in the tempo \ntext box to change" +
                " the tempo (it's in beats per minute). Click on the \"Upload WAV file\" to \nupload your own sound snippets! Click the" +
                " delete button to remove a sound. ");
        Label madeby = new Label("Made by Jeffrey Li with the help of Ziad Ali");
        root.getChildren().add(madeby);

        grid.setLayoutY(10);
        root.getChildren().add(description);
        description.setLayoutX(95);
        description.setLayoutY(10);
        root.getChildren().add(grid);
        grid.setLayoutY(150);
        primaryStage.setTitle("Drum Machine");
        primaryStage.setScene(new Scene(root, 650, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
        soundboard.setLayoutX(17);
        currTempo.setLayoutX((650-currTempo.getWidth())/2);
        currTempo.setLayoutY(190);
        grid.setLayoutX((650-grid.getWidth())/2);
        madeby.setLayoutX((650-madeby.getWidth())/2);
        madeby.setLayoutY(95);

    }
    public void changeStop() {
        stop = false;
    }
    public void handle() {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            String filename = file.getName();
            String filepath = file.getAbsolutePath();
            addInstrument(filename, filepath);
        }
    }
//    private void openFile(File file) {
//        try {
//            desktop.open(file);
//        } catch (IOException ex) {
//            Logger.getLogger(
//                    Main.class.getName()).log(
//                    Level.SEVERE, null, ex
//            );
//        }
//    }
    public void changeTempo(TextField text, Label label) {
        if (text.getText() != null && !text.getText().isEmpty()) {
            int x = Integer.parseInt(text.getText());
            text.clear();
            tempo = 60000 / (4*x);
        }
        label.setText("Current tempo: " + 60000/(4*tempo) + " beats per minute");

    }

    public void clear() {
        for (Sound i : sounds) {
            i.clear();
        }
        for (ToggleButton i : buttons) {
            i.setSelected(false);
        }
    }
    public void play() {
        ArrayList<byte[]> playlist = new ArrayList();
        byte[] finalSound2 = null;
        stop = true;
        for (int i = 0; i < 16; i++) {
            ArrayList<Sound> playedSounds = new ArrayList();
            byte[] finalSound = null;
            for (int j = 0; j < sounds.size(); j++) {
                if (sounds.get(j).isPlayed(i)) {
                    playedSounds.add(sounds.get(j));
                }
            }
            if (playedSounds.size() > 1) {
                for (int j = 1; j < playedSounds.size(); j++) {
                    if (finalSound == null) {
                        finalSound = merge.merge(playedSounds.get(j).getWAV_file(), playedSounds.get(j - 1).getWAV_file());
                    } else {
                        finalSound = merge.merge(finalSound, playedSounds.get(j).getWAV_file());
                    }
                }
                playlist.add(finalSound);
            } else if (playedSounds.size() == 1){

                playlist.add(merge.getByteArray(new File(playedSounds.get(0).getPathToWAV())));

            } else {
                playlist.add(new byte[0]);
            }
        }
        int x = 5;
        while (x > 0) {
            for (byte[] sound : playlist) {
                if (sound.length != 0) {
                    player.play(sound);
                }
                try {
                    synchronized (this) {
                        Thread.sleep(tempo);
                    }
                } catch (InterruptedException e) {
                }
            }
            x-=1;
        }
    }
    public void addInstrument(String name, String file) {
        //Initializes HBox that will contain instrument and instrument's buttons
        HBox instrument = new HBox();
        instrument.setSpacing(10.0);
        instrument.setPadding(new Insets(5,0,5,0));
        instrument.setAlignment(Pos.CENTER_LEFT);
        soundboard.getChildren().add(instrument);
        instrumentList.add(instrument);
        sounds.add(new Sound(name, file));

        //Adds ComboBox preset to selected instrument
        Label instrumentCombo = new Label(name);
        instrumentCombo.setAlignment(Pos.CENTER);

        instrumentCombo.setStyle("-fx-background-color: linear-gradient(#f2f2f2, #d6d6d6), linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%), linear-gradient(#dddddd 0%, #f6f6f6 50%);"+
                "-fx-background-radius: 8,7,6;"+
                "-fx-background-insets: 0,1,2;"+
                "-fx-text-fill: black;");
//        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ););");
//        instrumentCombo.setEffect(new DropShadow());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e->delete(deleteButton));
        instrument.getChildren().add(deleteButton);
        instrument.getChildren().add(instrumentCombo);
        instrumentCombo.setMinWidth(130.0);
        instrumentCombo.setPrefWidth(130.0);
        instrument.setMargin(instrumentCombo, new Insets(0, 10, 0, 10));

        //Adds all music buttons to HBox and sets buttons to change array when clicked
        for(int j = 0; j< 16; j++) {
            ToggleButton button = new ToggleButton();
            Integer id = j;
            button.setId(id.toString());
            instrument.getChildren().add(button);

            button.setOnAction(e -> beatClicked(button.getId(), instrumentList.indexOf(instrument)));
            buttons.add(button);
        }
    }
    public int delete(Button button) {
        for (Node child : soundboard.getChildren()) {
            for (Node child2 : ((HBox) child).getChildren()){
                if (child2 == button) {
                    soundboard.getChildren().remove(child);
                    return 0;
                }

            }

        }
        return 0;
    }
    public void beatClicked(String id, int listIndex) {
        //System.out.println("Button: " + id + " pressed in row number: " + listIndex);

        //This is where it edits the array
        sounds.get(listIndex).changePlayStatus(Integer.parseInt(id));

    }


    public static void main(String[] args) {
        launch(args);
    }
}
