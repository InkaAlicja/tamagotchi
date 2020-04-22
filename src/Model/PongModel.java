package Model;

import View.PongView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class PongModel {
    PongView view;
    Media sound;
    MediaPlayer mediaPlayer;
    boolean isIt3to0;
    public PongModel(PongView view){
        this.view=view;
        sound = new Media(new File("Resources/ping.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        isIt3to0=false;
    }
    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }
    public void itIs3to0(){isIt3to0=true;}
    public boolean isIt3to0(){return isIt3to0;}
}
