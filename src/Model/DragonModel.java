package Model;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DragonModel {
    public Image dragon,addition1;
    public Image coinImage;
    private float health;
    private float happiness;
    private int money;
    FileInputStream inputDragon,inputAddition1,inputCoin;

    public DragonModel() throws FileNotFoundException {
        money=100;
        health=0.9f;
        happiness=0.8f;

        inputDragon = new FileInputStream("Resources/dragon-animated.gif");
        dragon = new Image(inputDragon, 300, 300, true, false);

        inputAddition1 = new FileInputStream("Resources/blank.png");
        addition1 = new Image(inputAddition1,100,100,true,false);

        inputCoin = new FileInputStream("Resources/coin.png");
        coinImage = new Image(inputCoin,20,20,true,false);
    }
    public static class DyingDragonException extends Exception{}

    public float getHealth(){
        return health;
    }
    public void addHealth(float a) throws DyingDragonException{
        if(health+a<=1f)health+=a;
        if(health<0.05f) {
            health=0.05f;
            throw new DyingDragonException();
        }
    }
    public float getHappiness(){
        return happiness;
    }
    public void addHappiness(float a){
        if(happiness+a<=1 && happiness+a>=0)happiness+=a;
    }
    public static class BrokeException extends Exception{}

    public int getMoney(){
        return money;
    }
    public void addMoney(float a)throws BrokeException{
        if(money+a>=0)money+=a;
        else throw new BrokeException();
    }
    public void setAdditions(Image image){
        addition1 = image;
    }
    public Image getAdditions(){
        return addition1;
    }
}
