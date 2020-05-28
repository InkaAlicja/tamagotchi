package Controller;

import Additions.AlertBox;
import Additions.SaddleBox;
import View.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class PlayController {
    PlayView view;
    TicTacToeView ticTacToeView;
    PongView pongView;
    WalkingView walkingView;
    LobbyView lobbyView;
    public PlayController(PlayView view){
        this.view=view;
    }

    public void back(){
        view.getStage().close();
    }

    public void playTicTacToe() throws FileNotFoundException {
        ticTacToeView = new TicTacToeView(view);
    }

    public void playOtherOtherGame(){ walkingView = new WalkingView(this.view);}

    public void setLogin(){
        Stage loginStage = new Stage();
        Label label = new Label("Username:");
        TextArea login = new TextArea();
        login.setMaxSize(80,15);
        Button loginButton = new Button("Join!");
        loginButton.setOnAction(v->{
            if (!login.getText().equals("")) {
                lobbyView = new LobbyView(view, login.getText());
                loginStage.close();
            }
        });
        VBox vBox = new VBox(label, login, loginButton);
        vBox.setAlignment(Pos.CENTER);
        Scene loginScene = new Scene(vBox, 250, 150);
        loginStage.setScene(loginScene);
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.show();
    }

    public void playPong() throws FileNotFoundException {
        if(view.getDragonController().getDragonView().getMainView().getStoreView().getController().hasSaddle()){
            if(view.getDragonController().getDragonView().getMainView().getStoreView().getController().wearsSaddle()) {
                pongView = new PongView(view);//play
                pongView.display();
                System.out.println(pongView.getScore());
                view.getDragonController().addMoney(pongView.getScore()*10);
                view.getDragonController().addHealth(-0.1f);
            }
            else SaddleBox.display("You need to wear your saddle to play");
        }
        else SaddleBox.display("You need to buy a saddle to play");
    }
}
