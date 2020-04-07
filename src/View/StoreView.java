package View;

import Controller.StoreController;
import Model.StoreModel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.LinkedList;

public class StoreView {
    MainView mainView;
    StoreController controller;
    StoreModel model;
    Scene scene;
    Button pick1,pick2,pick3;
    Button buy1,buy2,buy3;//disappear?
    Button back;
    HBox box1,box2,box3,moneyBox;
    VBox mainBox;
    ImageView imgV1,imgV2,imgV3,coinV;
    Label money;
    int moneyInt;
    private static class TypeButton{
        Button b;
        public enum type{BACK,FACE,HEAD};
        type typ;

        public TypeButton(Button b,type typ){
            this.b=b; this.typ=typ;
        }
    }

    LinkedList<TypeButton> BList;
    public StoreView(MainView mainView) throws FileNotFoundException {
        this.mainView=mainView;
        model = new StoreModel();
        controller = new StoreController(model,this);

        back = new Button("Back");

        pick1 = new Button("pick");
        pick1.setDisable(true);//pick1.setVisible(false);
        pick2 = new Button("pick");
        pick2.setDisable(true);
        pick3 = new Button("pick");
        pick3.setDisable(true);
        buy1 = new Button("buy");
        buy2 = new Button("buy");
        buy3 = new Button("buy");

        BList = new LinkedList<>();
        BList.add(new TypeButton(pick1, TypeButton.type.HEAD));
        BList.add(new TypeButton(pick2, TypeButton.type.HEAD));
        BList.add(new TypeButton(pick3, TypeButton.type.BACK));

        imgV1 = new ImageView(model.img1);
        imgV2 = new ImageView(model.img2);
        imgV3 = new ImageView(model.img3);

        box1 = new HBox(imgV1,buy1,pick1);
        box1.setSpacing(5);
        box2 = new HBox(imgV2,buy2,pick2);
        box2.setSpacing(5);
        box3 = new HBox(imgV3,buy3,pick3);
        box3.setSpacing(5);

        moneyInt=mainView.getDragonView().getController().getMoney();
        coinV = new ImageView(model.coin);
        money = new Label(String.valueOf(moneyInt));//get money how??
        moneyBox = new HBox(coinV,money);

        mainBox = new VBox (moneyBox,box1,box2,box3,back);
        mainBox.setSpacing(5);

        back.setOnAction(value->mainView.stage.setScene(mainView.menu.scene));

        buy1.setOnAction(value->{
            pick1.setDisable(false);
            buy1.setDisable(true);
            mainView.getDragonView().getController().addMoney(-30);
        });
        buy2.setOnAction(value->{
            pick2.setDisable(false);
            buy2.setDisable(true);
            mainView.getDragonView().getController().addMoney(-30);
        });
        buy3.setOnAction(value->{
            pick3.setDisable(false);
            buy3.setDisable(true);
            controller.gotSaddle();
            mainView.getDragonView().getController().addMoney(-30);
        });
        pick1.setOnAction(value->{
            controller.setImage(model.img1big,"head");
            if(pick1.getText()=="pick"){
                for(TypeButton t : BList){
                    if(t.typ== TypeButton.type.HEAD)t.b.setText("pick");
                }
                pick1.setText("unpick");
            }
            else pick1.setText("pick");
        });
        pick2.setOnAction(value->{
            controller.setImage(model.img2big,"head");
            if(pick2.getText()=="pick"){
                for(TypeButton t : BList){
                    if(t.typ== TypeButton.type.HEAD)t.b.setText("pick");
                }
                pick2.setText("unpick");
            }
            else pick2.setText("pick");
        });
        pick3.setOnAction(value->{
            controller.setImage(model.img3big,"back");
            if(pick3.getText()=="pick"){
                for(TypeButton t : BList){
                    if(t.typ== TypeButton.type.BACK)t.b.setText("pick");
                }
                pick3.setText("unpick");
                controller.wearsSaddle(true);
            }
            else {
                pick3.setText("pick");
                controller.wearsSaddle(false);
            }
        });


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


}
