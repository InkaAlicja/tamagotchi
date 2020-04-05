package View;

import Model.Dragon;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class DragonScreen {
    Button button, pet;
    Scene scene;
    VBox mainVBox, health, happiness;
    HBox status;
    ProgressBar healthProgress, happinessProgress;
    Dragon dragon;
    ImageView imageView;
    public DragonScreen(MainView mainView) throws FileNotFoundException {
        pet = new Button("Pet");
        dragon = new Dragon();
        button = new Button("Back");
        imageView = new ImageView(dragon.image);
        healthProgress = new ProgressBar(dragon.health);
        health = new VBox(new Label("Health"), healthProgress);
        happinessProgress = new ProgressBar(dragon.happiness);
        happiness = new VBox(new Label("Happiness"), happinessProgress);
        health.setAlignment(Pos.CENTER);
        happiness.setAlignment(Pos.CENTER);
        status = new HBox(health, happiness);
        status.setAlignment(Pos.CENTER);
        mainVBox = new VBox(imageView, status, pet, button);
        mainVBox.setAlignment(Pos.CENTER);
        button.setOnAction(value->mainView.stage.setScene(new MainMenu(mainView).scene));
        pet.setOnAction(value->{dragon.happiness+=0.05f; happinessProgress.setProgress(dragon.happiness);});
        scene = new Scene(mainVBox, 400, 500);
    }
}
