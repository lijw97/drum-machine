package sample;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

import java.nio.file.Paths;

/**
 * Created by Ziad on 5/21/16.
 */
public class Player
{
    public Player()
    {

    }

    public void play(String file)
    {
        Media music = new Media(Paths.get(file).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setAutoPlay(true);
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
