package View;

import Additions.AdBox;
import Additions.AlertBox;
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
import javafx.stage.Popup;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public class DragonView {
    Button back, pet,play,feed,clean,ad;
    Scene scene;
    VBox mainVBox, health, happiness;
    HBox status,backAd;
    ProgressBar healthProgress, happinessProgress;
    DragonModel model;
    ImageView imageView,imageCoin1,imageCoin2;
    DragonController controller;
    Label money;
    public DragonView(MainView mainView) throws FileNotFoundException {
        feed = new Button("Feed");
        clean = new Button("Clean");
        pet = new Button("Pet");
        play = new Button("Play!");

        model = new DragonModel();
        controller = new DragonController(this,model);
        imageView = new ImageView(model.image);
        imageCoin1 = new ImageView(model.coinImage);
        imageCoin2 = new ImageView(model.coinImage);

        back = new Button("Back");
        ad = new Button("ad",imageCoin1);
        backAd=new HBox(back,ad);
        backAd.setSpacing(250);
        backAd.setAlignment(Pos.CENTER);

        healthProgress = new ProgressBar(model.getHealth());
        health = new VBox(new Label("Health"), healthProgress,feed,clean);
        health.setAlignment(Pos.CENTER);
        health.setSpacing(5);

        happinessProgress = new ProgressBar(model.getHappiness());
        happiness = new VBox(new Label("Happiness"), happinessProgress,pet,play);
        happiness.setAlignment(Pos.CENTER);
        happiness.setSpacing(5);

        money = new Label(String.valueOf(model.getMoney()),imageCoin2);

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
                if(controller.addMoney(-10))
                controller.addHealth(0.05f);

        });
        clean.setOnAction(value->{
            if(controller.addMoney(-30))
            controller.addHealth(0.15f);
        });
        play.setOnAction(value->{
            controller.addHealth(-0.2f);
            controller.addHappiness(0.2f);
        });
        ad.setOnAction(value-> {
            try {
                 if(AdBox.display("Resources/coin.png"))
                    controller.addMoney(20);
                 else AlertBox.display("Can't you even wait 10s?","sorry");
            } catch (FileNotFoundException e) {
                AlertBox.display("No ads to show","OK");
            }

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
