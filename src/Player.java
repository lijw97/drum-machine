import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jeffrey Li on 5/30/2016.
 */
public class Player {
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
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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

    public void pause(int milliseconds)
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
