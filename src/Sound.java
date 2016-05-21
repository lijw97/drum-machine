import java.io.File;
import java.io.*;
import java.util.concurrent.CountDownLatch;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;



/**
 * Created by Jeffrey Li on 5/15/2016.
 */

public class Sound implements Runnable{
    private File soundpath;
    private FileInputStream fis_sound;
    private BufferedInputStream bis_sound;
    private String soundname;
    private boolean[] arrayplay;
//    private CountDownLatch latch;
//
//    public CountDownLatch getLatch() {
//        return latch;
//    }
//
//    public void setLatch(CountDownLatch latch) {
//        this.latch = latch;
//    }

    public File getSoundpath() {
        return soundpath;
    }

    public void setSoundpath(File soundpath) {
        this.soundpath = soundpath;
    }

    public String getSoundname() {
        return soundname;
    }

    public void setSoundname(String soundname) {
        this.soundname = soundname;
    }

    public boolean[] getArrayplay() {
        return arrayplay;
    }

    public void setArrayplay(boolean[] arrayplay) {
        this.arrayplay = arrayplay;
    }

    public Sound(String soundpath, String soundname) {
        this.soundpath = new File(soundpath);
        this.soundname = soundname;
        arrayplay = new boolean[16];
        for (int i = 0; i < 16; i++) {
            arrayplay[i] = false;
        }

    }
    public void run() {
        Thread track = new Thread() {
            public void run() {
                try {
                    fis_sound = new FileInputStream(soundpath);
                    bis_sound = new BufferedInputStream(fis_sound);

                        Player play = new Player(bis_sound);
                        play.play();


                }
                catch (Exception e) { System.out.println(e); }
            }
        };
        track.start();

    }
    public void run(int index) {
        if (arrayplay[index]) {
            run();
        }
    }

    public void printArray() {
        System.out.print("[");
        for (Boolean bool : arrayplay) {
            System.out.print(" " + bool);
        }
        System.out.println("]");
    }

}