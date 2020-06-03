package View;

import Controller.StoreController;
import Model.AchievementsModel;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
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
    ItemBox []items;//item1,item2,item3,item4,item5;
    TrophyBox []trophies;// item6,item7,item8,item9,item10,item11;
    ScrollPane pane;
    HashMap<Integer,TrophyBox> mapOfTrophies;

    public enum type{BACK,FACE,HEAD};
    HashMap<ItemButton,type> Map;

    public StoreView(MainView mainView) throws FileNotFoundException {
        this.mainView=mainView;
        model = new StoreModel();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/data/storeModel.bin"));
            StoreModel tempModel = (StoreModel) objectInputStream.readObject();
            objectInputStream.close();
            copyInfo(model,tempModel);
        }catch(Exception e) { System.out.println("Nieudane kopiowanie storemodel z pliku"); }
        controller = new StoreController(model,this);

        Map = new HashMap<>();
        mapOfTrophies = new HashMap<>();

        items = new ItemBox[5];
        items[0] = new ItemBox(Map,model.hat,"head",type.HEAD,30,false);
        items[1] = new ItemBox(Map,model.bow,"head",type.HEAD,30,false);
        items[2] = new ItemBox(Map,model.saddle,"back",type.BACK,40,true);
        items[3] = new ItemBox(Map,model.flowers,"back",type.BACK,40,false);
        items[4] = new ItemBox(Map,model.zdzblo,"face",type.FACE,10,false);

        trophies = new TrophyBox[6];
        trophies[0] = new TrophyBox(Map,model.odznaka,"back",type.BACK,2,"Buyer");
        trophies[1] = new TrophyBox(Map,model.odznaka2,"back",type.BACK,1,"Caretaker");
        trophies[2] = new TrophyBox(Map,model.odznaka3,"back",type.BACK,3,"Winner");

        trophies[3] = new TrophyBox(Map,model.odznaka4,"back",type.BACK,4,"Painter");
        trophies[4] = new TrophyBox(Map,model.medal,"back",type.BACK,5,"TickTackToe Winner");
        trophies[5] = new TrophyBox(Map,model.trophy,"face",type.FACE,6,"TickTackToe Master");
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

        mainBox = new VBox (topBox,items[0].box,items[1].box,items[2].box,items[3].box,items[4].box,
                trophies[0].box,trophies[1].box,trophies[2].box,trophies[3].box,trophies[4].box,trophies[5].box);
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
        resetBackground();
        scene = new Scene(pane,400,500);
    }
    public MainView getMainView(){
        return mainView;
    }
    public StoreController getController(){
        return controller;
    }
    public StoreModel getModel(){return model;}
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
    public static class ItemButton extends MainModel.ClickButton {
        StoreModel.Item buttonItem;
        ItemButton(String name,String sound,int width,int height,StoreModel.Item it) throws FileNotFoundException {
            super(name,sound,width,height);
            buttonItem=it;
        }
        ItemButton(String name,int width,int height,StoreModel.Item it) throws FileNotFoundException {
            super(name,width,height);
            buttonItem=it;
        }
        public StoreModel.Item item(){return buttonItem;}
    }

    public class ItemBox{
        public HBox box,pictureBox;
        public ItemButton pick,buy;
        public ImageView imgView;
        public int cost;
        StoreModel.Item myItem;

        public ItemBox(HashMap<ItemButton,type> map, StoreModel.Item item, String where, type typ, int cost, boolean isSaddle)
                throws FileNotFoundException {
            myItem=item;
            imgView = new ImageView(myItem.smallImg());
            buy = new ItemButton("buy","Resources/buttonClick.mp3",40,25,myItem);
            pick = new  ItemButton("pick","Resources/buttonClick.mp3",45,25,myItem);

            if(item.bought())buy.setDisable(true);
            else pick.setDisable(true);

            if(myItem.isWearing()){
               // controller.setImage(item.bigImg(),where); // to nas wyblankuje bo juz zaladowalismy w DragonModelu co ma byc
                controller.pickButtonAction(pick,Map);//wear sa juz dobrze ustawione
            }

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
                myItem.buy();
                if(isSaddle)controller.gotSaddle();
                controller.bought();
            });
            pick.setOnAction(value->{
                //myItem.wear();
                controller.setImage(myItem.bigImg(),myItem.bigImgString(),where);
                boolean b=controller.pickButtonAction(pick,Map);
                if(isSaddle)controller.wearsSaddle(b);
            });
        }
    }
    public class TrophyBox{
        public HBox box,pictureBox;
        public ItemButton pick;
        public ImageView imgView;
        public Label label;
        int id;
        String name;
        StoreModel.Item myItem;

        public TrophyBox(HashMap<ItemButton,type> map, StoreModel.Item item, String where, type typ, int achievement, String name)
                throws FileNotFoundException {
            myItem=item;
            id=achievement;
            this.name=name;
            imgView = new ImageView(item.smallImg());
            this.label=new Label("Required: "+name);
            pick = new ItemButton("pick",45,25, item);
            if(!myItem.didWeWin())pick.setDisable(true);
            else label.setDisable(true);

            if(myItem.isWearing()){
                controller.setImage(item.bigImg(),item.bigImgString(),where);
                controller.pickButtonAction(pick,Map);
            }
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
               // controller.setImage(myItem.bigImg(),myItem.bigImgString(),where); // to nas wyblankuje bo juz zaladowalismy w DragonModelu co ma byc
                controller.pickButtonAction(pick,Map);
            });
            mapOfTrophies.put(id,this);
        }

        public void enableTrophy(){
            pick.setDisable(false);
            label.setDisable(true);
            myItem.win();
        }
    }

    public void resetBackground(){
        mainBoxFrame.setBackground(mainView.getMainModel().getMainBackground());
    }

    public HashMap<Integer,TrophyBox> getTrophyMap(){
        return this.mapOfTrophies;
    }

    void copyInfo(StoreModel model,StoreModel temp){
        if(temp.didBuy())model.bought();
        if(temp.hasSaddle()){ model.gotSaddle();}
        if(temp.wearsSaddle())model.wearsSaddle(true);

        for(int i=0;i<11;i++){
            if(temp.modelItems[i].bought()) model.modelItems[i].buy();
            if(temp.modelItems[i].isWearing())model.modelItems[i].wear();
            if(temp.modelItems[i].didWeWin())model.modelItems[i].win();
        }
    }

}
