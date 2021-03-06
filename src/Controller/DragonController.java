package Controller;

import Additions.AlertBox;
import Model.DragonModel;
import Model.MainModel;
import View.DragonView;
import View.MainView;
import View.PlayView;
import View.TicTacToeView;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static javafx.application.Application.launch;

public class DragonController {
    DragonView view;
    DragonModel model;
    PlayView playView;
    Thread clockThread,animationThread;

    public DragonController(DragonView view, DragonModel model) {
        this.view=view;
        this.model=model;
        clockThread=new Thread(new ClockIsTicking());
        clockThread.setDaemon(true);
        clockThread.start();
        animationThread=new Thread(new animTime());
        animationThread.setDaemon(true);
    }

    public synchronized void addHappiness(float a){
        model.addHappiness(a);
        view.setHappiness(model.getHappiness());
    }
    public void pet(){
        if(model.incPet())view.getMainView().getAchievementsView().getController().achieve(1);
    }
    public boolean addHealth(float a){
        boolean canBeMore=false;
        try{
            canBeMore=model.addHealth(a);
        }catch(DragonModel.DyingDragonException ex){
            view.setHealth(model.getHealth());
            try {
                AlertBox.display("Your dragon needs to be taken care of!","OK, lemme take care of him!", view.getMainView().getMainModel().getMainBackground());
            } catch (FileNotFoundException e) {}
        }
        view.setHealth(model.getHealth());
        return canBeMore;
    }

    class ClockIsTicking extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            try {
                while (true) {
                    //TimeUnit.MINUTES.sleep(5);
                    TimeUnit.MINUTES.sleep(1);
                    model.getMediaPlayerHeart().stop();
                    model.getMediaPlayerHeart().play();
                    addHappiness(-0.01f);
                    try {
                        model.addHealth(-0.005f);
                    } catch (DragonModel.DyingDragonException ignored){ }
                }
            }
            catch (InterruptedException e){
                return null;
            }
        }
    }

    public void play(){
        try {
            playView = new PlayView(this.view);
        } catch (FileNotFoundException e) { }
    }

    public boolean addMoney(int a){
        try{
            model.addMoney(a);
        }catch(DragonModel.BrokeException ex){
            try {
                AlertBox.display("You dont have enough money!","OK, lemme earn some!", view.getMainView().getMainModel().getMainBackground());
            } catch (FileNotFoundException e) {}
            return false;
        }
        view.setMoney(model.getMoney());
        view.getMainView().getStoreView().getController().addMoney(a);
        return true;
    }

    public Thread getClockThread(){
        return clockThread;
    }

    public void setImageAddition(Image image,String bigImgString,String where){
       // if(model.getAddition(where)!=image){
        if(!model.getAdditionString(where).equals(bigImgString)){
            model.setAddition(image,bigImgString,where);
            view.setAddition(model.getAddition(where),where);
        }
        else{
            FileInputStream F;
            Image img;
            try {
               // F = new FileInputStream("Resources/blank.png");
               // img = new Image(F,100,100,true,false);
                model.setAddition(where);
                view.setAddition(model.getAddition(where),where);
            } catch (FileNotFoundException e) {
                try {
                    AlertBox.display("File not found :/","ok", view.getMainView().getMainModel().getMainBackground());
                } catch (FileNotFoundException fileNotFoundException) { System.out.println("ERROR LOADING ALERTBOX");}
            }
        }
    }
    public void setAnimation(Image image){
        model.setAnimation(image);
        view.setAddition(model.getAddition("animation"),"animation");
        animationThread.interrupt();
        animationThread = new Thread(new animTime());
        animationThread.start();
    }
    class animTime extends Task<Void>{
        @Override
        protected Void call() throws Exception {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                model.setAddition("animation");
                view.setAddition(model.getAddition("animation"),"animation");
            } catch (Exception e) {return null;}
            this.cancel();
            return null;
        }
    }

    public int getMoney(){
        return model.getMoney();
    }

    public DragonView getDragonView(){
        return view;
    }
    public PlayView getPlayView(){
        return playView;
    }

}
