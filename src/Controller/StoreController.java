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
    public void setImage(Image image){
        view.getMainView().getDragonView().getController().setImageAddition(image);
    }
    public void addMoney(int money){
        view.addMoney(money);
        //view.getMainView().getDragonView().getController().addMoney(money); //sie zapetla ofc
    }
}
