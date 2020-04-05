package Model;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DragonModel {
    public Image image;
    private float health;
    private float happiness;
    FileInputStream input;
    public DragonModel() throws FileNotFoundException {
        health=0.9f;
        happiness=0.8f;
        input = new FileInputStream("Resources/dragon-animated.gif");
        image = new Image(input, 300, 300, true, false);
    }
    public float getHealth(){
        return health;
    }
    public void addHealth(float a){
        if(health+a<=1)health+=a;
    }
    public float getHappiness(){
        return happiness;
    }
    public void addHappiness(float a){
        if(happiness+a<=1)happiness+=a;
    }
}
