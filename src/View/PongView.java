package View;

import Controller.PongController;
import Model.PongModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

public class PongView {
    PlayView playView;
    PongModel model;
    PongController controller;

    double barHeight = 100;
    int barWidth = 10;
    int radius = 10;
    double ballXVector,ballYVector;
    double meX=0, meY, botX=600-barWidth, botY,ballX,ballY;
    int scoreMe, scoreBot;
    boolean start;

    public PongView(PlayView playView){
        this.playView=playView;
        model = new PongModel(this);
        controller = new PongController(this, model);
    }
    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinHeight(400);
        window.setMinWidth(600);

        Canvas canvas = new Canvas(600, 400);
        GraphicsContext graphContext = canvas.getGraphicsContext2D();

        Timeline time = new Timeline(new KeyFrame(Duration.millis(20), e -> run(graphContext)));
        time.setCycleCount(Timeline.INDEFINITE);

        Button back = new Button("back");
        back.setOnAction(value-> { time.stop();window.close();});
        VBox box = new VBox(canvas,back);
        box.setAlignment(Pos.CENTER);

        canvas.setOnMouseClicked(e ->  start = true);
        canvas.setOnMouseMoved(e ->  meY  = e.getY());

        Scene scene = new Scene(box);//new StackPane(canvas,back));
        window.setScene(scene);

        window.show();
        time.play();
    }
    private void run(GraphicsContext graphicsContext){
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, 600, 400);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(Font.font(25));

        if(start){
            ballX += ballXVector;
            ballY += ballYVector;
            if (ballX < 450) {
                botY = ballY - barHeight / 2;
            } else {
                botY = ballY > botY + barHeight / 2 ?botY += 1 : botY - 1;
            }

            graphicsContext.fillOval(ballX, ballY, radius, radius);

        }else {
            graphicsContext.setStroke(Color.WHITE);
            graphicsContext.setTextAlign(TextAlignment.CENTER);
            graphicsContext.strokeText("Click", 300, 200);

            ballX = 300;
            ballY = 200;
            double min=1,max=3;
            ballYVector=0;
            ballXVector=0;
            while(ballXVector==0 || ballYVector==0 || Math.abs(ballXVector/ballYVector)<1) {
                ballXVector = Math.random() * (max - min +1) + min;
                ballYVector = Math.random() * (max - min +1) + min;
            }
            System.out.println(String.valueOf(ballXVector) +" "+ String.valueOf(ballYVector)+ " "+Math.abs(ballXVector/ballYVector));
        }
            if(ballY > 400-5 || ballY < 5) ballYVector *=-1;

            if(ballX < meX - barWidth) {
                scoreBot++;
                start = false;
            }
            if(ballX > botX + barWidth) {
                scoreMe++;
                start = false;
            }
            if( ((ballX + radius > botX) && ballY >= botY && ballY <= botY + barHeight) ||
                    ((ballX < meX + barWidth) && ballY >= meY && ballY <= meY + barHeight)) {
                ballYVector += 1 * Math.signum(ballYVector);
                ballXVector += 1 * Math.signum(ballXVector);
                ballXVector *= -1;
                ballYVector *= -1;
            }

            graphicsContext.fillText(scoreMe + "\t\t\t\t\t\t" + scoreBot, 300, 100);
            graphicsContext.fillRect(botX, botY, barWidth, barHeight);
            graphicsContext.fillRect(meX,meY,barWidth,barHeight);

    }



}
