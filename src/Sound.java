import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jeffrey Li on 5/24/2016.
 */
public class Sound {
    private String pathToWAV;
    private File WAV_file;
    private String WAV_name;
    private boolean[] playArray = new boolean[16]; //represents 16 beats
    private Player player = new Player();
    public Sound(String WAV_name, String pathToWAV) {
        this.WAV_name = WAV_name;
        this.pathToWAV = pathToWAV;
        this.WAV_file = new File(pathToWAV);
        for (int i = 0; i < 16; i++) { //initializes everything to false
            playArray[i] = false;
        }
    }
    public Sound(File file) {
        this.WAV_file = file;
        this.pathToWAV = file.getPath();
        this.WAV_name = file.getName().replace(".wav", "");
        for (int i = 0; i < 16; i++) { //initializes everything to false
            playArray[i] = false;
        }
    }
    //changes the play status to the opposite of what it was before.
    public void changePlayStatus(int i) {
        playArray[i] = !(playArray[i]);
    }

    public String getPathToWAV() {
        return pathToWAV;
    }

    public File getWAV_file() {
        return WAV_file;
    }

    public String getWAV_name() {
        return WAV_name;
    }

    public boolean[] getPlayArray() {
        return playArray;
    }

    public boolean isPlayed(int beatindex) {
        return playArray[beatindex];
    }
    public void play() {
        for(int i = 0; i<16; i++)
        {
            if(playArray[i])
            {
                try {
                    player.play(pathToWAV);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                //System.out.println("Played: " + pathToWAV + ", Iteration: " + i);
                try {
                    player.endClip();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //pause(tempo);
        }
    }

    public void pause(int milliseconds) {
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
