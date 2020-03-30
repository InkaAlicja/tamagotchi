package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainMenu{
    Button playButton;
    Scene scene;

    public MainMenu(MainView mainView){
        playButton = new Button("Play");
        playButton.setOnAction(value->mainView.stage.setScene(new DragonScreen(mainView).scene));
        scene = new Scene(playButton, 400, 500);
    }
    public void go(){

    }

}
