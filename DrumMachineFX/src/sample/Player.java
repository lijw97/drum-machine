package sample;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;

import javax.sound.sampled.*;
import java.io.*;

import java.nio.file.Paths;

/**
 * Created by Jeffrey on 6/5/2016.
 */
public class Player {
    Clip clip;
    AudioInputStream ais;

    public Player() {

    }

    public void play(String file) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        //music = new Media(Paths.get(file).toUri().toString());
        //mediaPlayer = new MediaPlayer(music);
        //mediaPlayer.setAutoPlay(true);

        File realFile = new File(file);
        ais = AudioSystem.getAudioInputStream(realFile);
        clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();
    }
    public void play(byte[] newSound) {
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 1, 2, 44100.0f, false);
        try {
            Clip clip = AudioSystem.getClip(); //generates a generic audio clip check API doc for more info
            clip.open(format, newSound, 0, newSound.length);
            clip.start();

            clip.drain(); //DOES NOT BLOCK See Bug #4732218
            //Thread.sleep(clip.getMicrosecondLength()/1000);
        } catch (LineUnavailableException e){
            System.out.println("hello");
        }

    }


}
