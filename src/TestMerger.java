/**
 * Created by Jeffrey Li on 5/24/2016.
 */
public class TestMerger {
    public static void main(String[] args) {
        Sound clap = new Sound("Clap", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\clap.wav");
//        Sound kick = new Sound("Kick", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\kick.wav");
//        Sound snare = new Sound("Snare", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\snare.wav");
        Sound openhihat = new Sound("Open-Hihat", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Open-Hihat.wav");
        Player player = new Player();
        Merger merger = new Merger();
        byte[] boo = merger.merge(clap.getWAV_file(), openhihat.getWAV_file());
        player.play(boo);



    }
}
