package data;

import Model.MainModel;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Data implements Serializable {
    public float health;
    public float happiness;
    public int money;

    public String facePic,headPic,backPic;

    public Data(){
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/data/data.bin"));
            Data temp=(Data)objectInputStream.readObject();
            objectInputStream.close();

            money=temp.money;
            health=temp.health;
            happiness=temp.happiness;
            facePic=temp.facePic;
            headPic=temp.headPic;
            backPic=temp.backPic;
        }catch(Exception e){    System.out.println("Data fail");
            money=100;
            health=0.7f;
            happiness=0.9f;
            facePic="Resources/blank.png";
            headPic="Resources/blank.png";
            backPic="Resources/blank.png";
        }


    }


}
