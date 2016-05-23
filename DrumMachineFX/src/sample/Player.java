package sample;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;

import javax.sound.sampled.*;
import java.io.*;

import java.nio.file.Paths;

/**
 * Created by Ziad on 5/21/16.
 */
public class Player
{
    Media music;
    MediaPlayer mediaPlayer;
    Clip clip;
    AudioInputStream ais;

    public Player()
    {

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

    public void endClip() throws IOException
    {
        try
        {
            ais.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void pause(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}
