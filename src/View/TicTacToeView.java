package View;

import Controller.TicTacToeController;
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

public class TicTacToeView {
    PlayView playView;
    Scene scene;
    TicTacToeModel model;
    TicTacToeController controller;
    Label[] labels;
    Rectangle[] squares;
    StackPane[] stack;
    HBox[] rows;
    Button newGameButton, exitButton;
    VBox columns;
    public TicTacToeView(PlayView playView){
        this.playView=playView;
        model = new TicTacToeModel(this);
        controller = new TicTacToeController(this, model);
    }

    public void setNewScene(){
        stack = new StackPane[9];
        squares = new Rectangle[9];
        labels = new Label[9];
        rows = new HBox[3];
        newGameButton = new Button("New Game!");
        exitButton = new Button("exit");
        newGameButton.setOnAction(value->controller.newGame());
        exitButton.setOnAction(value->controller.exit());
        for (int i=0; i<9; i++){
            labels[i] = new Label();
            squares[i] = new Rectangle();
            squares[i].setHeight(60);
            squares[i].setWidth(60);
            squares[i].setFill(Color.WHITE);
            stack[i] = new StackPane(squares[i], labels[i]);
            int finalI = i;
            stack[i].setOnMouseClicked(value->controller.click(finalI));
        }
        for (int i=0; i<3; i++){
            rows[i] = new HBox(stack[3*i],stack[1+3*i],stack[2+3*i]);
            rows[i].setAlignment(Pos.CENTER);
            rows[i].setSpacing(10);
        }
        columns = new VBox(rows[0], rows[1], rows[2], newGameButton, exitButton);
        columns.setAlignment(Pos.CENTER);
        columns.setSpacing(10);
        scene = new Scene(columns, 400, 500);
        playView.getStage().setScene(scene);
    }

    public Label[] getLabels(){
        return labels;
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
