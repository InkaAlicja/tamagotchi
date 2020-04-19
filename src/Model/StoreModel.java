package Model;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class StoreModel {
    public Image coin;
    FileInputStream coinFileInputStream;
    boolean hasSaddle,wearsSaddle;
    public Item hat,bow,saddle;

    public class Item{
        FileInputStream fileInputStreamBig,fileInputStreamSmall;
        public SmallImage smallImage;
        public BigImage bigImage;

        public Item(String streamBig,String streamSmall) throws FileNotFoundException {
            fileInputStreamBig= new FileInputStream(streamBig);
            bigImage = new BigImage(fileInputStreamBig);
            fileInputStreamSmall = new FileInputStream(streamSmall);
            smallImage = new SmallImage(fileInputStreamSmall);
        }
    }

    public StoreModel() throws FileNotFoundException {

        hat = new Item ("Resources/hat.png","Resources/hatSmall.png");
        bow = new Item ("Resources/bow.png","Resources/bowSmall.png");
        saddle = new Item ("Resources/saddle.png","Resources/saddleSmall.png");

        coinFileInputStream = new FileInputStream("Resources/coin.png");
        coin = new Image(coinFileInputStream,20,20,true,false);

        hasSaddle=false;
        wearsSaddle=false;
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

}
