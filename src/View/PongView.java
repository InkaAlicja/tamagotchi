package View;

import Controller.PongController;
import Model.PongModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import static java.lang.Math.sqrt;

public class PongView {
    PlayView playView;
    PongModel model;
    PongController controller;

    double barHeight = 100;
    int barWidth = 10;
    int radius = 10;
    double ballXVector,ballYVector;
    double meX=8, meY, botX=600-barWidth-meX, botY,ballX,ballY;
    int scoreMe, scoreBot;
    boolean start;
    int score;
    double speedExc,speedInc=0.3d;
    MediaPlayer mediaPlayer;

    public PongView(PlayView playView){
        this.playView=playView;
        model = new PongModel(this);
        controller = new PongController(this, model);
    }
    public void display() {
        score=0;
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinHeight(400);
        window.setMinWidth(600);

        Canvas canvas = new Canvas(600, 400);
        GraphicsContext graphContext = canvas.getGraphicsContext2D();

        Timeline time = new Timeline(new KeyFrame(Duration.millis(16), e -> run(graphContext)));
        time.setCycleCount(Timeline.INDEFINITE);

        mediaPlayer = model.getMediaPlayer();
        mediaPlayer.setMute(playView.dragonView.mainView.getMainModel().getIsMuted());

        Button back = new Button("back");
        back.setOnAction(value-> { mediaPlayer.stop();time.stop();window.close();});
        HBox smallBox = new HBox(back);
        smallBox.setAlignment(Pos.CENTER);
        smallBox.setBackground(new Background(new BackgroundFill(Color.MAGENTA, CornerRadii.EMPTY, Insets.EMPTY),
                new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(5d))));
        smallBox.setStyle("-fx-padding: 10;");

        VBox box = new VBox(canvas,smallBox);

        canvas.setOnMouseClicked(e -> {
            start = true;
            mediaPlayer.play();
        });
        canvas.setOnMouseMoved(e ->  meY  = e.getY() - barHeight/2);

        Scene scene = new Scene(box);//new StackPane(canvas,back));
        window.setScene(scene);

        time.play();
        window.showAndWait();
    }
    private void run(GraphicsContext graphicsContext){
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, 600, 400);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(Font.font(25));

        if(start){
            ballX += ballXVector;
            ballY += ballYVector;
            if (ballX < 500) {
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
            double min=0.5,max=5;
            ballYVector=0;
            ballXVector=0;
            while(ballXVector==0 || ballYVector==0 || Math.abs(ballXVector/ballYVector)<2) {
                ballXVector = Math.random() * (max - min +1) + min;
                ballYVector = Math.random() * (max - min +1) + min;
            }
            int signumY= Math.random()*(1 + 1)  < 0.5f ? 1: -1;
            int signumX= Math.random()*(1 + 1)  < 0.5f ? 1: -1;
            ballYVector*=signumY;
            ballXVector*=signumX;
            speedExc=sqrt(ballXVector*ballXVector+ballYVector*ballYVector);
            System.out.println(String.valueOf(ballXVector) +" "+ String.valueOf(ballYVector)+ " "+Math.abs(ballXVector/ballYVector));
        }

            if(ballY > 400-5 || ballY < 5){
                ballYVector *=-1;
                while(ballY > 400-5)ballY-=1;
                while(ballY < 5)ballY+=1;
            }

            //jak w srodkowych 25% to odbij pod takim samym katem
            int sides=25;
            if( ((ballX + radius > botX) && (ballY >= botY+sides) && (ballY <= botY + barHeight-sides)) ||
                    ((ballX < meX + barWidth) && (ballY >= meY+sides) && (ballY <= meY + barHeight-sides))) {   //System.out.println("hi");
                double diff=(speedExc+speedInc)/(speedExc);
                speedExc+=speedInc;
                ballYVector *= diff ;
                ballXVector *= diff;
                ballXVector *= -1;
                ballX= ballX<meX+barWidth ? meX+barWidth : botX-radius;
            }//jak po bokach bota
            else if( ((ballX + radius > botX) && (ballY >= botY) && (ballY <= botY + barHeight))){
                double diff=(ballY-botY)>75 ? (ballY-botY)-75: 25-(ballY-botY);

                ballXVector = -1*Math.signum(ballXVector)*(sqrt(1-(diff/26)*(diff/26)));
                ballYVector = -1*Math.signum(ballYVector)*(diff/26);
                speedExc+=speedInc;
                ballYVector *= speedExc;
                ballXVector *= speedExc;
                ballX=botX-radius;
            }//jak po moich bokach
            else if(((ballX < meX + barWidth) && (ballY >= meY) && (ballY <= meY + barHeight))) {
                double diff=(ballY-meY)>75 ? (ballY-meY)-75: 25-(ballY-meY);

                ballXVector = -1*Math.signum(ballXVector)*(sqrt(1-(diff/26)*(diff/26)));
                ballYVector = -1*Math.signum(ballYVector)*(diff/26);
                speedExc+=speedInc;
                ballYVector *= speedExc;
                ballXVector *= speedExc;
                ballX=meX+barWidth;
            }
            else if(ballX < meX - barWidth) {
                scoreBot++;
                start = false;
                score=Math.max(0,scoreMe-scoreBot);
                mediaPlayer.stop();
            }
            else if(ballX > botX + barWidth) {
                scoreMe++;
                start = false;
                score=Math.max(0,scoreMe-scoreBot);
                mediaPlayer.stop();
                controller.isIt3to0(scoreMe,scoreBot);
            }

            graphicsContext.fillText(scoreMe + "\t\t\t\t\t\t" + scoreBot, 300, 100);
            graphicsContext.fillRect(botX, botY, barWidth, barHeight);
            graphicsContext.fillRect(meX,meY,barWidth,barHeight);

    }
    public int getScore(){
        return score;
    }
    public PlayView getPlayView(){ return playView; }

}
