package Model;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StoreModel {
    FileInputStream imgF1,imgF2,imgF3,coinF;
    public Image img1,img2,img3,coin;

    public StoreModel() throws FileNotFoundException {
        imgF1 = new FileInputStream("Resources/coin.png");
        img1 = new Image(imgF1);
        imgF2 = new FileInputStream("Resources/coin.png");
        img2 = new Image(imgF2);
        imgF3 = new FileInputStream("Resources/coin.png");
        img3 = new Image(imgF3);

        coinF = new FileInputStream("Resources/coin.png");
        coin = new Image(coinF,20,20,true,false);
    }


}
