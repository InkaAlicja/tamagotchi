package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;

public class DragonScreen {
    Button button;
    Scene scene;
    public DragonScreen(MainView mainView){
        button = new Button("back");
        button.setOnAction(value->mainView.stage.setScene(new MainMenu(mainView).scene));
        scene = new Scene(button, 400, 500);
    }
}
