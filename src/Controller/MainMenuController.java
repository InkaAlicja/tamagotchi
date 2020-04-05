package Controller;

import View.MainMenuView;

public class MainMenuController {
    MainMenuView view;
    public MainMenuController(MainMenuView view){
        this.view=view;
    }

    public void exit(){
        view.getMainView().getDragonView().getController().getClockThread().interrupt();
        view.getMainView().getStage().close();
    }
}
