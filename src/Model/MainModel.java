package Model;

import View.DragonView;
import View.MainMenuView;
import View.MainView;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class MainModel {
    MainView view;
    Background mainBackground;
    BackgroundFill[] mainBackgroundFill;
    BackgroundFill[][] backgroundFills;
    public MainModel(MainView view){
        this.view = view;
        backgroundFills = new BackgroundFill[5][2];
        backgroundFills[0][0] = new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY);
        backgroundFills[1][0] = new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY);
        backgroundFills[1][1] = new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, new Insets(5d));
        backgroundFills[2][0] = new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY);
        mainBackgroundFill = backgroundFills[2];
        mainBackground = new Background(mainBackgroundFill);

    }

    public Background getMainBackground(){
        return mainBackground;
    }

    public BackgroundFill[][] getBackgroundFills() {
        return backgroundFills;
    }

    public void setMainBackgroundFill(BackgroundFill... fill){
        mainBackgroundFill = fill;
    }
}
