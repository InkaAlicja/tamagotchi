package Controller;

import Model.DragonModel;
import Model.MainModel;
import View.DragonView;
import View.MainView;

public class DragonController {
    DragonView view;
    DragonModel model;
    public DragonController(DragonView view, DragonModel model){this.view=view; this.model=model;}

    public void addHappiness(float a){
        model.addHappiness(a);
        view.setHappiness(model.getHappiness());
    }
    public void addHealth(float a){
        model.addHealth(a);
        view.setHealth(model.getHealth());
    }

}
