import java.io.File;

/**
 * Created by Jeffrey Li on 5/24/2016.
 */
public class Sound {
    private String pathToWAV;
    private File WAV_file;
    private String WAV_name;
    private boolean[] playArray = new boolean[16]; //represents 16 beats
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
}
