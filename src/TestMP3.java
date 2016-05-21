/**
 * Created by Jeffrey Li on 5/14/2016.
 */


import java.io.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.decoder.JavaLayerException;

import javazoom.jl.player.*;

public class TestMP3 {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            MP3Runnable mp31 = new MP3Runnable("C:\\Users\\Jeffrey Li\\drum-machine\\src\\clap.mp3");
            MP3Runnable mp32 = new MP3Runnable("C:\\Users\\Jeffrey Li\\drum-machine\\src\\snare1.mp3");
            mp31.run();
            mp32.run();
//            try {
//                FileInputStream fis = new FileInputStream("C:\\Users\\Jeffrey Li\\drum-machine\\src\\clap.mp3");
//                try {
//                    Player playMP3 = new Player(fis);
//                    playMP3.play();
//
//
//                } catch (JavaLayerException e) {
//                }
//
//            } catch (FileNotFoundException e) {
//            }
        }



    }
}
