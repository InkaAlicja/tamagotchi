package View;

import Additions.AdBox;
import Additions.AlertBox;
import Controller.DragonController;
import Model.AchievementsModel;
import Model.DragonModel;
import Model.MainModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeUnit;

public class DragonView {
    MainView mainView;
    DragonController controller;
    DragonModel model;
    MainModel.ClickButton ad;
    MainModel.ClickButton back,pet,play,feed,clean;
    Scene scene;
    VBox mainVBox, health, happiness;
    HBox status,backAd;
    ProgressBar healthProgress, happinessProgress;
    ImageView imageDragon;
    ImageView imageAdditionHead,imageAdditionFace,imageAdditionBack,imageAnimation;
    ImageView imageCoin1,imageCoin2;
    Label money;
    StackPane stackPane;
    public DragonView(MainView mainView) throws IOException, ClassNotFoundException {
        this.mainView=mainView;
        model = new DragonModel(this);

        controller = new DragonController(this,model);

        feed = new MainModel.ClickButton("Feed","Resources/eating.mp3",60,30);
        clean = new MainModel.ClickButton("Clean","Resources/bubble.mp3",60,30);
        pet = new MainModel.ClickButton("Pet","Resources/pop.mp3","Resources/petBlinkAnimation.png",60,30);
        play = new MainModel.ClickButton("Play!",60,30);

        imageDragon = new ImageView(model.dragon);
        imageAdditionHead = new ImageView(model.getAddition("head"));
        imageAdditionFace = new ImageView(model.getAddition("face"));
        imageAdditionBack = new ImageView(model.getAddition("back"));
        imageAnimation = new ImageView(model.getAddition("animation"));
        stackPane = new StackPane(imageDragon,imageAdditionHead,imageAdditionFace,imageAdditionBack,imageAnimation);

        imageCoin1 = new ImageView(model.coinImage);
        imageCoin2 = new ImageView(model.coinImage);

        back = new MainModel.ClickButton("Back",60,30);
        ad = new MainModel.ClickButton("ad",imageCoin1,60,30);
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
        mainVBox = new VBox(backAd,money,stackPane, status);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(10);

        back.setOnAction(value->{
            model.getMediaPlayerHeart().stop();
            mainView.stage.setScene(mainView.menu.scene);
        });
        pet.setOnAction(value->{
            controller.addHappiness(0.05f);
            controller.pet();
            controller.setAnimation(pet.image);
        });
        feed.setOnAction(value->{
                if(controller.addMoney(-10)) {
                    if(!controller.addHealth(0.05f))
                        controller.addMoney(10);
                }
        });
        clean.setOnAction(value->{
            if(controller.addMoney(-30)) {
                if(!controller.addHealth(0.15f))
                    controller.addMoney(30);
            }
        });
        play.setOnAction(value->{
            controller.play();
        });
        ad.setOnAction(value-> {
            try {
                 if(AdBox.display("Resources/socksPNG.gif", getMainView().getMainModel().getMainBackground()))
                    controller.addMoney(20);
                 else AlertBox.display("Can't you even wait 10s?","sorry", mainView.getMainModel().getMainBackground());
            } catch (FileNotFoundException e) {
                try {
                    AlertBox.display("No ads to show","OK", mainView.getMainModel().getMainBackground());
                } catch (FileNotFoundException fileNotFoundException) {}
            }
        });
        resetBackground();
        scene = new Scene(mainVBox, 400, 500);
    }

    public void resetBackground(){
        mainVBox.setBackground(mainView.getMainModel().getMainBackground());
    }

    public DragonController getController(){
        return controller;
    }
    public DragonModel getModel(){
        return model;
    }
    public MainView getMainView(){
        return mainView;
    }
    public void setHappiness(float a){happinessProgress.setProgress(a);}
    public void setHealth(float a){healthProgress.setProgress(a);}
    public void setMoney(int a){money.setText(String.valueOf(a));}

    public void setAddition(Image img,String where){
        if(where.equals("head"))imageAdditionHead.setImage(img);
        if(where.equals("face"))imageAdditionFace.setImage(img);
        if(where.equals("back")) imageAdditionBack.setImage(img);
        if(where.equals("animation")) imageAnimation.setImage(img);
    }

}
