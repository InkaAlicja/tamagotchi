package Model;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DragonModel {
    public Image dragon,additionHead,additionFace,additionBack;
    public Image coinImage;
    private float health;
    private float happiness;
    private int money;
    FileInputStream inputDragon;
    FileInputStream inputAdditionFace,inputAdditionHead,inputAdditionBack;
    FileInputStream inputCoin;
    private int pet;
    Media soundHeart,soundEating;
    MediaPlayer mediaPlayerHeart,mediaPlayerEating;

    public DragonModel() throws FileNotFoundException {
        money=100;
        health=0.7f;
        happiness=0.9f;

        inputDragon = new FileInputStream("Resources/dragon-animated.gif");
        dragon = new Image(inputDragon, 300, 300, true, false);

        inputAdditionFace = new FileInputStream("Resources/blank.png");
        additionFace = new Image(inputAdditionFace,100,100,true,false);
        inputAdditionHead = new FileInputStream("Resources/blank.png");
        additionHead = new Image(inputAdditionHead,100,100,true,false);
        inputAdditionBack = new FileInputStream("Resources/blank.png");
        additionBack = new Image(inputAdditionBack,100,100,true,false);

        inputCoin = new FileInputStream("Resources/coin.png");
        coinImage = new Image(inputCoin,20,20,true,false);

        soundHeart = new Media(new File("Resources/heartbeatShort.mp3").toURI().toString());
        mediaPlayerHeart = new MediaPlayer(soundHeart);
        soundEating = new Media(new File("Resources/eating.mp3").toURI().toString());
        mediaPlayerEating = new MediaPlayer(soundEating);
    }
    public static class DyingDragonException extends Exception{}

    public float getHealth(){
        return health;
    }
    public synchronized boolean addHealth(float a) throws DyingDragonException{
        if(health>=1f && a>0)return false;
        if(health<1f && a>=0){
            health+=a;
            health = Float.min(1f, health);
            return true;
        }
        health+=a;//a ujemne
        if(health<0.05f) {
            health=0.05f;
            throw new DyingDragonException();
        }
        return true;
    }
    public float getHappiness(){
        return happiness;
    }
    public void addHappiness(float a){
        if(a>=0 || happiness+a>=0) happiness = Float.min(1f, happiness+a);
    }
    public static class BrokeException extends Exception{}

    public int getMoney(){
        return money;
    }
    public void addMoney(float a)throws BrokeException{
        if(money+a>=0)money+=a;
        else throw new BrokeException();
    }
    public void setAddition(Image image, String where){
        if(where.equals("head")) additionHead = image;
        if(where.equals("face")) additionFace = image;
        if(where.equals("back")) additionBack = image;
    }
    public Image getAddition(String where) {
        if(where.equals("head")) return additionHead;
        else if(where.equals("face")) return additionFace;
        else if(where.equals("back")) return additionBack;
        else return null;
    }
    public boolean incPet(){
        return((++pet)==5);
    }
    public MediaPlayer getMediaPlayerHeart(){
        return mediaPlayerHeart;
    }
    public MediaPlayer getMediaPlayerEating(){
        return mediaPlayerEating;
    }

}
