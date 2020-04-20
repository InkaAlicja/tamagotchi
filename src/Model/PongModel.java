package Model;

import View.PongView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class PongModel {
    PongView view;
    Media sound;
    MediaPlayer mediaPlayer;
    public PongModel(PongView view){
        this.view=view;
        sound = new Media(new File("Resources/ping.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
    }
    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }
}
