package Controller;

import Model.StoreModel;
import View.StoreView;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class StoreController {
    StoreModel model;
    StoreView view;

    public StoreController(StoreModel model, StoreView view){
        this.model=model;
        this.view=view;
    }
    public void addMoney(int money){
        view.addMoney(money);
    }
    public void setImage(Image image,String where){
        if(where.equals("head"))
        view.getMainView().getDragonView().getController().setImageAdditionHead(image);
        if(where.equals("face"))
            view.getMainView().getDragonView().getController().setImageAdditionFace(image);
        if(where.equals("back"))
            view.getMainView().getDragonView().getController().setImageAdditionBack(image);
    }
    public boolean hasSaddle(){return model.hasSaddle(); }
    public void gotSaddle(){ model.gotSaddle();}
    public boolean wearsSaddle(){return model.wearsSaddle();}
    public void wearsSaddle(boolean b){model.wearsSaddle(b);}
}
