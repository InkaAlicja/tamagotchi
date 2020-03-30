package Model;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Dragon {
    public Image image;
    public float health;
    public float happiness;
    FileInputStream input;
    public Dragon() throws FileNotFoundException {
        health=0.9f;
        happiness=0.8f;
        input = new FileInputStream("resources/dragon-animated.gif");
        image = new Image(input, 300, 300, true, false);
    }
}
