package Model;

import View.DragonView;
import View.MainMenuView;
import View.MainView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
    boolean isMuted;
    public MainModel(MainView view){
        this.view = view;
        isMuted = false;
        backgroundFills = new BackgroundFill[3][2];
        backgroundFills[0][0] = new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY);
        backgroundFills[1][0] = new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY);
        backgroundFills[1][1] = new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, new Insets(5d));
        backgroundFills[2][0] = new BackgroundFill(Color.PLUM, CornerRadii.EMPTY, Insets.EMPTY);
        mainBackgroundFill = backgroundFills[0];
        mainBackgroundFillId=0;
        mainBackground = new Background(mainBackgroundFill);

    }

    public boolean getIsMuted(){
        return isMuted;
    }

    public void setIsMuted(boolean b){
        isMuted = b;
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
        FileInputStream inputStreamForButton;
        Image imageForButton;
        BackgroundImage backgroundImageForButton;
        Background imageViewForButton;
        Media soundClick;
        public MediaPlayer mediaPlayerClick;
        public Image image;
        static boolean mute=false;
        public static void setMute(boolean mute){
            ClickButton.mute = mute;
        }

        public ClickButton(String name,int width,int height) throws FileNotFoundException {
            super(name);
            soundClick = new Media(new File("Resources/buttonClick.mp3").toURI().toString());
            mediaPlayerClick = new MediaPlayer(soundClick);

            this.setButtonBackground(width,height,"Resources/button2.png");

            this.setOnMouseClicked(value->{mediaPlayerClick.setMute(mute);mediaPlayerClick.stop();mediaPlayerClick.play();});
        }
        public ClickButton(String name,String sound,int width,int height) throws FileNotFoundException {
            super(name);
            soundClick = new Media(new File(sound).toURI().toString());
            mediaPlayerClick = new MediaPlayer(soundClick);

            this.setButtonBackground(width,height,"Resources/button2.png");

            this.setOnMouseClicked(value->{mediaPlayerClick.setMute(mute);mediaPlayerClick.stop();mediaPlayerClick.play();});
        }
        public ClickButton(String name,String sound,String pic,int width,int height) throws FileNotFoundException {
            super(name);
            image = new Image(new FileInputStream(pic),300,300,true,false);

            soundClick = new Media(new File(sound).toURI().toString());
            mediaPlayerClick = new MediaPlayer(soundClick);

            this.setButtonBackground(width,height,"Resources/button2.png");

            this.setOnMouseClicked(value->{mediaPlayerClick.setMute(mute);mediaPlayerClick.stop();mediaPlayerClick.play();});
        }
        public ClickButton(String name,ImageView imgView,int width,int height) throws FileNotFoundException {
            super(name,imgView);
            this.setButtonBackground(width,height,"Resources/button2.png");

            soundClick = new Media(new File("Resources/buttonClick.mp3").toURI().toString());
            mediaPlayerClick = new MediaPlayer(soundClick);

            this.setOnMouseClicked(value->{mediaPlayerClick.setMute(mute);mediaPlayerClick.stop();mediaPlayerClick.play();});
        }
        public ClickButton(String name,String pic,int width,int height,boolean b) throws FileNotFoundException {
            super(name);
            this.setButtonBackground(width,height,pic);

            soundClick = new Media(new File("Resources/buttonClick.mp3").toURI().toString());
            mediaPlayerClick = new MediaPlayer(soundClick);

            this.setOnMouseClicked(value->{mediaPlayerClick.setMute(mute);mediaPlayerClick.stop();mediaPlayerClick.play();});
        }

        void setButtonBackground(int width,int height,String pic) throws FileNotFoundException {
            inputStreamForButton=new FileInputStream(pic);
            imageForButton=new Image(inputStreamForButton,width,height,false,false);
            backgroundImageForButton =
                    new BackgroundImage(imageForButton, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
            imageViewForButton=new Background(backgroundImageForButton);
            this.setMinWidth(width);
            this.setMinHeight(height);
            this.setBackground(imageViewForButton);
        }
    }

}
