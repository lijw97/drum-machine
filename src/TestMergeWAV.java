import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

/**
 * Created by Jeffrey Li on 5/22/2016.
 */
public class TestMergeWAV {
    byte[] sound1;
    byte[] sound2;
    AudioInputStream ais1;
    AudioInputStream ais2;
    public TestMergeWAV(String path1, String path2){

        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        BufferedInputStream in1 = null;
        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        BufferedInputStream in2 = null;
        try {
            in1 = new BufferedInputStream(new FileInputStream(new File(path1)));
            in2 = new BufferedInputStream(new FileInputStream(new File(path2)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int read;
        byte[] buff = new byte[1024];
        int read2;
        byte[] buff2 = new byte[1024];
        try {
            while ((read = in1.read(buff)) > 0) {
                out1.write(buff, 0, read);
            }
        } catch(java.io.IOException e) {

        }
        try {
            while ((read2 = in2.read(buff2)) > 0) {
                out2.write(buff2, 0, read2);
            }
        } catch(java.io.IOException e) {

        }
        try {
            out1.flush();
            out2.flush();
        } catch(java.io.IOException e){}
        sound1 = out1.toByteArray();
        sound2 = out2.toByteArray();
        try {
            try {
                ais1 = AudioSystem.getAudioInputStream(new File(path1));
                ais2 = AudioSystem.getAudioInputStream(new File(path2));
            } catch(IOException e){}
        } catch(UnsupportedAudioFileException e){}

    }
    private byte[] mixBuffers() {
        byte[] array = new byte[Math.max(sound1.length, sound2.length)];
        byte[] max;
        if (sound2.length > sound1.length){
            max = sound2;
        } else {
            max = sound1;
        }
        for (int i=0; i< Math.min(sound1.length, sound2.length); i+=2) {
            short buf1A = sound1[i+1];
            short buf2A = sound1[i];
            buf1A = (short) ((buf1A & 0xff) << 8);
            buf2A = (short) (buf2A & 0xff);

            short buf1B = sound2[i+1];
            short buf2B = sound2[i];
            buf1B = (short) ((buf1B & 0xff) << 8);
            buf2B = (short) (buf2B & 0xff);

            short buf1C = (short) (buf1A + buf1B);
            short buf2C = (short) (buf2A + buf2B);

            short res = (short) (buf1C | buf2C);

            array[i] = (byte) res;
            array[i+1] = (byte) (res >> 8);
        }
        for (int i = Math.min(sound1.length, sound2.length); i < Math.max(sound1.length, sound2.length); i++){
            array[i] = max[i];
        }
        return array;
    }
    private void write(byte[] array){
        ByteArrayInputStream bai = new ByteArrayInputStream(array);
        long max = Math.max(ais1.getFrameLength(), ais2.getFrameLength());
        AudioInputStream ais = new AudioInputStream(bai, ais1.getFormat(), max);
        try {
            AudioSystem.write(ais,
                    AudioFileFormat.Type.WAVE,
                    new File("//Users//Ziad//Documents//Programming//drum-machine//src//new3.wav"));
        } catch(IOException e){}

    }
    public static void main(String[] args) {
        TestMergeWAV wav = new TestMergeWAV("//Users//Ziad//Documents//Programming//drum-machine//src//new3.wav", "//Users//Ziad//Documents//Programming//drum-machine//src//clap.wav");
        byte[] array = wav.mixBuffers();
        wav.write(array);

    }
}
