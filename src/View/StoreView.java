package View;

import Controller.StoreController;
import Model.StoreModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.*;

public class StoreView {
    MainView mainView;
    StoreController controller;
    StoreModel model;
    Scene scene;
    Button back;
    HBox moneyBox,topBox,backBox;
    VBox mainBox;
    ImageView coinV;
    Label money;
    int moneyInt;
    ItemBox item1,item2,item3;

    public enum type{BACK,FACE,HEAD};
    HashMap<Button,type> Map;

    public StoreView(MainView mainView) throws FileNotFoundException {
        this.mainView=mainView;
        model = new StoreModel();
        controller = new StoreController(model,this);

        Map = new HashMap<>();

        item1 = new ItemBox(Map,model.hat.smallImage,model.hat.bigImage,"head",type.HEAD,30,false);
        item2 = new ItemBox(Map,model.bow.smallImage,model.bow.bigImage,"head",type.HEAD,30,false);
        item3 = new ItemBox(Map,model.saddle.smallImage,model.saddle.bigImage,"back",type.BACK,40,true);
        //add item.box to the mainBox

        moneyInt=mainView.getDragonView().getController().getMoney();
        coinV = new ImageView(model.coin);
        money = new Label(String.valueOf(moneyInt));
        moneyBox = new HBox(coinV,money);
        moneyBox.setAlignment(Pos.CENTER);
        moneyBox.setMinSize(200,50);
        back = new Button("Back");
        backBox = new HBox(back);
        backBox.setMinSize(100,50);
        backBox.setAlignment(Pos.CENTER);
        topBox = new HBox(backBox,moneyBox);

        mainBox = new VBox (topBox,item1.box,item2.box,item3.box);
        mainBox.setSpacing(8);

        back.setOnAction(value->mainView.stage.setScene(mainView.menu.scene));

        scene = new Scene(mainBox,400,500);
    }
    public MainView getMainView(){
        return mainView;
    }
    public StoreController getController(){
        return controller;
    }
    public void addMoney(int money){
        moneyInt+=money;
        this.money.setText(String.valueOf(moneyInt));
    }

    public void setButton(Button b,String s){
        b.setText(s);
    }

    void pressBuyButton(Button buyButton,Button pickButton,int cost){
        buyButton.setDisable(true);
        pickButton.setDisable(false);
        mainView.getDragonView().getController().addMoney(-cost);
    }

    public class ItemBox{
        public HBox box;
        public Button pick,buy;
        public ImageView imgView;
        public int cost;

        public ItemBox(Map map,Image img,Image bigImage,String where,type typ,int cost,boolean isSaddle){
            imgView = new ImageView(img);
            buy = new Button("buy");
            pick = new Button ("pick");
            pick.setDisable(true);
            map.put(pick,typ);
            this.cost=cost;

            box = new HBox(imgView,buy,pick);
            box.setSpacing(5);
            box.setAlignment(Pos.CENTER_LEFT);

            buy.setOnAction(value->{
                pressBuyButton(buy,pick,cost);
                if(isSaddle)controller.gotSaddle();
            });
            pick.setOnAction(value->{
                controller.setImage(bigImage,where);
                boolean b=controller.pickButtonAction(pick,Map);
                if(isSaddle)controller.wearsSaddle(b);
            });
        }
    }

}
