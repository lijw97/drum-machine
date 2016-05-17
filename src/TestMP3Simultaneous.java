/**
 * Created by Jeffrey Li on 5/14/2016.
 */
import java.io.*;

import javazoom.jl.decoder.JavaLayerException;

import javazoom.jl.player.*;

public class TestMP3Simultaneous {
    public static void main(String[] args) {
        Thread t = new Thread(new MP3Runnable("C:\\Users\\Jeffrey Li\\drum-machine\\src\\clap.mp3"));
        Thread t2 = new Thread(new MP3Runnable("C:\\Users\\Jeffrey Li\\drum-machine\\src\\open-hihat.mp3"));
        t.start();
        t2.start();

        try {
            Thread.sleep(400);
        } catch (InterruptedException e){
            System.out.println("Interrupted.");
        }
        System.out.println("swag");

    }
}
