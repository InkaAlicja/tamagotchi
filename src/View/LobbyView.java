package View;

import Additions.AlertBox;
import Controller.LobbyController;
import Model.MainModel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LobbyView {
    PlayView playView;
    Scene scene;
    VBox mainVBox;
    MainModel.ClickButton backButton;
    Map<String, Button> map;
    LobbyController controller;
    Background background;

    public LobbyView(PlayView playView, String name) throws FileNotFoundException {
        this.playView = playView;
        background = playView.getDragonController().getDragonView().getMainView().getMainModel().getMainBackground();
        try {
            controller = new LobbyController(this, name);
        } catch (IOException e){
            AlertBox.display("Connection error", "Ok", background);
            return;
        }
        backButton = new MainModel.ClickButton("Back", 72,36);
        backButton.setOnAction(value->{
            playView.getStage().setTitle("Play");
            controller.back();
        });
        mainVBox = new VBox(backButton);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(5);
        mainVBox.setBackground(background);
        scene = new Scene(mainVBox, 400, 500);
        map = new HashMap<>();
        playView.getStage().setTitle("Lobby");
        playView.getStage().setScene(scene);
    }

    public void showInvitation(String id) throws FileNotFoundException {
        if (!controller.getAmIChallenging()) {
            removeButton(id);
            MainModel.ClickButton temp = new MainModel.ClickButton("Accept invitation from " + id, 180, 36);
            temp.setOnAction(value -> controller.acceptInvitation(id));
            map.put(id, temp);
            mainVBox.getChildren().add(temp);
        }
    }

    public void addButton(String id) throws FileNotFoundException {
        System.out.println("Adding button "+ id);
        MainModel.ClickButton temp = new MainModel.ClickButton("Challenge "+ id, 180, 36);
        temp.setDisable(controller.getAmIChallenging());
        temp.setOnAction(value -> controller.challenge(id));
        map.put(id, temp);
        mainVBox.getChildren().add(temp);
    }

    public void removeButton(String id){
        System.out.println("Removing button "+id);
        mainVBox.getChildren().remove(map.remove(id));
        if (id.equals(controller.getWhoAmIChallenging())){
            disableOrEnableButtons(id, false);
            controller.setAmIChallenging(false);
        }
    }

    public void disableOrEnableButtons(String id, boolean b){
        for (String s: map.keySet()){
            if (!s.equals(id))
                map.get(s).setDisable(b);
        }
        if (b)
            map.get(id).setText("Waiting...");
    }
    public PlayView getPlayView(){
        return playView;
    }

}
