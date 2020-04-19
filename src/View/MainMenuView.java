package View;

import Controller.MainMenuController;
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
    Button playButton, settingsButton, exitButton,storeButton,achievementsButton;
    Scene scene;
    VBox vbox;
    MainView mainView;
    MainMenuController controller;
    public MainMenuView(MainView mainView){
        this.mainView=mainView;
        controller = new MainMenuController(this);
        playButton = new Button("Play");
        settingsButton = new Button("Settings");
        exitButton = new Button("Exit");
        storeButton = new Button("Store");
        achievementsButton = new Button("Achievements");
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
