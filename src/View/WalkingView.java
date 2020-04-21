package View;

import Controller.PongController;
import Controller.WalkingController;
import Model.PongModel;
import Model.WalkingModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.KeyAdapter;
import java.beans.EventHandler;

import static java.lang.Math.sqrt;

public class WalkingView implements javafx.event.EventHandler<KeyEvent>{
    PlayView playView;
    WalkingModel model;
    WalkingController controller;
    MediaPlayer mediaPlayer,mediaPlayerKnock;
    boolean start;
    double X,Y,radius=10,speed=20;

    public WalkingView(PlayView play){
        playView=play;
        model = new WalkingModel(this);
        controller = new WalkingController(model,this);
        this.display();
    }
    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinHeight(400);
        window.setMinWidth(600);

        Canvas canvas = new Canvas(600, 400);
        GraphicsContext graphContext = canvas.getGraphicsContext2D();

        Timeline time = new Timeline(new KeyFrame(Duration.millis(16), e -> run(graphContext)));
        time.setCycleCount(Timeline.INDEFINITE);

        Button back = new Button("back");
        back.setOnAction(value-> { mediaPlayer.stop();time.stop();window.close();});
        HBox smallBox = new HBox(back);
        smallBox.setAlignment(Pos.CENTER);
        smallBox.setBackground(new Background(new BackgroundFill(Color.NAVAJOWHITE, CornerRadii.EMPTY, Insets.EMPTY),
                new BackgroundFill(Color.MOCCASIN, CornerRadii.EMPTY, new Insets(5d))));
        smallBox.setStyle("-fx-padding: 10;");

        VBox box = new VBox(canvas,smallBox);

        mediaPlayer = model.getMediaPlayer("click");
        mediaPlayerKnock = model.getMediaPlayer("knock");

        canvas.setOnMouseClicked(e -> {
            start = true;
            mediaPlayer.play();
        });
       // canvas.setOnKeyPressed(this);
        //canvas.setOnMouseMoved(e ->  Y  = e.getY());

        Scene scene = new Scene(box);
        scene.setOnKeyPressed(this);
        window.setScene(scene);

        time.play();
        window.showAndWait();
    }
    public void run(GraphicsContext graphicsContext){
        graphicsContext.setFill(Color.ANTIQUEWHITE);
        graphicsContext.fillRect(0, 0, 600, 400);
        graphicsContext.setFill(Color.DIMGRAY);
        graphicsContext.setFont(Font.font(25));

        if(!start){
            graphicsContext.setStroke(Color.DIMGRAY);
            graphicsContext.setTextAlign(TextAlignment.CENTER);
            graphicsContext.strokeText("Click", 300, 200);
            X=300;
            Y=200;
        }else{
            graphicsContext.fillOval(X, Y, radius, radius);
        }
    }
    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode keykode=keyEvent.getCode();
        boolean XisWall=(X==0 || X==600-radius),YisWall=(Y==0 || Y==400-radius);
        if(keykode.equals(KeyCode.A) ) X=X-speed>0 ? X-speed : 0;
        if(keykode.equals(KeyCode.W) ) Y=Y-speed>0 ? Y-speed : 0;
        if(keykode.equals(KeyCode.S) )Y=Y+speed<400-radius ? Y+speed : 400-radius;
        if(keykode.equals(KeyCode.D) )X=X+speed<600-radius ? X+speed : 600-radius;
        //System.out.println(X+ " "+Y);
        if(((X==0 || X==600-radius) && !XisWall) ||((Y==0 || Y==400-radius) && !YisWall)){
            mediaPlayerKnock.stop();
            mediaPlayerKnock.play();
        }
    }

}
