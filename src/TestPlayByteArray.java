import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jeffrey Li on 5/25/2016.
 */
public class TestPlayByteArray {
    public static void main(String[] args) {
        Sound clap = new Sound("Clap", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Clap.wav");
        Sound closed_hihat = new Sound("Closed-Hihat", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Closed-hihat.wav");
        Sound open_hihat = new Sound("Open-Hihat", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Open-hihat.wav");
        Sound snare = new Sound("Snare", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Snare.wav");
        byte[] finalbyte = null;
        byte[] finalbyte2 = null;
        Timer soundStopper = new Timer();
        Merger merge = new Merger();
        finalbyte = merge.merge(clap.getWAV_file(), open_hihat.getWAV_file());
        finalbyte2 = merge.merge(clap.getWAV_file(), snare.getWAV_file());
        long closeAfterStopDelay = 1;
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 1, 2, 44100.0f, false);
        try {
            Clip clip = AudioSystem.getClip(); //generates a generic audio clip check API doc for more info
            clip.open(format, finalbyte, 0, finalbyte.length);
            clip.start();
            clip.drain();
            Thread.sleep(1000);

            Clip clip2 = AudioSystem.getClip(); //generates a generic audio clip check API doc for more info
            clip2.open(format, finalbyte2, 0, finalbyte.length);
            clip2.start();

            clip2.drain(); //DOES NOT BLOCK See Bug #4732218
            //Thread.sleep(clip2.);


        } catch (LineUnavailableException e) {
            System.out.println("hello");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//
//        }


    }
}
