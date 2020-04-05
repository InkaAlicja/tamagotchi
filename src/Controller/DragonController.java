package Controller;

import Additions.AlertBox;
import Model.DragonModel;
import Model.MainModel;
import View.DragonView;
import View.MainView;
import javafx.concurrent.Task;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class DragonController {
    DragonView view;
    DragonModel model;
    Thread clockThread;

    public DragonController(DragonView view, DragonModel model){this.view=view; this.model=model; clockThread=new Thread(new ClockIsTicking()); clockThread.start();}

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

    public boolean addMoney(int a){
        try{
            model.addMoney(a);
        }catch(DragonModel.BrokeException ex){
            AlertBox.display("You dont have enough money!","OK, lemme earn some!");
            return false;
        }
        view.setMoney(model.getMoney());
        return true;
    }

    public Thread getClockThread(){
        return clockThread;
    }

}
