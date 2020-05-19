package View;

import Controller.StoreController;
import Model.MainModel;
import Model.StoreModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
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
    MainModel.ClickButton back;
    HBox moneyBox,topBox,backBox;
    VBox mainBox,mainBoxFrame;
    ImageView coinV;
    Label money;
    int moneyInt;
    ItemBox item1,item2,item3,item4,item5;
    TrophyBox item6,item7,item8,item9,item10,item11;
    ScrollPane pane;
    HashMap<Integer,TrophyBox> mapOfTrophies;

    public enum type{BACK,FACE,HEAD};
    HashMap<Button,type> Map;

    public StoreView(MainView mainView) throws FileNotFoundException {
        this.mainView=mainView;
        model = new StoreModel();
        controller = new StoreController(model,this);

        Map = new HashMap<>();
        mapOfTrophies = new HashMap<>();

        item1 = new ItemBox(Map,model.hat.smallImage,model.hat.bigImage,"head",type.HEAD,30,false);
        item2 = new ItemBox(Map,model.bow.smallImage,model.bow.bigImage,"head",type.HEAD,30,false);
        item3 = new ItemBox(Map,model.saddle.smallImage,model.saddle.bigImage,"back",type.BACK,40,true);
        item4 = new ItemBox(Map,model.flowers.smallImage,model.flowers.bigImage,"back",type.BACK,40,false);
        item5 = new ItemBox(Map,model.zdzblo.smallImage,model.zdzblo.bigImage,"face",type.FACE,10,false);

        item6 = new TrophyBox(Map,model.odznaka.smallImage,model.odznaka.bigImage,"back",type.BACK,2,"Buyer");
        item7 = new TrophyBox(Map,model.odznaka2.smallImage,model.odznaka2.bigImage,"back",type.BACK,1,"Caretaker");
        item8 = new TrophyBox(Map,model.odznaka3.smallImage,model.odznaka3.bigImage,"back",type.BACK,3,"Winner");

        item9 = new TrophyBox(Map,model.odznaka4.smallImage,model.odznaka4.bigImage,"back",type.BACK,4,"Painter");
        item10 = new TrophyBox(Map,model.medal.smallImage,model.medal.bigImage,"back",type.BACK,5,"TickTackToe Winner");
        item11 = new TrophyBox(Map,model.trophy.smallImage,model.trophy.bigImage,"face",type.FACE,6,"TickTackToe Master");
        //add item.box to the mainBox

        moneyInt=mainView.getDragonView().getController().getMoney();
        coinV = new ImageView(model.coin);
        money = new Label(String.valueOf(moneyInt));
        moneyBox = new HBox(coinV,money);
        moneyBox.setAlignment(Pos.CENTER);
        moneyBox.setMinSize(230,50);
        back = new MainModel.ClickButton("Back",60,30);
        backBox = new HBox(back);
        backBox.setMinSize(70,40);
        backBox.setAlignment(Pos.CENTER);
        topBox = new HBox(backBox,moneyBox);

        mainBox = new VBox (topBox,item1.box,item2.box,item3.box,item4.box,item5.box,item6.box,item7.box,item8.box,item9.box,item10.box,item11.box);
        mainBox.setSpacing(8);
        mainBoxFrame = new VBox(mainBox);
        VBox.setMargin(mainBox, new Insets(10,10,10,10));
        mainBoxFrame.setMinWidth(385);
        mainBoxFrame.setMinHeight(500);

        pane= new ScrollPane();
        pane.setStyle("-fx-font-size: 10px;");
        mainBoxFrame.setStyle("-fx-font-size: 11px;");
        pane.setContent(mainBoxFrame);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        final double SPEED = 0.01;
        pane.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * SPEED;
            pane.setVvalue(pane.getVvalue() - deltaY);
        });

        back.setOnAction(value->mainView.stage.setScene(mainView.menu.scene));

        scene = new Scene(pane,400,500);
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
        if(mainView.getDragonView().getController().addMoney(-cost)) {
            buyButton.setDisable(true);
            pickButton.setDisable(false);
        }

    }

    public class ItemBox{
        public HBox box,pictureBox;
        public Button pick,buy;
        public ImageView imgView;
        public int cost;

        public ItemBox(HashMap<Button,type> map,Image img,Image bigImage,String where,type typ,int cost,boolean isSaddle){
            imgView = new ImageView(img);
            buy = new Button("buy");
            pick = new Button ("pick");
            pick.setDisable(true);
            map.put(pick,typ);
            this.cost=cost;

            pictureBox = new HBox(imgView);
            pictureBox.setStyle(
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" +
                    "-fx-border-radius: 5;" +
                    "-fx-border-color: black;");

            box = new HBox(pictureBox,buy,pick);
            box.setSpacing(5);
            box.setAlignment(Pos.CENTER_LEFT);

            buy.setOnAction(value->{
                pressBuyButton(buy,pick,cost);
                if(isSaddle)controller.gotSaddle();
                controller.bought();
            });
            pick.setOnAction(value->{
                controller.setImage(bigImage,where);
                boolean b=controller.pickButtonAction(pick,Map);
                if(isSaddle)controller.wearsSaddle(b);
            });
        }
    }
    public class TrophyBox{
        public HBox box,pictureBox;
        public Button pick;
        public ImageView imgView;
        public Label label;
        int id;
        String name;

        public TrophyBox(HashMap<Button,type> map,Image img,Image bigImage,String where,type typ,int achievement,String name){
            id=achievement;
            this.name=name;
            imgView = new ImageView(img);
            this.label=new Label("Required: "+name);
            pick = new Button ("pick");
            pick.setDisable(true);
            map.put(pick,typ);

            pictureBox = new HBox(imgView);
            pictureBox.setStyle(
                    "-fx-border-style: solid inside;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-insets: 5;" +
                            "-fx-border-radius: 5;" +
                            "-fx-border-color: black;");


            box = new HBox(pictureBox,pick,label);
            box.setSpacing(5);
            box.setAlignment(Pos.CENTER_LEFT);

            pick.setOnAction(value->{
                controller.setImage(bigImage,where);
                controller.pickButtonAction(pick,Map);
            });
            mapOfTrophies.put(id,this);
        }

        public void enableTrophy(){
            pick.setDisable(false);
            label.setDisable(true);
        }
    }

    public void resetBackground(){
        mainBoxFrame.setBackground(mainView.getMainModel().getMainBackground());
    }

    public HashMap<Integer,TrophyBox> getTrophyMap(){
        return this.mapOfTrophies;
    }

}
