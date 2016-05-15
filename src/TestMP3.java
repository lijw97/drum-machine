/**
 * Created by Jeffrey Li on 5/14/2016.
 */


import java.io.*;

import javazoom.jl.decoder.JavaLayerException;

import javazoom.jl.player.*;

public class TestMP3 {
    public static void main(String[] args) {
        try{
            File file = new File("C:\\Users\\Jeffrey Li\\drum-machine\\src\\clap.mp3");
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            try {
                Player play = new Player(bis);
                play.play();

            } catch (JavaLayerException jexcep) {

            }
        } catch (IOException ex) {

        }
    }
}
