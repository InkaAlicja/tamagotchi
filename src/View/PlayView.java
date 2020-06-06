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
    MainModel.ClickButton ticTacToeButton, otherGameButton,otherOtherGameButton,backButton, pongOnlineButton;
    VBox mainVBox;
    DragonView dragonView;
    Stage stage;
    Scene scene;
    public PlayView(DragonView dragonView) throws FileNotFoundException {
        this.dragonView=dragonView;
        controller = new PlayController(this);
        ticTacToeButton = new MainModel.ClickButton("Tic Tac Toe",100,50);
        ticTacToeButton.setOnAction(value-> {
            try {
                controller.playTicTacToe();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        otherGameButton = new MainModel.ClickButton("Pong",100,50);
        otherGameButton.setOnAction(value-> {
            try {
                controller.playPong();
            } catch (FileNotFoundException e) { }
        });

        otherOtherGameButton = new MainModel.ClickButton("other game",100,50);
        otherOtherGameButton.setOnAction((value->controller.playOtherOtherGame()));

        pongOnlineButton = new MainModel.ClickButton("Join Lobby", 100, 50);
        pongOnlineButton.setOnAction(v->controller.setLogin());

        backButton = new MainModel.ClickButton("Back",100,50);
        backButton.setOnAction(value->controller.back());
        mainVBox = new VBox(ticTacToeButton,otherGameButton, pongOnlineButton, backButton);//otherOtherGameButton,
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(10);
        resetBackground();
        scene = new Scene(mainVBox, 400, 500);
        stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Play");
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
