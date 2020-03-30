package View;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends Application {
    Stage stage;
    MainMenu menu;
    DragonScreen dragonScreen;

    public MainView(){
        menu = new MainMenu(this);
        dragonScreen = new DragonScreen(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage=primaryStage;
        stage.setScene(menu.scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
