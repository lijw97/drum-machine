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
        Timer soundStopper = new Timer();
        Merger merge = new Merger();
        finalbyte = merge.merge(clap.getWAV_file(), open_hihat.getWAV_file());
        long closeAfterStopDelay = 1;
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 1, 2, 44100.0f, false);
        try {


            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine soundLine = (SourceDataLine) AudioSystem.getLine(info);
            soundLine.open(format);
            soundLine.start();
            int nBytesRead = 0;
            byte[] sampledData = new byte[finalbyte.length];
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(sampledData, 0, sampledData.length);
                if (nBytesRead >= 0) {
                    // Writes audio data to the mixer via this source data line.
                    soundLine.write(sampledData, 0, nBytesRead);
                }
            }
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } finally {
            soundLine.drain();
            soundLine.close();
        }
//            Clip clip = AudioSystem.getClip(); //generates a generic audio clip check API doc for more info
//            clip.open(format, finalbyte, 0, finalbyte.length);
//            clip.addLineListener(new LineListener() {
//                public void update(LineEvent le) {
//                    //
//                    // See BUG 4434125 for the reason of this rather interesting
//                    // delay.
//                    //
//                    if (le.getType().equals(LineEvent.Type.STOP)) {
//                        if (soundStopper != null) {
//                            soundStopper.schedule(new TimerTask() {
//                                public void run() {
//                                    clip.close();
//                                }
//                            }, closeAfterStopDelay);
//                        } else {
//                            clip.close();
//                        }
//                    }
//                }
//            });
            //

//            clip.start();


//            Thread.sleep(clip.getMicrosecondLength());
//
//            Clip clip2 = AudioSystem.getClip(); //generates a generic audio clip check API doc for more info
//            clip2.open(format, finalbyte, 0, finalbyte.length);
//            clip2.start();
//
//            clip2.drain(); //DOES NOT BLOCK See Bug #4732218

//            clip.stop();

//        } catch (LineUnavailableException e) {
//            System.out.println("hello");
//        }
//        catch (InterruptedException e) {
//
//        }


    }
}
