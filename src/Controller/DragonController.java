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
    Thread clockThread;

    public DragonController(DragonView view, DragonModel model)
    {this.view=view; this.model=model; clockThread=new Thread(new ClockIsTicking()); clockThread.start();}

    public void addHappiness(float a){
        model.addHappiness(a);
        view.setHappiness(model.getHappiness());
    }
    public void addHealth(float a){
        try{
            model.addHealth(a);
        }catch(DragonModel.DyingDragonException ex){
            view.setHealth(model.getHealth());
            AlertBox.display("Your dragon needs to be taken care of!","OK, lemme take care of him!");
        }
        view.setHealth(model.getHealth());
    }

    class ClockIsTicking extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            try {
                while (true) {
                    TimeUnit.MINUTES.sleep(5);
                    addHappiness(-0.01f);
                    addHealth(-0.005f);
                }
            }
            catch (InterruptedException e){
                return null;
            }
        }
    }

    public void play(){
        playView = new PlayView(this.view);
    }

    public boolean addMoney(int a){
        try{
            model.addMoney(a);
        }catch(DragonModel.BrokeException ex){
            AlertBox.display("You dont have enough money!","OK, lemme earn some!");
            return false;
        }
        view.setMoney(model.getMoney());
        view.getMainView().getStoreView().getController().addMoney(a);
        return true;
    }

    public Thread getClockThread(){
        return clockThread;
    }

    public void setImageAddition(Image image){
        if(model.getAdditions()!=image){
            model.setAdditions(image);
            view.setView(model.addition1);
        }
        else{
            FileInputStream F;
            Image img;
            try {
                F = new FileInputStream("Resources/blank.png");
                img = new Image(F,100,100,true,false);
                model.setAdditions(img);
                view.setView(model.addition1);
            } catch (FileNotFoundException e) {
                AlertBox.display("File not found :/","ok");
            }

        }
    }

    public int getMoney(){
        return model.getMoney();
    }

}
