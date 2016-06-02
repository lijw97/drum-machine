import java.util.Timer;

/**
 * Created by Jeffrey Li on 6/2/2016.
 */
public class AudioData {
    public static void main(String[] args) {


        Sound clap = new Sound("Clap", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Clap.wav");
        Sound closed_hihat = new Sound("Closed-Hihat", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Closed-hihat.wav");
        Sound open_hihat = new Sound("Open-Hihat", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Open-hihat.wav");
        Sound snare = new Sound("Snare", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Snare.wav");
        byte[] finalbyte = null;
        Timer soundStopper = new Timer();
        Merger merge = new Merger();
        finalbyte = merge.merge(clap.getWAV_file(), open_hihat.getWAV_file());
        sun.audio.AudioData audiodata = new sun.audio.AudioData(finalbyte);
        sun.audio.AudioDataStream audioStream = new sun.audio.AudioDataStream(audiodata);
       // sun.audio.AudioPlayer.player.start(audioStream);

    }
}
