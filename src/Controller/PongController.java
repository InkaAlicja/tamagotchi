package Controller;

import Model.PongModel;
import View.PongView;

public class PongController {
    PongView view;
    PongModel model;
    public PongController(PongView view,PongModel model){
        this.view=view;
        this.model=model;
    }
    public void isIt3to0(int me,int bot){
        if(model.isIt3to0())return;
        if(bot==0 && me>=3) {
            view.getPlayView().getDragonController().getDragonView().getMainView().getAchievementsView().getController().achieve(3);
            model.itIs3to0();
        }
    }
}
