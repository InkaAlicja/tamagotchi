package View;

import Controller.Delegate;
import Model.DragonModel;
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

public class DragonView {
    Button backButton,healthButton,happinessButton;
    Scene scene;
    VBox mainVBox, health, happiness;
    HBox status;
    ProgressBar healthProgress, happinessProgress;
    DragonModel dragon;
    ImageView imageView;
    Delegate delegate;
    public DragonView(MainView mainView) throws FileNotFoundException {
        dragon = new DragonModel();
        backButton = new Button("Back");
        healthButton = new Button("Clean");
        happinessButton = new Button("Pet");
        healthButton.setOnAction(value->{dragon.incHealth();});

        imageView = new ImageView(dragon.image);
        healthProgress = new ProgressBar(dragon.health);
        health = new VBox(new Label("Health"), healthProgress,healthButton);
        happinessProgress = new ProgressBar(dragon.happiness);
        happiness = new VBox(new Label("Happiness"), happinessProgress,happinessButton);
        health.setAlignment(Pos.CENTER);
        happiness.setAlignment(Pos.CENTER);
        status = new HBox(health, happiness);
        status.setAlignment(Pos.CENTER);
        mainVBox = new VBox(imageView, status, backButton);
        mainVBox.setAlignment(Pos.CENTER);
        backButton.setOnAction(value->mainView.stage.setScene(new MainMenuView(mainView).scene));
        scene = new Scene(mainVBox, 400, 500);
    }
}
