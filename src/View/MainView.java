package View;

import Model.MainModel;
import Model.StoreModel;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class MainView extends Application {
    Stage stage;
    MainMenuView menu;
    DragonView dragonView;
    StoreView storeView;
    AchievementsView achievementsView;
    MainModel mainModel;
    SettingsView settingsView;

    public MainView(){
        mainModel = new MainModel(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        menu = new MainMenuView(this);
        dragonView = new DragonView(this);
        storeView = new StoreView(this);
        achievementsView = new AchievementsView(this);
        settingsView = new SettingsView(this);
        stage.setScene(menu.scene);
        stage.show();
    }
    public MainModel getMainModel(){
        return mainModel;
    }

    public DragonView getDragonView() {
        return dragonView;
    }
    public StoreView getStoreView() {
        return storeView;
    }
    public AchievementsView getAchievementsView(){
        return achievementsView;
    }
    public SettingsView getSettingsView(){
        return settingsView;
    }
    public MainMenuView getMainMenuView(){
        return menu;
    }

    public Stage getStage(){
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
