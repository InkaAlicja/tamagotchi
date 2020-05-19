package Additions;
import Model.MainModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public class SaddleBox {
    public static void display(String message) throws FileNotFoundException {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinHeight(150);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        MainModel.ClickButton back = new MainModel.ClickButton("Back",60,30);
        back.setOnAction(event -> window.close());

        VBox box = new VBox(10);
        box.getChildren().addAll(label,back);
        box.setAlignment(Pos.CENTER);

        Scene scene = new Scene(box);
        window.setScene(scene);

        window.showAndWait();

    }
}
