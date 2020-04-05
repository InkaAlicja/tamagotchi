package Model;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DragonModel {
    public Image image;
    public float health;
    public float happiness;
    FileInputStream input;
    public DragonModel() throws FileNotFoundException {
        health=0.6f;
        happiness=0.5f;
        input = new FileInputStream("Resources/dragon-animated.gif");
        image = new Image(input, 300, 300, true, false);
    }

    public void incHealth(){health+=0.1;}
    public void incHappiness(){happiness+=0.1;}

    public float getHealth(){return this.health;}
    public float getHappiness(){return this.happiness;}
}
