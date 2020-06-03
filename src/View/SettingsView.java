package View;

import Controller.SettingsController;
import Model.MainModel;
import com.sun.tools.javac.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;

//import java.awt.*;

public class SettingsView {
    MainView mainView;
    HBox colorsHBox;
    VBox mainVBox;
    Scene scene;
    StackPane[] backgroundOptions;
    int backgroundOptionsCount;
    MainModel.ClickButton backButton,muteButton;
    SettingsController controller;
    public SettingsView(MainView mainView) throws FileNotFoundException {
        this.mainView = mainView;
        controller = new SettingsController(this);
        backButton = new MainModel.ClickButton("Back",60,30);
        mainView.getMainModel().addButton(backButton);

        backButton.setOnAction(value->{
            mainView.stage.setScene(mainView.menu.scene);
        });

        muteButton = new MainModel.ClickButton("Mute!",60,30);
        mainView.getMainModel().addButton(muteButton);
        if(mainView.getMainModel().getIsMuted()){
            muteButton.setText("Unmute!");
            controller.mute();
        }
        muteButton.setOnAction(value ->{
            if (muteButton.getText().equals("Mute!")) {
                muteButton.setText("Unmute!");
                controller.mute();
            }
            else{
                muteButton.setText("Mute!");
                controller.unmute();
            }
        });
        backgroundOptionsCount = mainView.getMainModel().getBackgroundFillsCount();
        backgroundOptions = new StackPane[backgroundOptionsCount];
        for (int i=0; i<backgroundOptionsCount; i++){
            if (i==0)
                backgroundOptions[i] = backgroundOption(Color.GREEN, i, mainView.getMainModel().getBackgroundFills()[i]);
            else
                backgroundOptions[i] = backgroundOption(Color.GREY, i, mainView.getMainModel().getBackgroundFills()[i]);
        }
        colorsHBox = new HBox(backgroundOptions);
        colorsHBox.setAlignment(Pos.CENTER);
        colorsHBox.setSpacing(2);

        mainVBox = new VBox(new Label("Choose background:"), colorsHBox, new Label("Sound:"), muteButton, backButton);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(5);
        this.resetBackground(mainView.mainModel.getMainBackgroundFillId(),0);
        scene = new Scene(mainVBox, 400, 500);
    }

    public StackPane backgroundOption(Color color, int i, BackgroundFill... fill){
        Pane square = new Pane();
        square.setBackground(new Background(fill));
        square.setMaxSize(55, 55);
        square.setMinSize(55,55);
        Pane bigSquare = new Pane();
        bigSquare.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        bigSquare.setMaxSize(61, 61);
        bigSquare.setMinSize(61,61);
        StackPane temp = new StackPane(bigSquare, square);
        temp.setOnMouseClicked(value->{
            controller.setBackground(i);
            //MainModel.ClickButton.setPictureForAll("Resources/purpleButton.png");//////////
        });
        return temp;
    }

    public MainView getMainView(){
        return mainView;
    }

    public StackPane[] getBackgroundOptions() {
        return backgroundOptions;
    }

    public void resetBackground(int i, int old){
        mainVBox.setBackground(mainView.getMainModel().getMainBackground());
        backgroundOptions[old]=backgroundOption(Color.GREY, old, mainView.getMainModel().getBackgroundFills()[old]);
        backgroundOptions[i]=backgroundOption(Color.GREEN, i, mainView.getMainModel().getBackgroundFills()[i]);
        colorsHBox.getChildren().clear();
        colorsHBox.getChildren().addAll(backgroundOptions);
    }
}
