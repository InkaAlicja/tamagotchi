package Model;

import View.DragonView;
import View.MainMenuView;
import View.MainView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainModel {
    MainView view;
    Background mainBackground;
    BackgroundFill[] mainBackgroundFill;
    BackgroundFill[][] backgroundFills;
    int mainBackgroundFillId;
    public MainModel(MainView view){
        this.view = view;
        backgroundFills = new BackgroundFill[3][2];
        backgroundFills[0][0] = new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY);
        backgroundFills[1][0] = new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY);
        backgroundFills[1][1] = new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, new Insets(5d));
        backgroundFills[2][0] = new BackgroundFill(Color.PLUM, CornerRadii.EMPTY, Insets.EMPTY);
        mainBackgroundFill = backgroundFills[0];
        mainBackgroundFillId=0;
        mainBackground = new Background(mainBackgroundFill);

    }

    public Background getMainBackground(){
        return mainBackground;
    }

    public BackgroundFill[][] getBackgroundFills() {
        return backgroundFills;
    }

    public int getBackgroundFillsCount(){
        return backgroundFills.length;
    }

    public int getMainBackgroundFillId(){
        return mainBackgroundFillId;
    }

    public void setMainBackgroundFill(int i, BackgroundFill... fill){
        mainBackgroundFillId = i;
        mainBackgroundFill = fill;
        mainBackground = new Background(fill);
    }
    public static class ClickButton extends Button {
        Media soundClick;
        public MediaPlayer mediaPlayerClick;
        public Image image;
        public ClickButton(String name) {
            super(name);
            soundClick = new Media(new File("Resources/buttonClick.mp3").toURI().toString());
            mediaPlayerClick = new MediaPlayer(soundClick);

            this.setOnMouseClicked(value->{mediaPlayerClick.stop();mediaPlayerClick.play();});
        }
        public ClickButton(String name,String sound) {
            super(name);
            soundClick = new Media(new File(sound).toURI().toString());
            mediaPlayerClick = new MediaPlayer(soundClick);

            this.setOnMouseClicked(value->{mediaPlayerClick.stop();mediaPlayerClick.play();});
        }
        public ClickButton(String name,String sound,String pic) throws FileNotFoundException {
            super(name);
            image = new Image(new FileInputStream(pic),300,300,true,false);

            soundClick = new Media(new File(sound).toURI().toString());
            mediaPlayerClick = new MediaPlayer(soundClick);

            this.setOnMouseClicked(value->{mediaPlayerClick.stop();mediaPlayerClick.play();});
        }
    }

}
