package View;

import javafx.geometry.Insets;
import javafx.scene.Scene;
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
    public SettingsView(MainView mainView){
        this.mainView = mainView;
        backgroundOptions = new StackPane[3];
        for (int i=0; i<3; i++){
            backgroundOptions[i] = backgroundOption(mainView.getMainModel().getBackgroundFills()[i]);
        }
        colorsHBox = new HBox(backgroundOptions);
        mainVBox = new VBox(colorsHBox);
        scene = new Scene(mainVBox);
    }

    public StackPane backgroundOption(BackgroundFill... fill){
        Region square = new Region();
        square.setBackground(new Background(fill));
        square.setMaxSize(20, 20);
        Region bigSquare = new Region();
        bigSquare.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        bigSquare.setMaxSize(25, 25);
        return new StackPane(bigSquare, square);
    }
}
