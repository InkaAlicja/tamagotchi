package View;

import Controller.DragonController;
import Controller.PlayController;
import Model.MainModel;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileNotFoundException;

public class PlayView{
    PlayController controller;
    MainModel.ClickButton ticTacToeButton, otherGameButton,otherOtherGameButton,backButton;
    VBox mainVBox;
    DragonView dragonView;
    Stage stage;
    Scene scene;
    public PlayView(DragonView dragonView) throws FileNotFoundException {
        controller = new PlayController(this);
        this.dragonView=dragonView;
        ticTacToeButton = new MainModel.ClickButton("Tic Tac Toe",90,30);
        ticTacToeButton.setOnAction(value-> {
            try {
                controller.playTicTacToe();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        otherGameButton = new MainModel.ClickButton("Pong",90,30);
        otherGameButton.setOnAction(value-> {
            try {
                controller.playPong();
            } catch (FileNotFoundException e) { }
        });

        otherOtherGameButton = new MainModel.ClickButton("other game",90,30);
        otherOtherGameButton.setOnAction((value->controller.playOtherOtherGame()));

        backButton = new MainModel.ClickButton("Back",90,30);
        backButton.setOnAction(value->controller.back());
        mainVBox = new VBox(ticTacToeButton,otherGameButton, backButton);//otherOtherGameButton,
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(15);
        resetBackground();
        scene = new Scene(mainVBox, 400, 500);
        stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public Stage getStage(){
        return stage;
    }

    public void resetScene(){
        stage.setScene(scene);
    }

    public void resetBackground(){
        mainVBox.setBackground(dragonView.mainView.getMainModel().getMainBackground());
    }

    public DragonController getDragonController(){
        return dragonView.getController();
    }
}
