import java.io.File;
import java.io.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;



/**
 * Created by Jeffrey Li on 5/15/2016.
 */

public class Sound {
    File soundpath;
    FileInputStream fis_sound;
    BufferedInputStream bis_sound;
    String soundname;
    boolean play = false;
    public Sound(String soundpath, String soundname) {
        this.soundpath = new File(soundpath);
        this.soundname = soundname;
        try {
            fis_sound = new FileInputStream(this.soundpath);
            bis_sound = new BufferedInputStream(fis_sound);
        } catch(FileNotFoundException exception) {
            System.out.println("Sound in Sound Class is not found!");
        }
    }
    public void run() {
        if (!play) {
            try {
                Player play = new Player(bis_sound);
                play.play();

            } catch (JavaLayerException jexcep) {
                System.out.println("Problem in run in Sound class");
            }
        }
    }
}
