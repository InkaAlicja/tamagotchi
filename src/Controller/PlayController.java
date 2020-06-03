package Controller;

import Additions.AlertBox;
import View.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
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
    Background background;
    
    public PlayController(PlayView view){
        this.view=view;
        background = view.getDragonController().getDragonView().getMainView().getMainModel().getMainBackground();
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
            try {
                if (login.getText().equals(""))
                    AlertBox.display("Name can't be empty", "Ok", background);
                else if (login.getText().length()>10)
                    AlertBox.display("Name is too long", "Ok", background);
                else {
                    lobbyView = new LobbyView(view, login.getText());
                    loginStage.close();
                }
            } catch (FileNotFoundException e){
                loginStage.close();
            }
        });
        VBox vBox = new VBox(label, login, loginButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        vBox.setBackground(background);
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
            else AlertBox.display("You need to wear your saddle to play", "Back", background);
        }
        else AlertBox.display("You need to buy a saddle to play", "Back", background);
    }
}
