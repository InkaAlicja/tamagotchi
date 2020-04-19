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
}
