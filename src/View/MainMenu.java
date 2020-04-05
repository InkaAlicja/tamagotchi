package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class MainMenu{
    Button playButton, settingsButton, exitButton;
    Scene scene;
    VBox vbox;

    public MainMenu(MainView mainView){
        playButton = new Button("Play");
        settingsButton = new Button("Settings");
        exitButton = new Button("Exit");
        playButton.setOnAction(value-> {
            try {
                mainView.stage.setScene(new DragonScreen(mainView).scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        exitButton.setOnAction(value->mainView.stage.close());
        vbox = new VBox(playButton, settingsButton, exitButton);
        vbox.setAlignment(Pos.CENTER);
        scene = new Scene(vbox, 400, 500);
    }

}
