package Model;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StoreModel {
    FileInputStream imgF1,imgF2,imgF3,coinF;
    FileInputStream imgFB1,imgFB2,imgFB3;
    public Image img1,img2,img3,coin;
    public Image img1big,img2big,img3big;
    boolean hasSaddle,wearsSaddle;

    public StoreModel() throws FileNotFoundException {
        imgF1 = new FileInputStream("Resources/hatSmall.png");
        imgFB1 = new FileInputStream("Resources/hat.png");
        img1 = new Image(imgF1,80,80,true,false);
        img1big = new Image(imgFB1,300,300,true,false);

        imgF2 = new FileInputStream("Resources/bowSmall.png");
        imgFB2 = new FileInputStream("Resources/bow.png");
        img2 = new Image(imgF2,80,80,true,false);
        img2big = new Image(imgFB2,300,300,true,false);

        imgF3 = new FileInputStream("Resources/saddleSmall.png");
        imgFB3 = new FileInputStream("Resources/saddle.png");
        img3 = new Image(imgF3,80,80,true,false);
        img3big = new Image(imgFB3,300,300,true,false);

        coinF = new FileInputStream("Resources/coin.png");
        coin = new Image(coinF,20,20,true,false);

        hasSaddle=false;
        wearsSaddle=false;
    }
    public boolean hasSaddle(){ return hasSaddle; }
    public void gotSaddle(){ hasSaddle=true; }

    public boolean wearsSaddle(){return wearsSaddle;}
    public void  wearsSaddle(boolean b){wearsSaddle=b;}

}
