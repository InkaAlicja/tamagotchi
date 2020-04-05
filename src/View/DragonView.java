package View;

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
    Button back, pet,play,feed,clean;
    Scene scene;
    VBox mainVBox, health, happiness;
    HBox status,healthButtons;
    ProgressBar healthProgress, happinessProgress;
    DragonModel model;
    ImageView imageView;
    DragonController controller;
    public DragonView(MainView mainView) throws FileNotFoundException {
        feed = new Button("Feed");
        clean = new Button("Clean");
        pet = new Button("Pet");
        play = new Button("Play!");
        back = new Button("Back");

        model = new DragonModel();
        controller=new DragonController(this,model);

        imageView = new ImageView(model.image);

        healthProgress = new ProgressBar(model.getHealth());
        health = new VBox(new Label("Health"), healthProgress,feed,clean);
        health.setAlignment(Pos.CENTER);

        happinessProgress = new ProgressBar(model.getHappiness());
        happiness = new VBox(new Label("Happiness"), happinessProgress,pet,play);
        happiness.setAlignment(Pos.CENTER);

        status = new HBox(health, happiness);
        status.setAlignment(Pos.CENTER);
        mainVBox = new VBox(imageView, status,back);
        mainVBox.setAlignment(Pos.CENTER);

        back.setOnAction(value->mainView.stage.setScene(mainView.menu.scene));
        pet.setOnAction(value->controller.addHappiness(0.05f));
        feed.setOnAction(value->controller.addHealth(0.05f));
        clean.setOnAction(value->controller.addHealth(0.05f));
        play.setOnAction(value->{
            controller.addHealth(-0.2f);
            controller.addHappiness(0.2f);
        });
        scene = new Scene(mainVBox, 400, 500);
    }

    public void setHappiness(float a){happinessProgress.setProgress(a);}
    public void setHealth(float a){healthProgress.setProgress(a);}
}
