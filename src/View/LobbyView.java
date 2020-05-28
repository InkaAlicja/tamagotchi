package View;

import Controller.LobbyController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LobbyView {
    PlayView playView;
    Scene scene;
    VBox mainVBox;
    Button backButton;
    Map<String, Button> map;
    LobbyController controller;

    public LobbyView(PlayView playView, String name){
        this.playView = playView;
        try {
            controller = new LobbyController(this, name);
        } catch (IOException e){
            System.out.println("no");
            return;
        }
        backButton = new Button("Back");
        backButton.setOnAction(value->controller.back());
        mainVBox = new VBox(backButton);
        mainVBox.setAlignment(Pos.CENTER);
        scene = new Scene(mainVBox, 400, 500);
        map = new HashMap<>();
        playView.getStage().setScene(scene);
    }

    public void showInvitation(String id){
        removeButton(id);
        Button temp = new Button ("Accept invitation from "+id);
        temp.setOnAction(value->controller.acceptInvitation(id));
        map.put(id, temp);
        mainVBox.getChildren().add(temp);
    }

    public void addButton(String id){
        System.out.println("Adding button "+ id);
        Button temp = new Button("Challenge "+ id);
        temp.setOnAction(value -> controller.challenge(id));
        map.put(id, temp);
        mainVBox.getChildren().add(temp);
    }

    public void removeButton(String id){
        System.out.println("Removing button "+id);
        mainVBox.getChildren().remove(map.remove(id));
    }

    public PlayView getPlayView(){
        return playView;
    }
}
