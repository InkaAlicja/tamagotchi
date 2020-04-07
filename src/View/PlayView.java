package View;

import Controller.DragonController;
import Controller.PlayController;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

public class PlayView{
    PlayController controller;
    Button ticTacToeButton, otherGameButton,backButton;
    VBox mainVBox;
    DragonView dragonView;
    Stage stage;
    Scene scene;
    public PlayView(DragonView dragonView){
        controller = new PlayController(this);
        this.dragonView=dragonView;
        ticTacToeButton = new Button("Tic Tac Toe");
        ticTacToeButton.setOnAction(value->controller.playTicTacToe());

        otherGameButton = new Button("other game");
        otherGameButton.setOnAction(value->controller.playOtherGame());

        backButton = new Button("Back");
        backButton.setOnAction(value->controller.back());
        mainVBox = new VBox(ticTacToeButton,otherGameButton, backButton);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(15);
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

    public DragonController getDragonController(){
        return dragonView.getController();
    }
}
