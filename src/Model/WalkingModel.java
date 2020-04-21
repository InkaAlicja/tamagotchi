package Model;

import View.PongView;
import View.WalkingView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class WalkingModel {
    WalkingView view;
    Media sound,knock;
    MediaPlayer mediaPlayer,mediaPlayerKnock;
    public WalkingModel(WalkingView view){
        this.view=view;
        sound = new Media(new File("Resources/ping.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        knock = new Media(new File("Resources/knock.mp3").toURI().toString());
        mediaPlayerKnock = new MediaPlayer(knock);
    }
    public MediaPlayer getMediaPlayer(String s){
        if(s.equals("click"))return mediaPlayer;
        if(s.equals("knock"))return mediaPlayerKnock;
        return null;
    }
}
