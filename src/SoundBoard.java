import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Jeffrey Li on 5/24/2016.
 */
public class SoundBoard implements SoundBoardInterface{
    ArrayList<Sound> soundBoard; //things in soundBoard are arranged in alphabetical order
    Merger merge = new Merger();
    HashSet<Sound> mergedSounds;
    int tempo = 600; //default tempo is 100 bpm or 600ms between beats.
    Player player = new Player();
    public SoundBoard() {
        soundBoard = new ArrayList();
        soundBoard.add(0, new Sound("Clap", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Clap.wav"));
        soundBoard.add(1, new Sound("Closed-Hihat", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Closed-hihat.wav"));
        soundBoard.add(2, new Sound("Kick", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Kick.wav"));
        soundBoard.add(3, new Sound("Open-Hihat", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Open-hihat.wav"));
        soundBoard.add(4, new Sound("Snare", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\Snare.wav"));
        mergedSounds = new HashSet();
    }
    public void changePlayStatus(int beatindex, String soundName) {
        Sound selectedSound;
        for (int i = 0; i < soundBoard.size(); i++) {
            if (soundBoard.get(i).getWAV_name().equals(soundName)) {
                selectedSound = soundBoard.get(i);
                selectedSound.changePlayStatus(beatindex);
                break;
            }
        }
    }
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public void run() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        for (int i = 0; i < 16; i++) {
            ArrayList<Sound> playedSounds = new ArrayList();
            byte[] finalSound = null;
            for (int j = 0; j < 5; j++) {
                if (soundBoard.get(j).isPlayed(i)) {
                    playedSounds.add(soundBoard.get(j));
                }
            }
            if (playedSounds.size() > 1) {
                for (int j = 1; j < playedSounds.size(); j++) {
                    if (finalSound == null) {
                        finalSound = merge.merge(playedSounds.get(j).getWAV_file(), playedSounds.get(j - 1).getWAV_file());
                    } else {
                        finalSound = merge.merge(finalSound, playedSounds.get(j).getWAV_file());
                    }
                }
                player.play(finalSound);
            } else if (playedSounds.size() == 1){
                player.play(playedSounds.get(0).getPathToWAV());
            }
            try {
                if (i != 15) {
                    Thread.sleep(250);
                } else {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        SoundBoard board = new SoundBoard();
        board.changePlayStatus(0, "Kick");
        board.changePlayStatus(1, "Kick");
        board.changePlayStatus(2, "Kick");
        board.changePlayStatus(3, "Open-Hihat");
        board.changePlayStatus(4, "Kick");
        board.changePlayStatus(5, "Kick");
        board.changePlayStatus(6, "Kick");
        board.changePlayStatus(7, "Open-Hihat");
        board.changePlayStatus(8, "Kick");
        board.changePlayStatus(9, "Kick");
        board.changePlayStatus(10, "Kick");
        board.changePlayStatus(11, "Open-Hihat");
        board.changePlayStatus(12, "Kick");
        board.changePlayStatus(13, "Kick");
        board.changePlayStatus(14, "Kick");
        board.changePlayStatus(15, "Open-Hihat");
        board.run();
        System.out.println("complete");

    }

}
