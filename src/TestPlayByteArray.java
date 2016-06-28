import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jeffrey Li on 5/25/2016.
 */
public class TestPlayByteArray {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException{
        Sound clap = new Sound("Clap", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Clap.wav");
        Sound closed_hihat = new Sound("Closed-Hihat", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Closed-hihat.wav");
        Sound open_hihat = new Sound("Open-Hihat", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Open-hihat.wav");
        Sound snare = new Sound("Snare", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Snare.wav");
        byte[] finalbyte = null;
        byte[] finalbyte2 = null;
        Timer soundStopper = new Timer();
        Merger merge = new Merger();
        Player player = new Player();

        //finalbyte2 = merge.merge(clap.getWAV_file(), snare.getWAV_file());
        long closeAfterStopDelay = 1;
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 1, 2, 44100.0f, false);

            byte[] a = merge.concat(merge.getByteArray(clap.getWAV_file()), merge.getByteArray(snare.getWAV_file()));
            player.play(a);




    }
}
