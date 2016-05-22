package sample;


import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Created by Ziad on 5/15/16.
 */
public class EditorController
{
    @FXML private VBox soundboard;
    private ArrayList<HBox> instrumentList = new ArrayList<HBox>();
    String baseFile = "//Users//Ziad//Documents//Programming//drum-machine//src//";
    ArrayList<Sound> sounds = new ArrayList<Sound>();

    @FXML public void initialize()
    {
        addInstrument("Hi-Hat", "closed-hihat.mp3");
        addInstrument("Snare Drum", "snare1.mp3");
        addInstrument("Kick", "kick.mp3");
        addInstrument("Clap", "clap.mp3");

        Button playButton = new Button("Play");
        playButton.setOnAction(e -> play());
        soundboard.getChildren().add(playButton);
    }

    public void addInstrument(String name, String file)
    {
        //Initializes HBox that will contain instrument and instrument's buttons
        HBox instrument = new HBox();
        instrument.setSpacing(10.0);
        instrument.setPadding(new Insets(5,0,5,0));
        instrument.setAlignment(Pos.CENTER_LEFT);
        soundboard.getChildren().add(instrument);
        instrumentList.add(instrument);
        sounds.add(new Sound(baseFile + file));

        //Adds ComboBox preset to selected instrument
        ComboBox<String> instrumentCombo = new ComboBox<String>();
        instrumentCombo.setValue(name);
        instrument.getChildren().add(instrumentCombo);
        instrumentCombo.setPrefWidth(130.0);
        instrument.setMargin(instrumentCombo, new Insets(0, 10, 0, 10));

        //Adds all music buttons to HBox and sets buttons to change array when clicked
        for(int j = 0; j< 16; j++)
        {
            ToggleButton button = new ToggleButton();
            Integer id = j;
            button.setId(id.toString());
            instrument.getChildren().add(button);
            button.setOnAction(e -> beatClicked(button.getId(), instrumentList.indexOf(instrument)));
        }
    }

    public void beatClicked(String id, int listIndex)
    {
        System.out.println("Button: " + id + " pressed in row number: " + listIndex);

        //This is where it edits the array
        if(sounds.get(listIndex).array[Integer.parseInt(id)] == 0) sounds.get(listIndex).array[Integer.parseInt(id)] = 1;
        else sounds.get(listIndex).array[Integer.parseInt(id)] = 0;
    }

    public void play()
    {
        
        for(Sound sound : sounds)
        {
            new Thread(sound).start();
        }
    }
}
