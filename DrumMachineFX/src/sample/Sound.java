package sample;

/**
 * Created by Ziad on 5/21/16.
 */
public class Sound extends Thread
{
    String file = "";
    Player player = new Player();

    public Sound(String fileName)
    {
        file = fileName;
    }

    public void run()
    {
        player.play(file);
    }
}
