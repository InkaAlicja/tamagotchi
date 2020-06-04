package Model;

import View.DragonView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.Timestamp;

public class DragonModel implements Serializable {
    DragonView view;
    public Image dragon,additionHead,additionFace,additionBack,animation;
    public Image coinImage;
    private float health;
    private float happiness;
    private int money;
    FileInputStream inputDragon;
    FileInputStream inputAdditionFace,inputAdditionHead,inputAdditionBack,inputAnimation;
    FileInputStream inputCoin;
    private int pet;
    Media soundHeart;
    MediaPlayer mediaPlayerHeart;

    public DragonModel(DragonView view) throws FileNotFoundException {
        this.view=view;
        money=view.getMainView().getData().money;
        health=recalculate(view.getMainView().getData().time,view.getMainView().getData().health);
        happiness=recalculate(view.getMainView().getData().time,view.getMainView().getData().happiness);

        inputDragon = new FileInputStream("Resources/dragon-animated.gif");
        dragon = new Image(inputDragon, 300, 300, true, false);

        inputAdditionFace = new FileInputStream(view.getMainView().getData().facePic);
        additionFace = new Image(inputAdditionFace,300,300,true,false);
        inputAdditionHead = new FileInputStream(view.getMainView().getData().headPic);
        additionHead = new Image(inputAdditionHead,300,300,true,false);
        inputAdditionBack = new FileInputStream(view.getMainView().getData().backPic);
        additionBack = new Image(inputAdditionBack,300,300,true,false);
        inputAnimation = new FileInputStream("Resources/blank.png");
        animation = new Image(inputAnimation,100,100,true,false);

        inputCoin = new FileInputStream("Resources/coin.png");
        coinImage = new Image(inputCoin,20,20,true,false);

        soundHeart = new Media(new File("Resources/heartBeatShort.mp3").toURI().toString());
        mediaPlayerHeart = new MediaPlayer(soundHeart);

    }
    float recalculate(Timestamp oldTime,float value){
        long newTime = new Timestamp(System.currentTimeMillis()).getTime();
        System.out.println(value);
        System.out.println((value-(float)(newTime-oldTime.getTime())/(1000*60*60*48)));
        return value-(float)(newTime-oldTime.getTime())/(1000*60*60*48);
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
            view.getMainView().getData().health=this.health;
            return true;
        }
        health+=a;//a ujemne
        if(health<0.05f) {
            health=0.05f;
            view.getMainView().getData().health=this.health;
            throw new DyingDragonException();
        }
        view.getMainView().getData().health=this.health;
        return true;
    }
    public float getHappiness(){
        return happiness;
    }
    public void addHappiness(float a){
        if(a>=0 || happiness+a>=0) happiness = Float.min(1f, happiness+a);
        view.getMainView().getData().happiness=this.happiness;
    }
    public static class BrokeException extends Exception{}

    public int getMoney(){
        return money;
    }
    public void addMoney(float a)throws BrokeException{
        if(money+a>=0)money+=a;
        else throw new BrokeException();
        view.getMainView().getData().money=this.money;
    }
    public void setAddition(Image image, String imgString,String where){
        if(where.equals("head")) {additionHead = image;view.getMainView().getData().headPic=imgString;}
        if(where.equals("face")) {additionFace = image;view.getMainView().getData().facePic=imgString;}
        if(where.equals("back")) {additionBack = image;view.getMainView().getData().backPic=imgString;}
       // if(where.equals("animation")) animation = image;
    }
    public void setAnimation(Image img){
        animation = img;
    }
    public void setAddition(String where) throws FileNotFoundException {
        FileInputStream input = new FileInputStream("Resources/blank.png");
        Image image = new Image(input,300,300,true,false);
        if(where.equals("head")) {additionHead = image;view.getMainView().getData().headPic="Resources/blank.png";}
        if(where.equals("face")) {additionFace = image;view.getMainView().getData().facePic="Resources/blank.png";}
        if(where.equals("back")) {additionBack = image;view.getMainView().getData().backPic="Resources/blank.png";}
        if(where.equals("animation")) animation = image;
    }
    public Image getAddition(String where) {
        if(where.equals("head")) return additionHead;
        else if(where.equals("face")) return additionFace;
        else if(where.equals("back")) return additionBack;
        else if(where.equals("animation")) return animation;
        else return null;
    }
    public String getAdditionString(String where){
        if(where.equals("head")) return view.getMainView().getData().headPic;
        else if(where.equals("face")) return view.getMainView().getData().facePic;
        else if(where.equals("back")) return view.getMainView().getData().backPic;
        else return null;
    }

    public boolean incPet(){
        return((++pet)==5);
    }
    public MediaPlayer getMediaPlayerHeart(){
        return mediaPlayerHeart;
    }

}
