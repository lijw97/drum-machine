/**
 * Created by Jeffrey Li on 5/15/2016.
 */
import java.util.ArrayList;

public class TestBeat {
    public TestBeat(){
        ArrayList<ArrayList<Sound>> soundboard = new ArrayList();
        soundboard.add(new ArrayList<Sound>());
        soundboard.add(new ArrayList<Sound>());
        soundboard.add(new ArrayList<Sound>());
        soundboard.add(new ArrayList<Sound>());
        soundboard.add(new ArrayList<Sound>());
        soundboard.get(0).add(new Sound("C:\\Users\\Jeffrey Li\\drum-machine\\src\\clap.mp3", "Clap"));
        soundboard.get(1).add(new Sound("C:\\Users\\Jeffrey Li\\drum-machine\\src\\kick.mp3", "Kick"));
        soundboard.get(2).add(new Sound("C:\\Users\\Jeffrey Li\\drum-machine\\src\\open-hihat.mp3",
                "Open-Hihat"));
        soundboard.get(3).add(new Sound("C:\\Users\\Jeffrey Li\\drum-machine\\src\\snare1.mp3", "Snare"));
        soundboard.get(4).add(new Sound("C:\\Users\\Jeffrey Li\\drum-machine\\src\\closed-hihat.mp3"
                , "Closed-Hihat"));

    }

}
