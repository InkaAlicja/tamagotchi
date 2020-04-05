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
    Button back, pet,play,feed,clean,ad;
    Scene scene;
    VBox mainVBox, health, happiness;
    HBox status,backAd;
    ProgressBar healthProgress, happinessProgress;
    DragonModel model;
    ImageView imageView;
    DragonController controller;
    Label money;
    public DragonView(MainView mainView) throws FileNotFoundException {
        feed = new Button("Feed");
        clean = new Button("Clean");
        pet = new Button("Pet");
        play = new Button("Play!");

        back = new Button("Back");
        ad = new Button("ad");
        backAd=new HBox(back,ad);
        backAd.setSpacing(250);
        backAd.setAlignment(Pos.CENTER);

        model = new DragonModel();
        controller = new DragonController(this,model);

        imageView = new ImageView(model.image);

        healthProgress = new ProgressBar(model.getHealth());
        health = new VBox(new Label("Health"), healthProgress,feed,clean);
        health.setAlignment(Pos.CENTER);
        health.setSpacing(5);

        happinessProgress = new ProgressBar(model.getHappiness());
        happiness = new VBox(new Label("Happiness"), happinessProgress,pet,play);
        happiness.setAlignment(Pos.CENTER);
        happiness.setSpacing(5);

        money = new Label(String.valueOf(model.getMoney()));

        status = new HBox(health, happiness);
        status.setAlignment(Pos.CENTER);
        status.setSpacing(20);
        mainVBox = new VBox(backAd,money,imageView, status);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(10);

        back.setOnAction(value->mainView.stage.setScene(mainView.menu.scene));
        pet.setOnAction(value->{
            controller.addHappiness(0.05f);
        });
        feed.setOnAction(value->{
            controller.addMoney(-10);
            controller.addHealth(0.05f);
        });
        clean.setOnAction(value->{
            controller.addMoney(-30);
            controller.addHealth(0.15f);
        });
        play.setOnAction(value->{
            controller.addHealth(-0.2f);
            controller.addHappiness(0.2f);
        });
        scene = new Scene(mainVBox, 400, 500);
    }

    public DragonController getController(){
        return controller;
    }

    public void setHappiness(float a){happinessProgress.setProgress(a);}
    public void setHealth(float a){healthProgress.setProgress(a);}
    public void setMoney(int a){money.setText(String.valueOf(a));}
}
