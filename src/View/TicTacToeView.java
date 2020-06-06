package View;

import Controller.TicTacToeController;
import Model.MainModel;
import Model.TicTacToeModel;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class TicTacToeView {
    PlayView playView;
    Scene scene;
    TicTacToeModel model;
    TicTacToeController controller;
    Rectangle[] squares;
    StackPane[] stack;
    HBox[] rows;
    MainModel.ClickButton newGameButton, exitButton;
    VBox columns;
    public TicTacToeView(PlayView playView) throws FileNotFoundException {
        this.playView=playView;
        model = new TicTacToeModel(this);
        controller = new TicTacToeController(this, model);
    }

    public void setNewScene() throws FileNotFoundException {
        stack = new StackPane[9];
        squares = new Rectangle[9];
        rows = new HBox[3];
        newGameButton = new MainModel.ClickButton("New Game!",80,40);
        exitButton = new MainModel.ClickButton("exit",80,40);
        newGameButton.setOnAction(value-> {
            try {
                controller.newGame();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        exitButton.setOnAction(value->{
            playView.stage.setTitle("Play");
            controller.exit();
        });
        for (int i=0; i<9; i++){
            squares[i] = new Rectangle();
            squares[i].setHeight(60);
            squares[i].setWidth(60);
            squares[i].setFill(Color.WHITE);
            stack[i] = new StackPane(squares[i]);
            int finalI = i;
            stack[i].setOnMouseClicked(value-> {
                try {
                    controller.click(finalI);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        for (int i=0; i<3; i++){
            rows[i] = new HBox(stack[3*i],stack[1+3*i],stack[2+3*i]);
            rows[i].setAlignment(Pos.CENTER);
            rows[i].setSpacing(10);
        }
        columns = new VBox(rows[0], rows[1], rows[2], newGameButton, exitButton);
        columns.setAlignment(Pos.CENTER);
        columns.setSpacing(10);
        columns.setBackground(playView.dragonView.mainView.getMainModel().getMainBackground());
        scene = new Scene(columns, 400, 500);
        playView.getStage().setTitle("TicTacToe");
        playView.getStage().setScene(scene);
    }

    public void setDifficultyLevelScene() throws FileNotFoundException {
        MainModel.ClickButton easyButton = new MainModel.ClickButton("Easy",100,50);
        MainModel.ClickButton hardButton = new MainModel.ClickButton("Hard",100,50);
        MainModel.ClickButton exitDifficultyLevelSceneButton = new MainModel.ClickButton("Exit",100,50);
        easyButton.setOnAction(value-> {
            try {
                controller.setDifficulty(true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        hardButton.setOnAction(value-> {
            try {
                controller.setDifficulty(false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        exitDifficultyLevelSceneButton.setOnAction(value->{
            playView.getStage().setTitle("Play");
            controller.exit();
        });
        VBox vbox = new VBox(easyButton, hardButton, exitDifficultyLevelSceneButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setBackground(playView.dragonView.mainView.getMainModel().getMainBackground());
        Scene difficultyLevelScene = new Scene(vbox, 400, 500);
        playView.getStage().setScene(difficultyLevelScene);
    }


    public StackPane[] getStack() {
        return stack;
    }

    public Scene getScene() {
        return scene;
    }

    public PlayView getPlayView() {
        return playView;
    }
}
