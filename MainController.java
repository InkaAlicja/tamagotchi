package Controller;

import Model.MainModel;
import View.MainView;

public class MainController{
    MainView view;
    MainModel model;
    public MainController(MainView view, MainModel model){
        this.view=view;

        this.model=model;
    }

}
