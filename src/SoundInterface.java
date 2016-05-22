import java.io.File;

/**
 * Created by Jeffrey Li on 5/22/2016.
 */
public interface SoundInterface {
    File getSoundpath(); //gets the path of the sound

    void setSoundpath(File soundpath);

    String getSoundname();

    void setSoundname(String soundname);

    boolean[] getArrayplay(); /* Each sound has an array of 16 that full of booleans.
    the true or false dictate whether or not the sound will be played. true indicates the sound will be played
    and false indicates that it will not. this just returns it.

    */

    void run();

    void run(int index);

    void setTempo(int i); //changes the time between each beat, i is in milliseconds.
}
