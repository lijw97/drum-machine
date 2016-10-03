package sample;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;

/**
 * Created by Jeffrey Li on 6/5/2016.
 */
public class Merger {
    public Merger(){

    }
    public byte[] getByteArray(File file) {
        byte[] sound1;

        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        BufferedInputStream in1 = null;
        try {
            in1 = new BufferedInputStream(new FileInputStream(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int read;
        byte[] buff = new byte[1024];
        try {
            while ((read = in1.read(buff)) > 0) {
                out1.write(buff, 0, read);
            }
        } catch(java.io.IOException e) {
            System.out.println("Problem with reading out1.");
        }
        try {
            out1.flush();

        } catch(java.io.IOException e){}
        sound1 = out1.toByteArray();
        System.out.println(sound1.length);
        return sound1;
    }
    public byte[] merge(byte[] array, File file) {
        byte[] sound1 = getByteArray(file);
        return mixBuffers(sound1, array);

    }
    public byte[] merge(File file1, File file2) {
        byte[] sound1 = getByteArray(file1);
        byte[] sound2 = getByteArray(file2);
        return mixBuffers(sound1, sound2);
    }
    private byte[] mixBuffers(byte[] sound1, byte[] sound2) {
        byte[] array = new byte[Math.max(sound1.length, sound2.length)];
        System.out.println(array.length);
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
    private Sound write(byte[] array, AudioInputStream ais1, AudioInputStream ais2,
                        String name1, String name2, int beatnum, boolean isLastMerge) {

        ByteArrayInputStream bai = new ByteArrayInputStream(array);

        long max = Math.max(ais1.getFrameLength(), ais2.getFrameLength());
        AudioInputStream ais = new AudioInputStream(bai, ais1.getFormat(), max);
        File newFile;
        try {
            if (isLastMerge) {
                newFile = new File("src\\" + name1 + name2 + beatnum + ".wav");
                AudioSystem.write(ais,
                        AudioFileFormat.Type.WAVE,
                        newFile);
                return new Sound(newFile);
            } else {
                newFile = new File("src\\" + name1 + name2 + ".wav");
                AudioSystem.write(ais,
                        AudioFileFormat.Type.WAVE,
                        newFile);

                return new Sound(newFile);
            }
        } catch(IOException e){
            System.out.println("problem with write method.");
        }
        return null;
    }
}
