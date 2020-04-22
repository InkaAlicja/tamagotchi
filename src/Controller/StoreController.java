package Controller;

import Model.StoreModel;
import View.StoreView;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import java.util.HashMap;

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
        view.getMainView().getDragonView().getController().setImageAddition(image,where);
    }
    public boolean hasSaddle(){return model.hasSaddle(); }
    public void gotSaddle(){ model.gotSaddle();}
    public boolean wearsSaddle(){return model.wearsSaddle();}
    public void wearsSaddle(boolean b){model.wearsSaddle(b);}

    public boolean  pickButtonAction(Button button, HashMap<Button, StoreView.type> Map){
        StoreView.type buttonType=Map.get(button);
        if(button.getText()=="pick") {
            Map.forEach((Button, typ) -> {
                if (typ == buttonType) view.setButton(Button, "pick");
            });
            button.setText("unpick");
            return true;
        }
        else{
            button.setText("pick");
            return false;
        }
    }

    public void enableTrophy(Integer id){
        HashMap<Integer,StoreView.TrophyBox> map=view.getTrophyMap();
        map.forEach((trophyAchiv,trophyBox)->{
            if(id.equals(trophyAchiv))trophyBox.enableTrophy();
        });
    }

    public void bought(){
        if(!model.didBuy()){
            model.bought();
            view.getMainView().getAchievementsView().getController().achieve(2);
        }
    }

}
