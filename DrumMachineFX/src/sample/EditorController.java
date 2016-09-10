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
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.media.MediaView;
/**
 * Created by Ziad on 5/15/16.
 */
public class EditorController
{
    @FXML private VBox soundboard;
    @FXML public static MediaView mediaView;
    private ScrollPane pane;
    private ArrayList<HBox> instrumentList = new ArrayList<HBox>();
    //String baseFile = "C:\\Users\\Jeffrey Li\\drum-machine\\src\\";
    String baseFile = "C:\\Users\\Jeffrey Li\\drum-machine\\src\\";
    ArrayList<Sound> sounds = new ArrayList<Sound>();
    Merger merge = new Merger();
    Player player = new Player();
    Set<ToggleButton> buttons = new HashSet();
    int NUMBER_OF_BEATS = 16;
    int tempo = 300;

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

        pane = new ScrollPane();
        pane.setFitToWidth(true);
        pane.setContent(soundboard);
        TextField textField = new TextField ();
        textField.setPromptText("Set new tempo (bpm)");
        HBox hb = new HBox();
        soundboard.getChildren().addAll(textField);
        hb.setSpacing(10);
        soundboard.getChildren().add(hb);
        Button submitTempoButton = new Button("Submit");
        soundboard.getChildren().add(submitTempoButton);
        submitTempoButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if ((textField.getText() != null && !textField.getText().isEmpty())) {
                    String string = textField.getText();
                    int i = Integer.parseInt(string);
                    setTempo(i);

                }
            }
        });


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
        Label instrumentCombo = new Label(name);
        instrumentCombo.setAlignment(Pos.CENTER);

        instrumentCombo.setStyle("-fx-background-color: linear-gradient(#f2f2f2, #d6d6d6), linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%), linear-gradient(#dddddd 0%, #f6f6f6 50%);"+
        "-fx-background-radius: 8,7,6;"+
        "-fx-background-insets: 0,1,2;"+
        "-fx-text-fill: black;");
//        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ););");
//        instrumentCombo.setEffect(new DropShadow());
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

            } else {
                playlist.add(new byte[0]);
            }
        }
        for (byte[] sound : playlist) {
            if (sound.length != 0) {
                player.play(sound);
            }
            try {
                synchronized (this) {
                    Thread.sleep(tempo);
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

    public void setTempo(int i) {
        double secondsbetweenbeat = (double) 60 / (double) i;
        int newtempo = (int) (Math.round(secondsbetweenbeat * 1000));
        tempo = newtempo;
    }

}