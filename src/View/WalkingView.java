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
import Model.WalkingModel.Bullet;
import Model.WalkingModel.Player;

import java.awt.event.KeyAdapter;
import java.beans.EventHandler;
import java.util.LinkedList;

import static java.lang.Math.sqrt;

public class WalkingView implements javafx.event.EventHandler<KeyEvent>{
    PlayView playView;
    WalkingModel model;
    WalkingController controller;
    MediaPlayer mediaPlayer,mediaPlayerKnock,mediaPlayerShot;
    boolean start, mute;
    double radiusBullet=10;
    Player A,B;


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
        mute = playView.dragonView.mainView.getMainModel().getIsMuted();
        mediaPlayer = model.click.getMediaPlayer();
        mediaPlayerKnock = model.knock.getMediaPlayer();
        mediaPlayerShot = model.shot.getMediaPlayer();
        mediaPlayer.setMute(mute);
        mediaPlayerKnock.setMute(mute);
        mediaPlayerShot.setMute(mute);

        canvas.setOnMouseClicked(e -> {
            start = true;
            mediaPlayer.play();
        });

        Scene scene = new Scene(box);
        scene.setOnKeyPressed(this);
        window.setScene(scene);

        A = new Player(100,200,1,0,false);
        B = new Player(500,200,-1,0,true);

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
            A.X=100;A.Y=200;A.vecX=1;A.vecY=0;A.life=1000;A.bulletList.clear();
            B.X=500;B.Y=200;B.vecX=-1;B.vecY=0;B.life=1000;B.bulletList.clear();
        }else{
            graphicsContext.fillText(A.life + "\t\t\t\t\t\t\t" + B.life, 300, 380);
            graphicsContext.fillOval(A.X, A.Y, A.radius, A.radius);
            graphicsContext.fillOval(B.X, B.Y, B.radius, B.radius);
            writeOut(graphicsContext,A,B);
            writeOut(graphicsContext,B,A);
        }
        graphicsContext.fillText(A.score + "\t\t\t\t\t" + B.score, 300, 100);
    }
    private void writeOut(GraphicsContext graphicsContext,Player player,Player oponent){
        if(!player.bulletList.isEmpty()) {
            Bullet bul = player.bulletList.get(0);
            while (bul.x > 600 || bul.x < 0 || bul.y < 0 || bul.y > 400) {
                player.bulletList.removeFirst();
                if(player.bulletList.isEmpty())break;
                else bul = player.bulletList.getFirst();
            }
        }
        player.bulletList.forEach(b->{
            b.x+=b.vectorX;
            b.y+=b.vectorY;
            graphicsContext.fillOval(b.x, b.y, radiusBullet, radiusBullet);

            if(b.x>=oponent.X &&  b.x<=oponent.X+oponent.radius && b.y>=oponent.Y && b.y<=oponent.Y+oponent.radius) {
                oponent.life--;//odierz my zycie

                if (oponent.life == 0) {
                    start = false;
                    player.score++;
                    return;
                }
            }
        });
    }
    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode keyCode=keyEvent.getCode();
        if(keyCode.equals(KeyCode.A)||keyCode.equals(KeyCode.W)||keyCode.equals(KeyCode.S)||keyCode.equals(KeyCode.D))
            A.move(keyCode,mediaPlayerKnock);
        if(keyCode.equals(KeyCode.G)) A.shoot(keyCode,mediaPlayerShot);
        if(keyCode.equals(KeyCode.NUMPAD8)||keyCode.equals(KeyCode.NUMPAD4)||keyCode.equals(KeyCode.NUMPAD5)||keyCode.equals(KeyCode.NUMPAD6))
         B.move(keyCode,mediaPlayerKnock);
        if(keyCode.equals(KeyCode.L)) B.shoot(keyCode,mediaPlayerShot);
    }

}
