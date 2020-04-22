package View;

import Controller.AchievementsController;
import Model.AchievementsModel;
import Model.MainModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class AchievementsView {
    MainView mainView;
    AchievementsController controller;
    AchievementsModel model;
    Scene scene;
    ScrollPane pane;
    VBox mainBox,mainBoxFrame,achBox;
    Achievement ach1,ach2,ach3,ach4,ach5,ach6;
    MainModel.ClickButton backButton;
    HashMap<Integer, Achievement> map;

    public AchievementsView(MainView mainView){
        this.mainView = mainView;
        model = new AchievementsModel();
        controller = new AchievementsController(model,this);

        map= new HashMap<Integer, Achievement>();

        ach1 = new Achievement("Caretaker","pet 5 times",1);
        ach2 = new Achievement("Buyer","buy something",2);
        ach3 = new Achievement("Pong Winner","win Pong game 3 : 0",3);
        ach4 = new Achievement("Painter","Change background(settings)",4);
        ach5 = new Achievement("TickTackToe Winner","win TickTackToe game 3 : 0",5);
        ach6 = new Achievement("TickTackToe Master","win TickTackToe game 3 : 0 (hard mode)",6);

        achBox = new VBox(ach1.box,ach2.box,ach3.box,ach4.box,ach5.box,ach6.box);
        achBox.setSpacing(10);
        achBox.setMinWidth(200);
        achBox.setAlignment(Pos.CENTER);

        backButton = new MainModel.ClickButton("back");
        backButton.setOnAction(value->mainView.stage.setScene(mainView.menu.scene));

        mainBox = new VBox(backButton,achBox);
        mainBox.setSpacing(10);
        mainBoxFrame = new VBox(mainBox);
        mainBoxFrame.setMinHeight(500);
        mainBoxFrame.setMinWidth(385);
        VBox.setMargin(mainBox, new Insets(20,20,20,20));

        pane= new ScrollPane();
        pane.setContent(mainBoxFrame);
        pane.setStyle("-fx-font-size: 10px;");
        mainBoxFrame.setStyle("-fx-font-size: 11px;");
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        final double SPEED = 0.01;
        pane.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * SPEED;
            pane.setVvalue(pane.getVvalue() - deltaY);
        });

        scene = new Scene(pane,400,500);
    }

    public class Achievement {
        HBox box;
        String name;
        boolean isAchieved;
        Label label;
        Tooltip tooltip;
        int id;

        public Achievement(String name, String description,int id) {
            this.id=id;
            this.name = name;
            isAchieved = false;
            label = new Label(name);
            label.setAccessibleRoleDescription(description);
            tooltip = new Tooltip();
            tooltip.setText(description);
            tooltip.setShowDelay(Duration.seconds(0.5f));
            label.setTooltip(tooltip);
            box = new HBox(label);
            box.setStyle("-fx-padding: 10;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" +
                    "-fx-border-radius: 5;" +
                    "-fx-border-color: gray;");

            map.put(id,this);
        }

        public void achieve() {
            isAchieved = true;
            box.setStyle("-fx-padding: 10;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" +
                    "-fx-border-radius: 5;" +
                    "-fx-border-color: gold;");
        }
        public String getName(){return name;}
    }
    public void resetBackground(){
        mainBoxFrame.setBackground(mainView.getMainModel().getMainBackground());
    }
    public HashMap<Integer,Achievement> getMap(){
        return this.map;
    }
    public MainView getMainView(){
        return mainView;
    }
    public AchievementsController getController(){
        return controller;
    }

}
