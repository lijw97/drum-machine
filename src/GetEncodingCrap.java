import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jeffrey Li on 6/1/2016.
 */
public class GetEncodingCrap {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Jeffrey Li\\drum-machine\\src\\Snare.wav");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioInputStream.getFormat();
            long audioFileLength = file.length();
            int frameSize = format.getFrameSize();
            float frameRate = format.getFrameRate();
            AudioFormat.Encoding e = format.getEncoding();
            float i = format.getSampleRate();
            float durationInSeconds = (audioFileLength / (frameSize * frameRate));
            int j = format.getChannels();
            System.out.println(audioFileLength);
            System.out.println(frameSize);
            System.out.println(frameRate);
            System.out.println(e);
            System.out.println(i);
            System.out.println(j);

        } catch(UnsupportedAudioFileException | IOException e) {}
    }
}
