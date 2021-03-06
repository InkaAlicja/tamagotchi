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
    public void achieve(Integer trophyId){
        model.setAchToTrue(trophyId);
        HashMap<Integer, AchievementsView.Achievement> map=view.getMap();
        map.forEach((id,ach)->{
            if(id.equals(trophyId))ach.achieve();
        });
        if(trophyId<=6)view.getMainView().getStoreView().getController().enableTrophy(trophyId);
    }
    public AchievementsModel getModel(){return model;}
}
