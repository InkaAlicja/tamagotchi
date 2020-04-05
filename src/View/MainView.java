package View;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class MainView extends Application {
    Stage stage;
    MainMenuView menu;
    DragonView dragonView;

    public MainView(){}

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        menu = new MainMenuView(this);
        dragonView = new DragonView(this);
        stage.setScene(menu.scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
