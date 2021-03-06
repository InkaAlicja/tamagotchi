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
import javafx.scene.text.Font;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MainModel implements Serializable {
    transient MainView view;
    transient Background mainBackground;
    transient BackgroundFill[] mainBackgroundFill;
    transient BackgroundFill[][] backgroundFills;
    int mainBackgroundFillId;
    boolean isMuted;
    transient Set<ClickButton> clickButtonSet;
    transient ClickButton.COLOUR[] colours;
    public MainModel(MainView view) throws IOException, ClassNotFoundException {
        this.view = view;
        clickButtonSet = new HashSet<>();
        backgroundFills = new BackgroundFill[3][2];
        colours = new ClickButton.COLOUR[3];
        backgroundFills[0][0] = new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY);
        colours[0] = ClickButton.COLOUR.WHITE;
        backgroundFills[1][0] = new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY);
        backgroundFills[1][1] = new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, new Insets(5d));
        colours[1] = ClickButton.COLOUR.BEIGE;
        backgroundFills[2][0] = new BackgroundFill(Color.PLUM, CornerRadii.EMPTY, Insets.EMPTY);
        colours[2] = ClickButton.COLOUR.PINK;

        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/data/mainModel.bin"));
            MainModel model = (MainModel) objectInputStream.readObject();
            objectInputStream.close();
            isMuted = model.isMuted;
            mainBackgroundFillId=model.mainBackgroundFillId;
        }catch(Exception e){
            isMuted=false;
            mainBackgroundFillId=0;
        }

        mainBackgroundFill = backgroundFills[mainBackgroundFillId];
        ClickButton.setColour(colours[mainBackgroundFillId]);
        mainBackground = new Background(mainBackgroundFill);

    }

    public void addButton(ClickButton button){
        clickButtonSet.add(button);
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

    public ClickButton.COLOUR[] getColours() { return colours; }

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

    public void resetButtons(){
        for (ClickButton cb: clickButtonSet) {
            try {
                cb.resetButtonBackground("AUTO");
            } catch (FileNotFoundException ignored) { }
        }
    }

    public static class ClickButton extends Button {
        public enum COLOUR{WHITE,BEIGE,PINK}

        static COLOUR colour=COLOUR.WHITE;
       // static String pictureForAll="Resources/cremeButton.png";
        //public static void startList(){List=new LinkedList<>();}

        FileInputStream inputStreamForButton;
        Image imageForButton;
        BackgroundImage backgroundImageForButton;
        Background imageViewForButton;
        Media soundClick;
        public MediaPlayer mediaPlayerClick;
        public Image image;
        static boolean mute=false;
        int width,height;

        public static void setMute(boolean mute){
            ClickButton.mute = mute;
        }

        public ClickButton(String name,int width,int height) throws FileNotFoundException {
            super(name);
            this.setButtonBackground(width,height,"AUTO");
           /* soundClick = new Media(new File("Resources/buttonClick.mp3").toURI().toString());
            mediaPlayerClick = new MediaPlayer(soundClick);
            this.setOnMouseClicked(value->{mediaPlayerClick.setMute(mute);mediaPlayerClick.stop();mediaPlayerClick.play();});*/
           // List.add(this);
        }
        public ClickButton(String name,String sound,int width,int height) throws FileNotFoundException {
            super(name);
            this.setButtonBackground(width,height,"AUTO");

            soundClick = new Media(new File(sound).toURI().toString());
            mediaPlayerClick = new MediaPlayer(soundClick);
            this.setOnMouseClicked(value->{mediaPlayerClick.setMute(mute);mediaPlayerClick.stop();mediaPlayerClick.play();});
           // List.add(this);
        }
        public ClickButton(String name,String sound,String pic,int width,int height) throws FileNotFoundException {
            super(name);
            image = new Image(new FileInputStream(pic),300,300,true,false);
            soundClick = new Media(new File(sound).toURI().toString());
            mediaPlayerClick = new MediaPlayer(soundClick);
            this.setOnMouseClicked(value->{mediaPlayerClick.setMute(mute);mediaPlayerClick.stop();mediaPlayerClick.play();});
            this.setButtonBackground(width,height,"AUTO");
          //  List.add(this);
        }
        public ClickButton(String name,ImageView imgView,int width,int height) throws FileNotFoundException {
            super(name,imgView);
            this.setButtonBackground(width,height,"AUTO");
          //  List.add(this);
        }
        public ClickButton(String name,String pic,int width,int height,boolean b) throws FileNotFoundException {
            super(name);
            this.setButtonBackground(width,height,pic);
        //    List.add(this);
        }

        void setButtonBackground(int width,int height, String pic) throws FileNotFoundException {
            this.width=width;
            this.height=height;
            resetButtonBackground(pic);
        }

        void resetButtonBackground(String pic) throws FileNotFoundException {
            if(pic.equals("AUTO")){
                if (width/height>=3){
                    if(colour==COLOUR.WHITE)pic="Resources/longPinkishButton.png";
                    else if(colour==COLOUR.PINK)pic="Resources/longPurpleButton.png";
                    else pic="Resources/longCremeButton.png";
                }
                else {
                    if (colour == COLOUR.WHITE) pic = "Resources/pinkishButton.png";
                    else if (colour == COLOUR.PINK) pic = "Resources/purpleButton.png";
                    else pic = "Resources/cremeButton.png";
                }
            }
            // pic=pictureForAll;
            inputStreamForButton=new FileInputStream(pic);
            imageForButton=new Image(inputStreamForButton,width,height,false,false);
            backgroundImageForButton =
                    new BackgroundImage(imageForButton, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
            imageViewForButton=new Background(backgroundImageForButton);
            this.setMinWidth(width);
            this.setMinHeight(height);
            this.setBackground(imageViewForButton);
        }
        public static void setColour(COLOUR col){
            colour = col;
        }
    }

}
