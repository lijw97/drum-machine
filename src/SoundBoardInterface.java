/**
 * Created by Jeffrey Li on 5/22/2016.
 */
public interface SoundBoardInterface {
    void changePlayStatus(int beatindex, String soundName); //when user presses button, changes the play status from false to true or vice versa

    void run(); //runs and plays the sounds
}
