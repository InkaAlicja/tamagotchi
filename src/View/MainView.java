package View;

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

    public MainView(){}

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        menu = new MainMenuView(this);
        dragonView = new DragonView(this);
        storeView = new StoreView(this);
        achievementsView = new AchievementsView(this);

        stage.setScene(menu.scene);
        stage.show();
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

    public Stage getStage(){
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
