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

public class StoreView {
    MainView mainView;
    StoreController controller;
    StoreModel model;
    Scene scene;
    Button pick1,pick2,pick3;
    Button buy1,buy2,buy3;//disappear?
    Button back;
    HBox box1,box2,box3;
    VBox mainBox;
    ImageView imgV1,imgV2,imgV3,coinV;
    Label money;
    public StoreView(MainView mainView) throws FileNotFoundException {
        this.mainView=mainView;
        controller = new StoreController(model,this);
        model = new StoreModel();
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

        imgV1 = new ImageView(model.img1);
        imgV2 = new ImageView(model.img2);
        imgV3 = new ImageView(model.img3);

        box1 = new HBox(imgV1,buy1,pick1);
        box1.setSpacing(5);
        box2 = new HBox(imgV2,buy2,pick2);
        box2.setSpacing(5);
        box3 = new HBox(imgV3,buy3,pick3);
        box3.setSpacing(5);

        coinV = new ImageView(model.coin);
        money = new Label("money",coinV);//get money how??

        mainBox = new VBox (money,box1,box2,box3,back);
        mainBox.setSpacing(5);

        back.setOnAction(value->mainView.stage.setScene(mainView.menu.scene));

        buy1.setOnAction(value->{
            pick1.setDisable(false);
            buy1.setDisable(true);
        });
        buy2.setOnAction(value->{
            pick2.setDisable(false);
            buy2.setDisable(true);
        });
        buy3.setOnAction(value->{
            pick3.setDisable(false);
            buy3.setDisable(true);
        });
        pick1.setOnAction(value->{
            controller.setImage(model.img1);
            if(pick1.getText()=="pick"){
                pick1.setText("unpick");
                pick2.setText("pick");
                pick3.setText("pick");
            }
            else pick1.setText("pick");
        });
        pick2.setOnAction(value->{
            controller.setImage(model.img2);
            if(pick2.getText()=="pick"){
                pick2.setText("unpick");
                pick3.setText("pick");
                pick1.setText("pick");
            }
            else pick2.setText("pick");
        });
        pick3.setOnAction(value->{
            controller.setImage(model.img3);
            if(pick3.getText()=="pick"){
                pick3.setText("unpick");
                pick2.setText("pick");
                pick1.setText("pick");
            }
            else pick3.setText("pick");
        });


        scene = new Scene(mainBox,400,500);
    }
    public MainView getMainView(){
        return mainView;
    }


}
