package sample;

/**
 * Created by Ziad on 5/21/16.
 */
public class Sound implements Runnable
{
    String file = "";
    int[] array = new int[16];
    Player player = new Player();
    boolean running = true;

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
                    player.play(file);
                    System.out.println("Played: " + file + ", Iteration: " + i);
                }
                pause(250);
            }
            terminate();

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
