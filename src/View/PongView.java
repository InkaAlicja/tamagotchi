package View;

import Controller.PongController;
import Model.PongModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
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
    int diameter = 10, radius = diameter/2;
    double ballXVector,ballYVector;
    double meX=8, meY, botX=600-barWidth-meX, botY,ballX,ballY, ballCenterX, ballCenterY;
    int scoreMe, scoreBot;
    boolean start;
    int score, sides = 25;
    double speedExc,speedInc=0.1d;
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

        Timeline time = new Timeline(new KeyFrame(Duration.millis(5), e -> run(graphContext)));
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
        canvas.setCache(true);
        //canvas.setCacheShape(true);
        canvas.setCacheHint(CacheHint.SPEED);

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

        if (!start){
            graphicsContext.setStroke(Color.WHITE);
            graphicsContext.setTextAlign(TextAlignment.CENTER);
            graphicsContext.strokeText("Click", 300, 200);

            ballX = 300;
            ballY = 200;
            double min=0.1,max=2;
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
        }
        else {

            ballCenterY = ballY + radius;
            ballCenterX = ballX + radius;

            if ((ballCenterY - radius + ballYVector<0 && ballYVector<0) || (ballCenterY + radius + ballYVector > 400 && ballYVector>0)){
                ballYVector*=-1;
            }

            //me win
            if (ballCenterX + ballXVector + radius < meX - barWidth) {
                scoreBot++;
                start = false;
                score = Math.max(0, scoreMe - scoreBot);
                mediaPlayer.stop();
            }

            //bot win
            else if (ballCenterX + ballXVector - radius > botX + barWidth) {
                scoreMe++;
                start = false;
                score = Math.max(0, scoreMe - scoreBot);
                mediaPlayer.stop();
                controller.isIt3to0(scoreMe, scoreBot);
            }

            //jak w srodkowych 25%
            else if (((ballCenterX + ballXVector + radius > botX) && (ballCenterX + radius <= botX) && (ballCenterY >= botY + sides) && (ballCenterY <= botY + barHeight - sides) && (ballXVector > 0)) ||
                    ((ballCenterX + ballXVector - radius < meX + barWidth) && (ballCenterX - radius >= meX + barWidth) && (ballCenterY >= meY + sides) && (ballCenterY <= meY + barHeight - sides) && (ballXVector < 0))) {
                System.out.println("1");
                double diff = (speedExc + speedInc) / (speedExc);
                speedExc += speedInc;
                ballYVector *= diff;
                ballXVector *= diff;
                ballXVector *= -1;
                //ballX= ballCenterX - radius < meX + barWidth ? meX+barWidth : botX-diameter;
            }

            //jak po bokach bota
            else if (((ballCenterX + ballXVector + radius > botX) && (ballCenterX + radius <= botX) && (ballCenterY >= botY) && (ballCenterY <= botY + barHeight) && (ballXVector > 0))) {
                double diff = (ballCenterY - botY) > 75 ? (ballCenterY - botY) - 75 : 25 - (ballCenterY - botY);
                System.out.println("2");
                ballXVector = -1 * Math.signum(ballXVector) * (sqrt(1 - (diff / 26) * (diff / 26)));
                ballYVector = -1 * Math.signum(ballYVector) * (diff / 26);
                speedExc += speedInc;
                ballYVector *= speedExc;
                ballXVector *= speedExc;
                //ballX=botX-diameter;
            }

            //jak po moich bokach
            else if (((ballCenterX + ballXVector - radius < meX + barWidth) && (ballCenterX - radius >= meX + barWidth) && (ballCenterY >= meY) && (ballCenterY <= meY + barHeight) && (ballXVector < 0))) {
                double diff = (ballCenterY - meY) > 75 ? (ballCenterY - meY) - 75 : 25 - (ballCenterY - meY);
                System.out.println("3");
                ballXVector = -1 * Math.signum(ballXVector) * (sqrt(1 - (diff / 26) * (diff / 26)));
                ballYVector = -1 * Math.signum(ballYVector) * (diff / 26);
                speedExc += speedInc;
                ballYVector *= speedExc;
                ballXVector *= speedExc;
                //ballX=meX+barWidth;
            }

            else if (((ballCenterY + ballYVector - (meY+barHeight))*(ballCenterY + ballYVector - (meY+barHeight))+
                    (ballCenterX + ballXVector-(meX+barWidth))*(ballCenterX + ballXVector-(meX+barWidth))<radius*radius))
                    {
                System.out.println("corner");
                ballXVector+= 1.5 * speedExc;
                ballYVector+=speedExc;
                speedExc=sqrt(ballXVector*ballXVector+ballYVector*ballYVector);
                double diff = (speedExc + 3*speedInc) / (speedExc);
                speedExc += 3*speedInc;
                ballYVector *= diff;
                ballXVector *= diff;
            }

            else if(((ballCenterY + ballYVector - meY)*(ballCenterY + ballYVector - meY) +
                    (ballCenterX + ballXVector - (meX+barWidth))*(ballCenterX + ballXVector -(meX+barWidth))<radius*radius)){
                System.out.println("corner");
                ballXVector+= 1.5 * speedExc;
                ballYVector-=speedExc;
                speedExc=sqrt(ballXVector*ballXVector+ballYVector*ballYVector);
                double diff = (speedExc + 3*speedInc) / (speedExc);
                speedExc += 3*speedInc;
                ballYVector *= diff;
                ballXVector *= diff;
            }

            else if(((ballCenterY + ballYVector - (botY+barHeight))*(ballCenterY + ballYVector - (botY+barHeight))+
                    (ballCenterX + ballXVector-(botX))*(ballCenterX + ballXVector-(botX))<radius*radius)){
                System.out.println("corner");
                ballXVector-= 1.5 * speedExc;
                ballYVector+=speedExc;
                speedExc=sqrt(ballXVector*ballXVector+ballYVector*ballYVector);
                double diff = (speedExc + 3*speedInc) / (speedExc);
                speedExc += 3*speedInc;
                ballYVector *= diff;
                ballXVector *= diff;
            }

            else if(((ballCenterY + ballYVector - botY)*(ballCenterY + ballYVector - botY) +
                    (ballCenterX + ballXVector - (botX))*(ballCenterX + ballXVector -(botX))<radius*radius)){
                System.out.println("corner");
                ballXVector-= 1.5 * speedExc;
                ballYVector-=speedExc;
                speedExc=sqrt(ballXVector*ballXVector+ballYVector*ballYVector);
                double diff = (speedExc + 3*speedInc) / (speedExc);
                speedExc += 3*speedInc;
                ballYVector *= diff;
                ballXVector *= diff;
            }

            ballX += ballXVector;
            ballY += ballYVector;
            if (ballX < 500) {
                botY = ballY - barHeight / 2;
            } else {
                botY = ballY > botY + barHeight / 2 ?botY += 1 : botY - 1;
            }
            graphicsContext.fillOval(ballX, ballY, diameter, diameter);
        }

        graphicsContext.fillText(scoreMe + "\t\t\t\t\t\t" + scoreBot, 300, 100);
        graphicsContext.fillRect(botX, botY, barWidth, barHeight);
        graphicsContext.fillRect(meX, meY, barWidth, barHeight);

    }
    public int getScore(){
        return score;
    }
    public PlayView getPlayView(){ return playView; }

}
