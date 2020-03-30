package View;

import Model.Dragon;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class DragonScreen {
    Button button;
    Scene scene;
    VBox mainVBox, health, happiness;
    HBox status;
    ProgressBar healthProgress, happinessProgress;
    Label healthText, happinessText;
    Dragon dragon;
    ImageView imageView;
    public DragonScreen(MainView mainView) throws FileNotFoundException {
        dragon = new Dragon();
        button = new Button("back");
        imageView = new ImageView(dragon.image);
        healthProgress = new ProgressBar(dragon.health);
        healthText = new Label("health");
        health = new VBox(healthText, healthProgress);
        happinessProgress = new ProgressBar(dragon.happiness);
        happinessText = new Label("happiness");
        happiness = new VBox(happinessText, happinessProgress);
        status = new HBox(health, happiness);
        status.setAlignment(Pos.CENTER);
        mainVBox = new VBox(imageView, status, button);
        mainVBox.setAlignment(Pos.CENTER);
        button.setOnAction(value->mainView.stage.setScene(new MainMenu(mainView).scene));
        scene = new Scene(mainVBox, 400, 500);
    }
}
