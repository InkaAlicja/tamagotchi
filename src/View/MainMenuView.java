package View;

import Controller.MainMenuController;
import Model.MainModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class MainMenuView{
    MainModel.ClickButton playButton, settingsButton, exitButton,storeButton,achievementsButton;
    Scene scene;
    VBox vbox;
    MainView mainView;
    MainMenuController controller;
    public MainMenuView(MainView mainView){
        this.mainView=mainView;
        controller = new MainMenuController(this);
        playButton = new MainModel.ClickButton("Play");
        settingsButton = new MainModel.ClickButton("Settings");
        exitButton = new MainModel.ClickButton("Exit");
        storeButton = new MainModel.ClickButton("Store");
        achievementsButton = new MainModel.ClickButton("Achievements");
        playButton.setOnAction(value-> {
                mainView.stage.setScene(mainView.getDragonView().scene);
        });
        storeButton.setOnAction(value-> {
            mainView.stage.setScene(mainView.getStoreView().scene);
        });
        achievementsButton.setOnAction(value-> {
            mainView.stage.setScene(mainView.getAchievementsView().scene);
        });
        settingsButton.setOnAction(value->{
            mainView.stage.setScene(mainView.getSettingsView().scene);
        });
        exitButton.setOnAction(value->controller.exit());
        vbox = new VBox(playButton, storeButton, achievementsButton, settingsButton, exitButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        scene = new Scene(vbox, 400, 500);
    }

    public void resetBackground(){
        vbox.setBackground(mainView.getMainModel().getMainBackground());
    }

    public MainView getMainView(){
        return mainView;
    }

}
