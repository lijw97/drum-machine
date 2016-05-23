package sample;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Created by Ziad on 5/21/16.
 */
public class Sound implements Runnable
{
    String file = "";
    int[] array = new int[16];
    Player player = new Player();
    boolean running = true;
    boolean onMediaView = false;

    public Sound(String fileName)
    {
        file = fileName;
    }

    public void setArray(int[] newArray)
    {
        array = newArray;
    }

    public void terminate()
    {
        running = false;
    }

    public void restart()
    {
        running = true;
    }

    public void run()
    {
        for(int i = 0; i<16; i++)
        {
            if(array[i] == 1)
            {
                try {
                    player.play(file);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                System.out.println("Played: " + file + ", Iteration: " + i);
                try {
                    player.endClip();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            pause(250);
        }
    }

    public void pause(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}
