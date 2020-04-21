package Controller;

import Model.WalkingModel;
import View.WalkingView;

public class WalkingController {
    WalkingView view;
    WalkingModel model;
    public WalkingController(WalkingModel model,WalkingView view){
        this.view=view;
        this.model=model;
    }


}
