package Model;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

public class StoreModel implements Serializable {
    transient public Image coin;
    transient FileInputStream coinFileInputStream;
    boolean hasSaddle,wearsSaddle,boughtAny;
    public Item hat,bow,saddle,flowers,medal,odznaka,trophy,zdzblo,odznaka2,odznaka3,odznaka4;
    public Item [] modelItems;

    public class Item implements Serializable{
        transient FileInputStream fileInputStreamBig,fileInputStreamSmall;
        transient public SmallImage smallImage;
        transient public BigImage bigImage;
        boolean bought;
        boolean wearing;
        boolean won;//for trophies

        public Item(String streamBig,String streamSmall) throws FileNotFoundException {
            fileInputStreamBig= new FileInputStream(streamBig);
            bigImage = new BigImage(fileInputStreamBig);
            fileInputStreamSmall = new FileInputStream(streamSmall);
            smallImage = new SmallImage(fileInputStreamSmall);
            bought=false;
            wearing=false;
            won=false;
        }
        public SmallImage smallImg(){return smallImage;}
        public BigImage bigImg(){return bigImage;}

        public boolean bought(){return bought;}
        public void buy(){bought=true;}
        public boolean isWearing(){return wearing;}
        public void wear(){wearing=true;}
        public void unwear(){wearing=false;}

        public boolean didWeWin(){return won;}
        public void win(){won=true;}
    }

    public StoreModel() throws FileNotFoundException {

        modelItems = new Item[11];
        modelItems[0]= hat = new Item ("Resources/hat.png","Resources/hatSmall.png");
        modelItems[1]= bow = new Item ("Resources/bow.png","Resources/bowSmall.png");
        modelItems[2]= saddle = new Item ("Resources/saddle.png","Resources/saddleSmall.png");
        modelItems[3]= flowers = new Item("Resources/flowers.png","Resources/flowersSmall.png");
        modelItems[4]= medal = new Item("Resources/medal.png","Resources/medalSmall.png");
        modelItems[5]= odznaka = new Item("Resources/odznaka.png","Resources/odznakaSmall.png");
        modelItems[6]= odznaka2 = new Item("Resources/odznaka2.png","Resources/odznaka2Small.png");
        modelItems[7]= odznaka3 = new Item("Resources/odznaka3.png","Resources/odznaka3Small.png");
        modelItems[8]= odznaka4 = new Item("Resources/odznaka4.png","Resources/odznaka4Small.png");
        modelItems[9]= trophy = new Item("Resources/trophy.png","Resources/trophySmall.png");
        modelItems[10]= zdzblo = new Item("Resources/zdzblo.png","Resources/zdzbloSmall.png");


        coinFileInputStream = new FileInputStream("Resources/coin.png");
        coin = new Image(coinFileInputStream,20,20,true,false);

        hasSaddle=false;
        wearsSaddle=false;
        boughtAny=false;
    }
    public boolean hasSaddle(){ return hasSaddle; }
    public void gotSaddle(){ hasSaddle=true; }

    public boolean wearsSaddle(){return wearsSaddle;}
    public void  wearsSaddle(boolean b){wearsSaddle=b;}

    public class SmallImage extends Image {
        public SmallImage(InputStream inputStream) {
            super(inputStream,80,80,true,false);
        }
    }
    public class BigImage extends Image {
        public BigImage(InputStream inputStream) {
            super(inputStream,300,300,true,false);
        }
    }
    public void bought(){
        boughtAny=true;
    }
    public boolean didBuy(){
        return boughtAny;
    }

}
