package Additions;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void display(String message,String action){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinHeight(250);
        window.setMinHeight(250);

        Label label = new Label();
        label.setText(message);

        Button button = new Button(action);
        button.setOnAction(event -> window.close());

        VBox box = new VBox(10);
        box.getChildren().addAll(label,button);
        box.setAlignment(Pos.CENTER);

        Scene scene = new Scene(box);
        window.setScene(scene);

        window.showAndWait();
    }
}
