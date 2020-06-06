package Additions;


import Model.MainModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AdBox {
    public static boolean display(String imageName, Background background) throws FileNotFoundException {
        Stage window = new Stage();
        FileInputStream input = new FileInputStream(imageName);
        Image image = new Image(input, 300, 300, true, false);
        ImageView imageView = new ImageView(image);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("AD");

        MainModel.ClickButton button = new MainModel.ClickButton("close",60,30);
        button.setOnAction(event -> window.close());

        VBox box = new VBox(imageView,button);

        box.setAlignment(Pos.CENTER);
        box.setBackground(background);

        Scene scene = new Scene(box);
        window.setScene(scene);

        Date dateBefore = new Date();

        window.showAndWait();

        Date dateAfter = new Date();
        long seconds = (dateAfter.getTime()-dateBefore.getTime())/1000;
        if(seconds>=10) return true;
        else return false;
    }
}
