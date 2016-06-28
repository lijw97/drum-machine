package sample;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.media.MediaView;
/**
 * Created by Ziad on 5/15/16.
 */
public class EditorController
{
    @FXML private VBox soundboard;
    @FXML public static MediaView mediaView;
    private ScrollPane scrollPane;
    private ArrayList<HBox> instrumentList = new ArrayList<HBox>();
    //String baseFile = "C:\\Users\\Jeffrey Li\\drum-machine\\src\\";
    String baseFile = "/Users/Ziad/Documents/Programming/drum-machine/src/";
    ArrayList<Sound> sounds = new ArrayList<Sound>();
    Merger merge = new Merger();
    Player player = new Player();
    Set<ToggleButton> buttons = new HashSet();
    int NUMBER_OF_BEATS = 16;

    @FXML public void initialize() {
        addInstrument("Closed Hi-Hat", "closed-hihat.wav");
        addInstrument("Snare Drum", "snare.wav");
        addInstrument("Kick", "kick.wav");
        addInstrument("Clap", "clap.wav");
        addInstrument("Open Hi-Hat", "open-hihat.wav");
        Media chance = new Media(Paths.get(baseFile + "sample.mp3").toUri().toString());
        MediaPlayer player = new MediaPlayer(chance);
        mediaView = new MediaView();
        mediaView.setMediaPlayer(player);
        //player.setAutoPlay(true);

        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(soundboard);
        Button playButton = new Button("Play");
        Button clearButton = new Button("Clear");
        playButton.setOnAction(e -> play());
        clearButton.setOnAction(e -> clear());
        soundboard.getChildren().add(playButton);
        soundboard.getChildren().add(clearButton);

    }

    public void addInstrument(String name, String file) {
        //Initializes HBox that will contain instrument and instrument's buttons
        HBox instrument = new HBox();
        instrument.setSpacing(10.0);
        instrument.setPadding(new Insets(5,0,5,0));
        instrument.setAlignment(Pos.CENTER_LEFT);
        soundboard.getChildren().add(instrument);
        instrumentList.add(instrument);
        sounds.add(new Sound(name, baseFile + file));

        //Adds ComboBox preset to selected instrument
        ComboBox<String> instrumentCombo = new ComboBox<String>();
        instrumentCombo.setValue(name);
        instrument.getChildren().add(instrumentCombo);
        instrumentCombo.setMinWidth(130.0);
        instrumentCombo.setPrefWidth(130.0);
        instrument.setMargin(instrumentCombo, new Insets(0, 10, 0, 10));

        //Adds all music buttons to HBox and sets buttons to change array when clicked
        for(int j = 0; j< NUMBER_OF_BEATS; j++) {
            ToggleButton button = new ToggleButton();
            Integer id = j;
            button.setId(id.toString());
            instrument.getChildren().add(button);

            button.setOnAction(e -> beatClicked(button.getId(), instrumentList.indexOf(instrument)));
            buttons.add(button);
        }
    }

    public void beatClicked(String id, int listIndex) {
        System.out.println("Button: " + id + " pressed in row number: " + listIndex);

        //This is where it edits the array
        sounds.get(listIndex).changePlayStatus(Integer.parseInt(id));

    }

    public void play() {
        ArrayList<byte[]> playlist = new ArrayList();
        byte[] finalSound2 = null;
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

            }
        }
        for (byte[] sound : playlist) {
            player.play(sound);
            try {
                synchronized (this) {
                    Thread.sleep(250);
                }
            } catch(InterruptedException e) {}
        }

    }

    public void clear() {
        for (Sound i : sounds) {
            i.clear();
        }
        for (ToggleButton i : buttons) {
            i.setSelected(false);
        }
    }

}