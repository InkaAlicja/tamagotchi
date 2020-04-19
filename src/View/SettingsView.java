package View;

import Controller.SettingsController;
import com.sun.tools.javac.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class SettingsView {
    MainView mainView;
    HBox colorsHBox;
    VBox mainVBox;
    Scene scene;
    StackPane[] backgroundOptions;
    int backgroundOptionsCount;
    Button backButton;
    SettingsController controller;
    public SettingsView(MainView mainView){
        this.mainView = mainView;
        controller = new SettingsController(this);
        backButton = new Button("Back");
        backButton.setOnAction(value->{
            mainView.stage.setScene(mainView.menu.scene);
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
        mainVBox = new VBox(colorsHBox, backButton);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(2);
        scene = new Scene(mainVBox, 400, 500);
    }

    public StackPane backgroundOption(Color color, int i, BackgroundFill... fill){
        Pane square = new Pane();
        square.setBackground(new Background(fill));
        square.setMaxSize(55, 55);
        square.setMinSize(55,55);
        Pane bigSquare = new Pane();
        bigSquare.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        bigSquare.setMaxSize(60, 60);
        bigSquare.setMinSize(60,60);
        StackPane temp = new StackPane(bigSquare, square);
        temp.setOnMouseClicked(value->{
            controller.setBackground(i);
        });
        return temp;
    }

    public MainView getMainView(){
        return mainView;
    }

    public StackPane[] getBackgroundOptions() {
        return backgroundOptions;
    }

    public void resetBackground(){
        mainVBox.setBackground(mainView.getMainModel().getMainBackground());
    }
}
