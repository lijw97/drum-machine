/**
 * Created by Jeffrey Li on 5/24/2016.
 */
public class TestMerger {
    public static void main(String[] args) {
        Sound clap = new Sound("Clap", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\clap.wav");
        Sound kick = new Sound("Kick", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\kick.wav");
        Sound snare = new Sound("Snare", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\snare.wav");
        Merger merger = new Merger();
        Sound clapsnare = merger.merge(clap.getWAV_file(), snare.getWAV_file(), clap.getWAV_name(), snare.getWAV_name(), 1, false);
        Sound clapsnarekick = merger.merge(clapsnare.getWAV_file(), kick.getWAV_file(), clapsnare.getWAV_name(), kick.getWAV_name(), 1, true);


    }
}
