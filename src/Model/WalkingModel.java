package Model;

import View.PongView;
import View.WalkingView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.LinkedList;

public class WalkingModel {
    WalkingView view;
    public Sound knock,click,shot;
    public WalkingModel(WalkingView view){
        this.view=view;
        click = new Sound("Resources/ping.mp3");
        knock = new Sound("Resources/knock.mp3");
        shot = new Sound("Resources/shootQ.mp3");

    }
    public class Sound{
        Media sound;
        MediaPlayer player;
        public Sound(String file){
            sound = new Media(new File(file).toURI().toString());
            player = new MediaPlayer(sound);
        }
        public MediaPlayer getMediaPlayer() {
            return player;
        }
    }
    public static class Bullet{
        public double x,y,speed=6;
        public double vectorX,vectorY;
        public Bullet(double x,double y,double vecX,double vecY,double ownerRadius){
            this.x=x+ownerRadius/4;
            this.y=y+ownerRadius/4;
            vectorX=vecX*speed;
            vectorY=vecY*speed;
            //System.out.println(vectorX+ " " + vectorY);
        }
    }

    public static class Player{
        public double X,Y,radius=25,vecX,vecY,speed=5;
        public int life,score;
        private KeyCode A,W,S,D,SHOT;
        public LinkedList<Bullet> bulletList;
        public Player(double x, double y,double vx,double vy,boolean isArrows){
            X=x;
            Y=y;
            vecX=vx;
            vecY=vy;
            life=100;
            score=0;
            if(isArrows){A = KeyCode.NUMPAD4;W = KeyCode.NUMPAD8;S = KeyCode.NUMPAD5;D = KeyCode.NUMPAD6;SHOT=KeyCode.L;}
            else {A = KeyCode.A;W = KeyCode.W;S = KeyCode.S;D = KeyCode.D;SHOT=KeyCode.G;}
            bulletList = new LinkedList<>();
        }
        public void move(KeyCode keykode, MediaPlayer mp) {
            boolean XisWall = (X == 0 || X == 600 - radius), YisWall = (Y == 0 || Y == 400 - radius);
            if (keykode.equals(A)) {
                vectors(keykode);
                X = X - speed > 0 ? X - speed : 0;
            }
            if (keykode.equals(W)) {
                vectors(keykode);
                Y = Y - speed > 0 ? Y - speed : 0;
            }
            if (keykode.equals(S)) {
                vectors(keykode);
                Y = Y + speed < 400 - radius ? Y + speed : 400 - radius;
            }
            if (keykode.equals(D)) {
                vectors(keykode);
                X = X + speed < 600 - radius ? X + speed : 600 - radius;
            }
            //System.out.println(X+ " "+Y);
            if (((X == 0 || X == 600 - radius) && !XisWall) || ((Y == 0 || Y == 400 - radius) && !YisWall)) {
                mp.stop();
                mp.play();
            }
        }
        public void shoot(KeyCode keykode, MediaPlayer mp){
            if(keykode.equals(SHOT)){
                mp.stop();
                mp.play();
                bulletList.addLast(new Bullet(X,Y,vecX,vecY,radius));
            }
        }
        void vectors(KeyCode code){
            if(code.equals(A)){vecX=-1;vecY=0;}
            if(code.equals(W)){vecX=0;vecY=-1;}
            if(code.equals(S)){vecX=0;vecY=1;}
            if(code.equals(D)){vecX=1;vecY=0;}
        }
    }

}
