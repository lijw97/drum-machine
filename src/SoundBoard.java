import java.io.File;
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
    public SoundBoard() {
        soundBoard = new ArrayList();
        soundBoard.add(0, new Sound("Clap", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\clap.wav"));
        soundBoard.add(1, new Sound("Closed-Hihat", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\closed-hihat.wav"));
        soundBoard.add(2, new Sound("Kick", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\kick.wav"));
        soundBoard.add(3, new Sound("Open-Hihat", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\open-hihat.wav"));
        soundBoard.add(4, new Sound("Snare", "C:\\Users\\Jeffrey Li\\drum-machine\\src\\snare.wav"));
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
    public void run() {

        for (int i = 0; i < 16; i++) {
            ArrayList<Sound> playedSounds = new ArrayList();
            //File finalMergedFile = null;
            Sound finalSound = null;
            for (int j = 0; j < 5; j++) {
                if (soundBoard.get(j).isPlayed(i)) {
                    playedSounds.add(soundBoard.get(j));
                }
            }
            for (int j = playedSounds.size() - 1; j > 0; j--) {
                if (j == 0) {
                    finalSound = playedSounds.get(j);
                } else if (finalSound == null && j == 1) {
                    finalSound = (merge.merge(playedSounds.get(j).getWAV_file(),
                            playedSounds.get(j - 1).getWAV_file(), playedSounds.get(j).getWAV_name(),
                            playedSounds.get(j - 1).getWAV_name(), i, true));
                } else if (finalSound.equals(null)) {
                    finalSound = (merge.merge(playedSounds.get(j).getWAV_file(),
                            playedSounds.get(j - 1).getWAV_file(), playedSounds.get(j).getWAV_name(),
                            playedSounds.get(j - 1).getWAV_name(), i, false));

                } else {
                    Sound copyFinalSound = finalSound;
                    finalSound = merge.merge(finalSound.getWAV_file(), playedSounds.get(j - 1).getWAV_file(),
                            finalSound.getWAV_name(), playedSounds.get(j - 1).getWAV_name(), i, j - 1 == 0);
                    copyFinalSound.getWAV_file().delete();
                }
            }
            mergedSounds.add(finalSound);
        }

    }

    public static void main(String[] args) {
        SoundBoard board = new SoundBoard();
        board.changePlayStatus(1, "Clap");
        board.changePlayStatus(0, "Kick");
        board.changePlayStatus(0, "Snare");
        board.changePlayStatus(1, "Open-Hihat");
        board.changePlayStatus(2, "Closed-Hihat");
        board.changePlayStatus(2, "Clap");
        board.run();
        System.out.println("complete");

    }

}
