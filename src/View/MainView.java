package View;

import Model.AchievementsModel;
import Model.DragonModel;
import Model.MainModel;
import Model.StoreModel;
import data.Data;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainView extends Application {
    Stage stage;
    MainMenuView menu;
    DragonView dragonView;
    StoreView storeView;
    AchievementsView achievementsView;
    MainModel mainModel;
    SettingsView settingsView;
    Data data;

    public MainView(){
        try {
            setUserAgentStylesheet(STYLESHEET_CASPIAN);
            mainModel = new MainModel(this);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setResizable(false);
        stage.setTitle("Tamagotchi");

        data = new Data();
        menu = new MainMenuView(this);
        dragonView = new DragonView(this);
        storeView = new StoreView(this);
        achievementsView = new AchievementsView(this);
        settingsView = new SettingsView(this);
        stage.setScene(menu.scene);
        stage.show();
    }
    @Override
    public void stop() throws IOException, ParseException {
        System.out.println("Stage is closing");
        // Save file
        writeModel("src/data/achievementsModel.bin",this.getAchievementsView().getModel());
        writeModel("src/data/mainModel.bin",this.mainModel);
        writeModel("src/data/storeModel.bin",this.getStoreView().getModel());

        data.time = new Timestamp(System.currentTimeMillis());
        System.out.println(data.time);
        writeModel("src/data/data.bin",data);
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
    public Data getData(){return data;}

    public Stage getStage(){
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    void writeModel(String file,Object mod) throws IOException {
        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(mod);
        objectOutputStream.close();
    }
}
