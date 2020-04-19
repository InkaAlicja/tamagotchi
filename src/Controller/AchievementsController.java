package Controller;

import Model.AchievementsModel;
import View.AchievementsView;

import java.util.HashMap;

public class AchievementsController {
    AchievementsModel model;
    AchievementsView view;

    public AchievementsController(AchievementsModel model, AchievementsView view){
        this.model=model;
        this.view=view;
    }
    public void achieve(String trophyName){
        HashMap<String, AchievementsView.Achievement> map=view.getMap();
        map.forEach((name,ach)->{
            if(name.equals(trophyName))ach.achieve();
        });
        view.getMainView().getStoreView().getController().enableTrophy(trophyName);
    }
}
