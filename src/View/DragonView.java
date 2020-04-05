package View;

import Additions.Delegate;
import Controller.DragonController;
import Model.DragonModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class DragonView {
    Button button, pet;
    Scene scene;
    VBox mainVBox, health, happiness;
    HBox status;
    ProgressBar healthProgress, happinessProgress;
    DragonModel model;
    ImageView imageView;
    DragonController controller;
    public DragonView(MainView mainView) throws FileNotFoundException {
        pet = new Button("Pet");
        model = new DragonModel();
        controller=new DragonController(this,model);
        button = new Button("Back");
        imageView = new ImageView(model.image);
        healthProgress = new ProgressBar(model.getHealth());
        health = new VBox(new Label("Health"), healthProgress);
        happinessProgress = new ProgressBar(model.getHappiness());
        happiness = new VBox(new Label("Happiness"), happinessProgress);
        health.setAlignment(Pos.CENTER);
        happiness.setAlignment(Pos.CENTER);
        status = new HBox(health, happiness);
        status.setAlignment(Pos.CENTER);
        mainVBox = new VBox(imageView, status, pet, button);
        mainVBox.setAlignment(Pos.CENTER);
        button.setOnAction(value->mainView.stage.setScene(new MainMenuView(mainView).scene));
        pet.setOnAction(value->controller.addHappiness(0.05f));
        scene = new Scene(mainVBox, 400, 500);
    }

    public void setHappiness(float a){happinessProgress.setProgress(a);}
    public void setHealth(float a){healthProgress.setProgress(a);}
}
