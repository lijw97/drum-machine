/**
 * Created by Jeffrey Li on 5/15/2016.
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import java.util.ArrayList;
public class SoundBoard {
    public ArrayList<Sound> soundboard = new ArrayList();
    public SoundBoard() {
        soundboard.add(new Sound("C:\\Users\\Jeffrey Li\\drum-machine\\src\\clap.mp3", "Clap"));
        soundboard.add(new Sound("C:\\Users\\Jeffrey Li\\drum-machine\\src\\kick.mp3", "Kick"));
        soundboard.add(new Sound("C:\\Users\\Jeffrey Li\\drum-machine\\src\\open-hihat.mp3", "Open-Hihat"));
        soundboard.add(new Sound("C:\\Users\\Jeffrey Li\\drum-machine\\src\\snare1.mp3", "Snare"));
        soundboard.add(new Sound("C:\\Users\\Jeffrey Li\\drum-machine\\src\\closed-hihat.mp3", "Closed-Hihat"));
    }
    public void changePlayStatus(int index, String soundName) {
        Sound selectedSound = null;

        for (int i = 0; i < soundboard.size(); i++) {
            if (soundName == soundboard.get(i).getSoundname()) {
                selectedSound = soundboard.get(i);
            }
        }
        try {
            selectedSound.getArrayplay()[index] = !(selectedSound.getArrayplay()[index]);
        } catch (NullPointerException exception) {
            System.out.println("soundboard did not contain sound!");
        }
    }
   public void run() {
       for (int i = 0; i < 16; i++) {
           int amounttrue = 0;
           for (int j = 0; j < soundboard.size(); j++) {
               if (soundboard.get(j).getArrayplay()[i] == true) {
                   amounttrue += 1;
               }
           }
           //CyclicBarrier gate = new CyclicBarrier(amounttrue);

           for (int j = 0; j < soundboard.size(); j++) {
               if (soundboard.get(j).getArrayplay()[i] == true) {
                   soundboard.get(j).run(i);
               }
           }

           try {
               Thread.sleep(1200);
           } catch (InterruptedException exception) {
               System.out.println("Program stopped.");
           }
       }
   }

    public static void main(String[] args) {
        SoundBoard board = new SoundBoard();
        board.changePlayStatus(0, "Clap");
        board.changePlayStatus(1, "Snare");

        for (int i = 0; i < board.soundboard.size(); i++) {
            board.soundboard.get(i).printArray();
        }
        board.run();
    }

}
