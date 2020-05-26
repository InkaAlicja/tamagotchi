import Model.DragonModel;
import Model.MainModel;
import View.MainMenuView;
import View.MainView;
import Controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.html.ImageView;
import java.io.FileInputStream;
import java.io.IOException;

import static javafx.application.Application.launch;

public class Main{

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MainView view = new MainView();
        MainModel model = new MainModel(view);

        MainController controller = new MainController(view, model);
        MainView.main(args);
    }

}